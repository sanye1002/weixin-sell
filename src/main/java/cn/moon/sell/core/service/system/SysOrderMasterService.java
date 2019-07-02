package cn.moon.sell.core.service.system;

import cn.moon.sell.core.dto.OrderByDTO;
import cn.moon.sell.core.dto.order.OrderDTO;
import cn.moon.sell.core.dto.order.PageOrderDTOByOpenid;
import cn.moon.sell.core.dto.order.SelectPageByOrderStatus;
import cn.moon.sell.core.dto.order.SysOrderDTO;
import cn.moon.sell.core.entity.OrderMaster;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.core.annotation.Order;

/**
 * @Author zhaoxiang
 * @Date 2019/01/04
 * @Desc 后台订单
 */
public interface SysOrderMasterService extends IService<OrderMaster> {

    /**
     * 分页查找所有订单
     * @param pageByOrderStatus
     * @return
     */
    Page<OrderMaster> selectPageByOrderStatus(SelectPageByOrderStatus pageByOrderStatus);

    /**
     * 查询订单
     * @param orderId
     * @return
     */
    OrderMaster selectOrderMasterById(String orderId);

    /**
     * 新加订单
     * @param orderMaster
     */
    void insertOrderMaster(OrderMaster orderMaster);

    /**
     * 分页查找用户订单
     * @param buyerOpenid
     * @param orderDTOByOpenid
     * @return
     */
    Page<OrderMaster> selectPageByOpenId(String buyerOpenid, PageOrderDTOByOpenid orderDTOByOpenid);

    /**
     * 后台创建订单
     * @param sysOrderDTO
     * @return
     */
    SysOrderDTO createOrder(SysOrderDTO sysOrderDTO);

    /**
     * 完成订单
     * @param sysOrderDTO
     */
    void finish(SysOrderDTO sysOrderDTO);

    /**
     * 取消订单
     * @param sysOrderDTO
     */
    void cancel(SysOrderDTO sysOrderDTO);
}
