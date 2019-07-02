package cn.moon.sell.core.dto.product;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author zhaoxiang
 * @Date 2019/01/12
 * @Desc 商品规格
 */
@Data
public class ProductStandardDTO implements Serializable {
    private static final long serialVersionUID = 6530203821643117492L;
//  价格全选
    private Boolean productPriceAll;
//  库存无限
    private Boolean unlimitedStock;
//    原价
    private List<StandardDTO> standardList;
}
