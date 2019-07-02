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
public class SysOrderDTO extends OrderDTO{
    /** 买家地址. */
    @NotBlank(message = "请输入买家地址")
    private String buyerAddress;

    /** 买家名字. */
    private String buyerName="收银台点餐";


}
