package cn.moon.sell.core.dto.order;

import cn.moon.sell.common.bean.OrderStatusEnum;
import cn.moon.sell.common.bean.PayStatusEnum;
import cn.moon.sell.common.util.EnumUtil;
import cn.moon.sell.common.util.serializer.Date2LongSerializer;
import cn.moon.sell.core.dto.product.ProductStandardDTO;
import cn.moon.sell.core.entity.OrderDetail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author zhaoxiang
 * @Date 2019/01/04
 * @Desc
 */
@Data
public class OrderDTO {
    /** 订单id. */
    private String orderId;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus;

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus;

    /** 创建时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 更新时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList;
     /** 规格 */
    List<ProductStandardDTO> standardDTOList;
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
