package cn.moon.sell.core.service.system;

import cn.moon.sell.core.dto.product.CategoryDTO;
import cn.moon.sell.core.entity.ProductCategory;
import com.baomidou.mybatisplus.service.IService;


import java.util.List;

/**
 * @Author zhaoxiang
 * @Date 2019/01/03
 * @Desc 商家商品类目service
 */
public interface SysProductCategoryService extends IService<ProductCategory> {

    /**
     *  添加新的商品类目
     * @param categoryDTO 商品类目参数对象
     */
    void insertCategory(CategoryDTO categoryDTO);

    /**
     * 查询所有的类目
     * @return
     */
    List<ProductCategory> listAllProductCategory();

    /**
     * 删除
     * @param categoryType
     */
    void deleteCategoryById(List<Integer> categoryType);

    /**
     * 更新类目
     * @param categoryDTO
     */
    void updateCategory(CategoryDTO categoryDTO);
}
