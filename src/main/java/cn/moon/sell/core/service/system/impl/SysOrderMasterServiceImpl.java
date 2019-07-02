package cn.moon.sell.core.service.system.impl;

import cn.moon.sell.common.bean.OrderStatusEnum;
import cn.moon.sell.common.bean.PayStatusEnum;
import cn.moon.sell.common.bean.ResponseCode;
import cn.moon.sell.common.exception.RequestException;
import cn.moon.sell.common.util.KeyUtil;
import cn.moon.sell.core.dto.order.*;
import cn.moon.sell.core.entity.OrderDetail;
import cn.moon.sell.core.entity.OrderMaster;
import cn.moon.sell.core.entity.ProductInfo;
import cn.moon.sell.core.mapper.OrderMasterMapper;
import cn.moon.sell.core.service.system.SysOrderDetailService;
import cn.moon.sell.core.service.system.SysOrderMasterService;
import cn.moon.sell.core.service.system.SysProductInfoService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class SysOrderMasterServiceImpl extends ServiceImpl<OrderMasterMapper, OrderMaster> implements SysOrderMasterService {
    @Autowired
    private SysProductInfoService productInfoService;
    @Autowired
    private SysOrderDetailService detailService;
    @Override
    public Page<OrderMaster> selectPageByOrderStatus(SelectPageByOrderStatus pageByOrderStatus) {
        try {
            Page<OrderMaster> page = new Page<>(pageByOrderStatus.getPage(), pageByOrderStatus.getPageSize());
            EntityWrapper<OrderMaster> wrapper = new EntityWrapper<>();
            wrapper.orderBy(pageByOrderStatus.getOrderByColumns(), pageByOrderStatus.getAsc());
            if (pageByOrderStatus.getOrderStatus() != null) {
                wrapper.eq("order_status", pageByOrderStatus.getOrderStatus());
            }
            return this.selectPage(page, wrapper);
        } catch (Exception e) {
            throw RequestException.fail("订单查询失败，请重试~", e);
        }
    }

    @Override
    public OrderMaster selectOrderMasterById(String orderId) {
        try {
            return this.selectById(orderId);
        } catch (Exception e) {
            throw RequestException.fail("订单查询失败，请重试~", e);
        }

    }

    @Override
    public void insertOrderMaster(OrderMaster orderMaster) {
        try {
            this.insert(orderMaster);
        } catch (Exception e) {
            throw RequestException.fail("订单保存失败，请重新下单~", e);
        }
    }

    @Override
    public Page<OrderMaster> selectPageByOpenId(String buyerOpenid, PageOrderDTOByOpenid orderDTOByOpenid) {
        try {
            Page<OrderMaster> page = new Page<>(orderDTOByOpenid.getPage(), orderDTOByOpenid.getPageSize());
            EntityWrapper<OrderMaster> wrapper = new EntityWrapper<>();
            wrapper.eq("buyer_openid", orderDTOByOpenid.getOpenid())
                    .orderBy(orderDTOByOpenid.getOrderByColumns(), orderDTOByOpenid.getAsc());

            return this.selectPage(page, wrapper);
        } catch (Exception e) {
            throw RequestException.fail("订单查询失败，请重试~", e);
        }

    }

    @Override
    public SysOrderDTO createOrder(SysOrderDTO sysOrderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        try {
            if (sysOrderDTO.getOrderDetailList().isEmpty()) {
                throw RequestException.fail(ResponseCode.CART_EMPTY);
            }
//            1 . 处理订单详情
            for (OrderDetail orderDetail : sysOrderDTO.getOrderDetailList()) {
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
            sysOrderDTO.setOrderId(orderId);
            BeanUtils.copyProperties(sysOrderDTO, orderMaster);
            orderMaster.setOrderAmount(orderAmount);
            orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
            orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
            this.insert(orderMaster);
//            3 . 扣库存
            List<CartDTO> cartDTOList = sysOrderDTO.getOrderDetailList().stream().map(e ->
                    new CartDTO(e.getProductId(), e.getProductQuantity())
            ).collect(Collectors.toList());
            productInfoService.decreaseStock(cartDTOList);
//            4 . 发送websocket消息 TODO
//            5 . 打印小票 TODO
            return sysOrderDTO;
        } catch (Exception e) {
            throw RequestException.fail(ResponseCode.NETWORK_ERROR);
        }
    }

    @Override
    public void finish(SysOrderDTO sysOrderDTO) {

    }

    @Override
    public void cancel(SysOrderDTO sysOrderDTO) {

    }


}
