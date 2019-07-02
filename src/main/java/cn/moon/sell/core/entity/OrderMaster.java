package cn.moon.sell.core.entity;

import cn.moon.sell.common.bean.OrderStatusEnum;
import cn.moon.sell.common.bean.PayStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author zhaoxiang
 * @Date 2019/01/03
 * @Desc 订单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderMaster implements Serializable {

    private static final long serialVersionUID = -3188534291039506820L;
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    /**  备注*/
    private String remark;
    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;
}
