package cn.moon.sell.core.mapper;

import cn.moon.sell.core.entity.ProductCategory;
import cn.moon.sell.core.entity.ProductInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author zhaoxiang
 * @Date 2019/01/03
 * @Desc
 */
@Mapper
@Resource
public interface ProductInfoMapper extends BaseMapper<ProductInfo> {
    @Update("UPDATE `product_info` SET `product_status` = #{status} WHERE `product_id` = #{productId}")
    void updateProductStatus(@Param(value = "status") Integer status, @Param(value = "productId") String productId);

    @Update("UPDATE `product_info` SET `product_stock` = #{stockNum} WHERE `product_id` = #{productId}")
    void updateProductStock(@Param(value = "stockNum") Integer stockNum, @Param(value = "productId") String productId);

    @Update("UPDATE `product_info` SET `recommend_status` = #{status} WHERE `product_id` in (#{ids})")
    void updateRecommendStatus(@Param(value = "status") Integer status, @Param(value = "ids") String ids);

    void updateRecommendByIds(@Param(value = "status") Integer status, @Param(value = "ids") List<String> ids);
}
