package cn.moon.sell.core.dto.product;

import cn.moon.sell.core.dto.SplitPageDTO;
import lombok.Data;

/**
 * @Author zhaoxiang
 * @Date 2019/01/03
 * @Desc
 */
@Data
public class PageProductInfoByCategoryIdDTO extends SplitPageDTO {
    // 类型编号
    private Integer categoryType;
    // 状态
    private Integer productStatus;

    private Integer recommendStatus;

}
