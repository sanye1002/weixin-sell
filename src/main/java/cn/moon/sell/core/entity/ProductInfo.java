package cn.moon.sell.core.entity;

import cn.moon.sell.common.bean.ProductStatusEnum;
import com.baomidou.mybatisplus.annotations.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author zhaoxiang
 * @Date 2019/01/03
 * @Desc
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfo implements Serializable {
    private static final long serialVersionUID = -8639134362545920419L;
    @TableId
    private String productId;
    /** 名字. */
    private String productName;
    /** 单价. */
    private BigDecimal productPrice;
    /** 原价. */
    private BigDecimal originalPrice;
    /** 库存. */
    private Integer productStock;
    /** 销量 */
    private Integer salesVolume;
    /** 是否有规格 0无 */
    private Integer standardStatus;
    /** 无限库存状态 0==true无限 1. */
    private Integer unlimitedStock;
    /** 规格. */
    private String standardObject;
    /** 描述. */
    private String productDescription;
    /** 小图. */
    private String productIcon;
    /** 状态, 0正常1下架. */
    private Integer productStatus = ProductStatusEnum.UP.getCode();
    /** 推荐状态, 0正常1下架. */
    private Integer recommendStatus;
    /** 类目编号. */
    private Integer categoryType;
    private Date createTime;

    private Date updateTime;
}
