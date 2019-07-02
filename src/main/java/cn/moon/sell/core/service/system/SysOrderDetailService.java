package cn.moon.sell.core.service.system;

import cn.moon.sell.core.entity.OrderDetail;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * @Author zhaoxiang
 * @Date 2019/01/04
 * @Desc
 */
public interface SysOrderDetailService extends IService<OrderDetail> {
    /**
     * 新增
     * @param orderDetail
     */
    void insertOrderDetail(OrderDetail orderDetail);

    List<OrderDetail> listOrderDetailByOrderId(String orderId);
}
