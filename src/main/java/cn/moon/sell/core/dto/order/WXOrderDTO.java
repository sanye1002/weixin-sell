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
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author zhaoxiang
 * @Date 2019/01/04
 * @Desc
 */
@Data
public class WXOrderDTO extends OrderDTO{
    /** 买家地址. */
    @NotBlank(message = "订单位置不能为空，请重新扫码点餐")
    private String buyerAddress;

    /** 买家名字. */
    private String buyerName = "扫一扫点餐";

    /** 买家微信Openid. */
    @NotBlank(message = "买家微信信息不能为空，请重新授权")
    private String buyerOpenid;
}
