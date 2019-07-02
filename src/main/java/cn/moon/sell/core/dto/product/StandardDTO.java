package cn.moon.sell.core.dto.product;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author zhaoxiang
 * @Date 2019/01/14
 * @Desc
 */
@Data
public class StandardDTO implements Serializable {
    private static final long serialVersionUID = -348020951706708573L;
    //    name
    private String name;
    //  现价
    private BigDecimal productPrice;
    //    原价
    private BigDecimal originalPrice;
    //    不限库存状态
    private Boolean stockUnlimited;
    //    库存
    private Integer productStock;
}
