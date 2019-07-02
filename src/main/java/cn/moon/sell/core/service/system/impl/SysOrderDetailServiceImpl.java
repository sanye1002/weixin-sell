package cn.moon.sell.core.service.system.impl;

import cn.moon.sell.common.exception.RequestException;
import cn.moon.sell.core.entity.OrderDetail;
import cn.moon.sell.core.mapper.OrderDetailMapper;
import cn.moon.sell.core.service.system.SysOrderDetailService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zhaoxiang
 * @Date 2019/01/04
 * @Desc
 */
@Service
public class SysOrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements SysOrderDetailService {

    @Override
    public void insertOrderDetail(OrderDetail orderDetail) {
        try {
            if (orderDetail != null) {
                this.insert(orderDetail);
            }
        } catch (Exception e) {
            throw RequestException.fail("订单详情保存失败", e);
        }
    }

    @Override
    public List<OrderDetail> listOrderDetailByOrderId(String orderId) {
        try {
            EntityWrapper<OrderDetail> wrapper = new EntityWrapper<>();
            wrapper.eq("order_id", orderId)
                    .orderBy("update_time", false);

            return this.selectList(wrapper);
        } catch (Exception e) {
            throw RequestException.fail("订单详情保存失败", e);
        }

    }
}
