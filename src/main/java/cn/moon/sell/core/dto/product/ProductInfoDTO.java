package cn.moon.sell.core.dto.product;

import cn.moon.sell.common.bean.ProductStatusEnum;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author zhaoxiang
 * @Date 2019/01/03
 * @Desc 传入商品详情数据
 */
@Data
public class ProductInfoDTO {

    private String productId;

    /**
     * 名字.
     */
    @NotBlank(message = "名称不能为空")
    private String productName;
    /**
     * 单价.
     */
    private BigDecimal productPrice;
    /**
     * 库存.
     */
    private Integer productStock;
    /**
     * 描述.
     */
    @NotBlank(message = "描述不能为空")
    private String productDescription;

    /**
     * 小图.
     */
    @NotBlank(message = "小图必须上传")
    private String productIcon;

    /** 类目编号.*/
    @NotNull(message = "类目编号必须选择")
    private Integer categoryType;
    /** 类目编号.*/
    private BigDecimal originalPrice;
    /** 是否有规格 0无.*/
    private Boolean standardStatus;
    /** 无限库存状态 0==true无限 1.*/
    private Boolean unlimitedStock;
    /** 规格.*/
    private ProductStandardDTO productStandard;
    private Integer salesVolume;
    /** 状态, 0正常1下架.*/
    private Integer productStatus;
    /** 推荐状态, 0正常1下架. */
    private Integer recommendStatus;
    private Date createTime;

    private Date updateTime;


}
