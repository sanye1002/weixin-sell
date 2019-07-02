package cn.moon.sell.core.service.wx.impl;

import cn.moon.sell.common.bean.OrderStatusEnum;
import cn.moon.sell.common.bean.PayStatusEnum;
import cn.moon.sell.common.bean.ResponseCode;
import cn.moon.sell.common.exception.RequestException;
import cn.moon.sell.common.util.KeyUtil;
import cn.moon.sell.core.dto.order.CartDTO;
import cn.moon.sell.core.dto.order.OrderDTO;
import cn.moon.sell.core.dto.order.PageOrderDTOByOpenid;
import cn.moon.sell.core.dto.order.WXOrderDTO;
import cn.moon.sell.core.entity.OrderDetail;
import cn.moon.sell.core.entity.OrderMaster;
import cn.moon.sell.core.entity.ProductInfo;
import cn.moon.sell.core.mapper.OrderDetailMapper;
import cn.moon.sell.core.mapper.ProductInfoMapper;
import cn.moon.sell.core.service.system.SysOrderDetailService;
import cn.moon.sell.core.service.system.SysOrderMasterService;
import cn.moon.sell.core.service.system.SysProductInfoService;
import cn.moon.sell.core.service.wx.WXOrderService;
import com.baomidou.mybatisplus.plugins.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author zhaoxiang
 * @Date 2019/01/04
 * @Desc
 */
@Service
@Slf4j
public class WXOrderServiceImpl implements WXOrderService {

    @Autowired
    private SysOrderDetailService detailService;
    @Autowired
    private SysOrderMasterService masterService;
    @Autowired
    private SysProductInfoService productInfoService;

    @Override
    @Transactional
    public WXOrderDTO create(WXOrderDTO orderDTO) {

        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        try {
            if (orderDTO.getOrderDetailList().isEmpty()) {
                throw RequestException.fail(ResponseCode.CART_EMPTY);
            }
//            1 . 处理订单详情
            for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
//                 1 . 查找商品
                ProductInfo productInfo = productInfoService.selectById(orderDetail.getProductId());
                if (productInfo == null) {
                    throw RequestException.fail(ResponseCode.PRODUCT_NOT_EXIST);
                }
//                2 . 计算总价
                orderAmount = productInfo.getProductPrice()
                        .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                        .add(orderAmount);
//                3 . 详情入库
                orderDetail.setOrderId(orderId);
                BeanUtils.copyProperties(productInfo, orderDetail);
                detailService.insert(orderDetail);
            }
//            2 . 写入订单数据库
            OrderMaster orderMaster = new OrderMaster();
            orderMaster.setOrderId(orderId);
            BeanUtils.copyProperties(orderDTO, orderMaster);
            orderMaster.setOrderAmount(orderAmount);
            orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
            orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
            masterService.insertOrderMaster(orderMaster);
//            3 . 扣库存
            List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                    new CartDTO(e.getProductId(), e.getProductQuantity())
            ).collect(Collectors.toList());
            productInfoService.decreaseStock(cartDTOList);
//            4 . 发送websocket消息 TODO
//            5 . 打印小票 TODO
            return orderDTO;
        } catch (Exception e) {
            throw RequestException.fail(ResponseCode.NETWORK_ERROR);
        }

    }

    @Override
    public WXOrderDTO findOne(String orderId) {
        try {
            OrderMaster orderMaster = masterService.selectOrderMasterById(orderId);
            if (orderMaster == null) {
                throw new RequestException(ResponseCode.ORDER_NOT_EXIST);
            }

            List<OrderDetail> orderDetailList = detailService.listOrderDetailByOrderId(orderId);
            if (CollectionUtils.isEmpty(orderDetailList)) {
                throw new RequestException(ResponseCode.ORDERDETAIL_NOT_EXIST);
            }
            WXOrderDTO orderDTO = new WXOrderDTO();
            BeanUtils.copyProperties(orderMaster, orderDTO);
            orderDTO.setOrderDetailList(orderDetailList);
            return orderDTO;
        } catch (Exception e) {
            throw RequestException.fail("ding！！！请重试~", e);
        }
    }

    @Override
    public Page<WXOrderDTO> findList(String buyerOpenid, PageOrderDTOByOpenid orderDTOByOpenid) {
        try {
            Page<OrderMaster> orderMasterPage = masterService.selectPageByOpenId(buyerOpenid, orderDTOByOpenid);
            Page<WXOrderDTO> orderDTOPage = new Page<>();
            BeanUtils.copyProperties(orderMasterPage, orderDTOPage);
            if (orderMasterPage.getRecords().isEmpty()) {
                throw RequestException.fail(ResponseCode.ORDER_DETAIL_EMPTY);
            }
            orderDTOPage.setRecords(
                    orderMasterPage.getRecords().stream().map(e -> {
                        WXOrderDTO orderDTO = new WXOrderDTO();
                        BeanUtils.copyProperties(e, orderDTO);
                        orderDTO.setOrderDetailList(detailService.listOrderDetailByOrderId(e.getOrderId()));
                        return orderDTO;
                    }).collect(Collectors.toList())
            );
            return orderDTOPage;
        } catch (Exception e) {
            throw RequestException.fail("网络异常，数据查询失败，请重试~", e);
        }
    }

    @Override
    public WXOrderDTO cancel(String orderId) {
//       取消订单
        try {
            OrderMaster orderMaster = new OrderMaster();
            WXOrderDTO orderDTO = this.findOne(orderId);
            //判断订单状态
            if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
                log.error("【取消订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
                throw RequestException.fail(ResponseCode.ORDER_STATUS_ERROR);
            }

            //修改订单状态
            orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
            BeanUtils.copyProperties(orderDTO, orderMaster);
            masterService.insertOrderMaster(orderMaster);
            //返回库存
            if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
                log.error("【取消订单】订单中无商品详情, orderDTO={}", orderDTO);
                throw RequestException.fail(ResponseCode.ORDER_DETAIL_EMPTY);
            }
            List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                    .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                    .collect(Collectors.toList());
            productInfoService.increaseStock(cartDTOList);
            //如果已支付, 需要退款 TODO
            if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
//                payService.refund(orderDTO);
            }
            return orderDTO;
        } catch (Exception e) {
            throw RequestException.fail("取消订单失败", e);
        }
    }

    @Override
    public WXOrderDTO finish(String orderId) {
//        完结订单 后台成功接单 打印小票后调用
        try {
            //判断订单状态
            WXOrderDTO orderDTO = this.findOne(orderId);
            if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
                log.error("【完结订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
                throw RequestException.fail(ResponseCode.ORDER_STATUS_ERROR);
            }
            //修改订单状态
            orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
            OrderMaster orderMaster = new OrderMaster();
            BeanUtils.copyProperties(orderDTO, orderMaster);
            masterService.insertOrderMaster(orderMaster);
            //推送微信模版消息  TODO
//            pushMessageService.orderStatus(orderDTO);
            return orderDTO;
        } catch (Exception e) {
            throw RequestException.fail("完成订单失败", e);
        }
    }

    @Override
    public WXOrderDTO paid(String orderId) {
//        支付订单
        try {
            //判断订单状态
            WXOrderDTO orderDTO = this.findOne(orderId);
            if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
                log.error("【订单支付完成】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
                throw RequestException.fail(ResponseCode.ORDER_STATUS_ERROR);
            }
            //判断支付状态
            if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
                log.error("【订单支付完成】订单支付状态不正确, orderDTO={}", orderDTO);
                throw RequestException.fail(ResponseCode.ORDER_PAY_STATUS_ERROR);
            }

            //修改支付状态
            orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
            OrderMaster orderMaster = new OrderMaster();
            BeanUtils.copyProperties(orderDTO, orderMaster);
            masterService.insertOrderMaster(orderMaster);
            return orderDTO;
        } catch (Exception e) {
            throw RequestException.fail("订单支付完成失败", e);
        }
    }


}
