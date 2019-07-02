package cn.moon.sell.core.service.system.impl;

import cn.moon.sell.common.exception.RequestException;
import cn.moon.sell.core.converter.CategoryNameConverter;
import cn.moon.sell.core.dto.product.CategoryDTO;
import cn.moon.sell.core.entity.ProductCategory;
import cn.moon.sell.core.entity.ProductInfo;
import cn.moon.sell.core.mapper.ProductCategoryMapper;
import cn.moon.sell.core.service.system.SysProductCategoryService;
import cn.moon.sell.core.service.system.SysProductInfoService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author zhaoxiang
 * @Date 2019/01/03
 * @Desc
 */
@Service
@Transactional
public class SysProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements SysProductCategoryService {

    @Autowired
    private SysProductInfoService productInfoService;

    @Override
    public void insertCategory(CategoryDTO categoryDTO) {
        ProductCategory productCategory = new ProductCategory(categoryDTO.getCategoryName(), categoryDTO.getCategoryType());
        try {
            this.insert(productCategory);
        } catch (Exception e) {
            throw RequestException.fail("添加商品类目失败", e);
        }

    }

    @Override
    public List<ProductCategory> listAllProductCategory() {
        EntityWrapper<ProductCategory> wrapper = new EntityWrapper<>();
        wrapper.orderBy("update_time", false);
        try {
            return this.selectList(wrapper);
        } catch (Exception e) {
            throw RequestException.fail("查询商品类目失败", e);
        }
    }

    @Override
    public void deleteCategoryById(List<Integer> categoryType) {
        List<ProductInfo> productInfoList = null;
        try {
            EntityWrapper<ProductInfo> wrapper = new EntityWrapper<>();
            wrapper.in("category_type",categoryType);
            productInfoList = productInfoService.selectList(wrapper);
        } catch (Exception e) {
            throw RequestException.fail("删除商品类目失败", e);
        }
        if (productInfoList.isEmpty()) {
            try {
                EntityWrapper<ProductCategory> wrapper = new EntityWrapper<>();
                wrapper.in("category_type",categoryType);
                this.delete(wrapper);
            } catch (Exception e) {
                throw RequestException.fail("删除商品类目失败", e);
            }
        } else {
            throw RequestException.fail(String.format(" %s ", CategoryNameConverter.converter(productInfoList)));
        }
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        if (categoryDTO.getCategoryId() == null) {
            throw RequestException.fail("类目ID未传入");
        }
        try {
            ProductCategory productCategory = this.selectById(categoryDTO.getCategoryId());
            BeanUtils.copyProperties(categoryDTO, productCategory);
            productCategory.setUpdateTime(new Date());
            this.updateById(productCategory);
        } catch (Exception e) {
            throw RequestException.fail("更新商品类目失败", e);
        }
    }
}
