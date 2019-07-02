package cn.moon.sell.core.service.wx;

import cn.moon.sell.core.dto.order.CartDTO;
import cn.moon.sell.core.dto.order.OrderDTO;
import cn.moon.sell.core.dto.order.PageOrderDTOByOpenid;
import cn.moon.sell.core.dto.order.WXOrderDTO;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;

/**
 * @Author zhaoxiang
 * @Date 2019/01/04
 * @Desc 微信用户下单 查询service
 */
public interface WXOrderService {
    /**
     *  创建订单.
     * @param orderDTO
     * @return
     */
    WXOrderDTO create(WXOrderDTO orderDTO);

    /**
     * 查询单个订单
     * @param orderId
     * @return
     */
    WXOrderDTO findOne(String orderId);

    /**
     * 查询订单列表
     * @param buyerOpenid
     * @param orderDTOByOpenid
     * @return
     */
    Page<WXOrderDTO> findList(String buyerOpenid, PageOrderDTOByOpenid orderDTOByOpenid);

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    WXOrderDTO cancel(String orderId);

    /**
     * 完结订单
     * @param orderId
     * @return
     */
    WXOrderDTO finish(String orderId);

    /**
     * 支付订单
     * @param orderId
     * @return
     */
    WXOrderDTO paid(String orderId);

}
