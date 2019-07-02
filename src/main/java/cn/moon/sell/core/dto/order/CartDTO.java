package cn.moon.sell.core.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zhaoxiang
 * @Date 2019/01/04
 * @Desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {
//    物品ID
    private String productId;
//    数量
    private Integer number;
}
