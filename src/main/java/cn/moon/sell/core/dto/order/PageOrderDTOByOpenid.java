package cn.moon.sell.core.dto.order;

import cn.moon.sell.core.dto.SplitPageDTO;
import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Author zhaoxiang
 * @Date 2019/01/04
 * @Desc
 */
@Data
public class PageOrderDTOByOpenid extends SplitPageDTO {

    @NotBlank(message = "用户微信openid不能为空")
    private String openid;
}
