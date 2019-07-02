package cn.moon.sell.core.dto.order;

import cn.moon.sell.core.dto.SplitPageDTO;
import lombok.Data;

/**
 * @Author zhaoxiang
 * @Date 2019/01/04
 * @Desc
 */
@Data
public class SelectPageByOrderStatus extends SplitPageDTO {
    //  orderStatus == null 查询全部
    private Integer orderStatus;
}
