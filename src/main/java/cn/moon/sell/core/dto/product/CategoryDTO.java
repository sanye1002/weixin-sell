package cn.moon.sell.core.dto.product;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @Author zhaoxiang
 * @Date 2019/01/03
 * @Desc 传入商品类目数据
 */
@Data
public class CategoryDTO {

    private Integer categoryId;

    /** 类目名字. */
    @NotBlank(message = "类目名字不能为空")
    private String categoryName;

    /** 类目编号. */
    @NotNull(message = "类目编号不能为空")
    private Integer categoryType;
}
