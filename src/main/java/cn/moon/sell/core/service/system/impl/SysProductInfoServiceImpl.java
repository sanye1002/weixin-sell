package cn.moon.sell.core.service.system.impl;

import cn.moon.sell.common.bean.ResponseCode;
import cn.moon.sell.common.exception.RequestException;
import cn.moon.sell.common.util.List2StringUtil;
import cn.moon.sell.core.converter.Integer2BoolConverter;
import cn.moon.sell.core.converter.ProductInfo2ProductInfoDTOConverter;
import cn.moon.sell.core.dto.order.CartDTO;
import cn.moon.sell.core.dto.product.PageProductInfoByCategoryIdDTO;
import cn.moon.sell.core.dto.product.ProductInfoDTO;
import cn.moon.sell.core.dto.product.ProductStandardDTO;
import cn.moon.sell.core.entity.ProductInfo;
import cn.moon.sell.core.mapper.ProductInfoMapper;
import cn.moon.sell.core.service.system.SysProductInfoService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author zhaoxiang
 * @Date 2019/01/04
 * @Desc
 */
@Service
@Transactional
public class SysProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements SysProductInfoService {
    @Override
    public void insertProductInfo(ProductInfoDTO productInfoDTO) {
        ProductInfo productInfo = new ProductInfo();
        if (productInfoDTO.getStandardStatus()) {
//            有规格判断规格
            if (productInfoDTO.getProductStandard().getStandardList().isEmpty()) {
                throw RequestException.fail(ResponseCode.PARAM_ERROR);
            }
        } else {
            if (productInfoDTO.getProductPrice() == null) {
                throw RequestException.fail("单价必须传入");
            }
            if (!productInfoDTO.getUnlimitedStock() && productInfoDTO.getProductStock() == null) {
                throw RequestException.fail("库存必须填");
            }
        }
        try {
            //            查找是否还有同名
            ProductInfo dbProduct = this.selectOne(new EntityWrapper<ProductInfo>()
                    .eq("product_name", productInfoDTO.getProductName()));
            if (dbProduct != null) {
                throw RequestException.fail(String.format("已经存在 %s 的商品了", productInfoDTO.getProductName()));
            }
            productInfo = ProductInfo2ProductInfoDTOConverter.reverse(productInfoDTO);
            //            保存
            this.insert(productInfo);

        } catch (Exception e) {
            throw RequestException.fail("添加商品详情失败", e);
        }
    }

    @Override
    public Page<ProductInfoDTO> selectPageProductByCategoryType(PageProductInfoByCategoryIdDTO pageProductInfoByCategoryIdDTO) {
        Page<ProductInfoDTO> dtoPage = new Page<>();
        Page<ProductInfo> productInfoPage = new Page<>(pageProductInfoByCategoryIdDTO.getPage()
                , pageProductInfoByCategoryIdDTO.getPageSize());
        EntityWrapper<ProductInfo> wrapper = new EntityWrapper<>();
        wrapper.orderBy(pageProductInfoByCategoryIdDTO.getOrderByColumns(), pageProductInfoByCategoryIdDTO.getAsc());
        if (pageProductInfoByCategoryIdDTO.getCategoryType() != null) {
            wrapper.eq("category_type", pageProductInfoByCategoryIdDTO.getCategoryType());
        }
        if (pageProductInfoByCategoryIdDTO.getProductStatus() != null) {
            wrapper.eq("category_type", pageProductInfoByCategoryIdDTO.getProductStatus());
        }
        wrapper.orderBy(pageProductInfoByCategoryIdDTO.getOrderByColumns(), pageProductInfoByCategoryIdDTO.getAsc());
        try {
            productInfoPage = this.selectPage(productInfoPage, wrapper);
            BeanUtils.copyProperties(productInfoPage, dtoPage);
            List<ProductInfoDTO> dtoList = new ArrayList<>();
            if (!productInfoPage.getRecords().isEmpty()) {
                dtoList = productInfoPage.getRecords().stream().map(e -> {
                    return ProductInfo2ProductInfoDTOConverter.converter(e);
                }).collect(Collectors.toList());
            }
            dtoPage.setRecords(dtoList);
            return dtoPage;
        } catch (Exception e) {
            throw RequestException.fail("查找商品失败", e);
        }
    }

    @Override
    public void deleteProductByIds(List<String> ids) {
        try {
            this.deleteBatchIds(ids);
        } catch (Exception e) {
            throw RequestException.fail("删除商品失败", e);
        }
    }

    @Override
    public void updateProductInfo(ProductInfoDTO productInfoDTO) {
        //判断商品名是否重复
        EntityWrapper<ProductInfo> flagWrapper = new EntityWrapper<>();
        flagWrapper.eq("product_name", productInfoDTO.getProductName())
                .ne("product_id", productInfoDTO.getProductId());
        ProductInfo dbProduct = null;
        try {
            dbProduct = this.selectOne(flagWrapper);
        } catch (Exception e) {
            throw RequestException.fail("商品名是否重复判断失败", e);
        }
        if (dbProduct != null) {
            throw RequestException.fail(String.format("已经存在【%s】的商品了", dbProduct.getProductName()));
        }
        if (productInfoDTO.getProductId() == null) {
            throw RequestException.fail("商品ID未传入");
        }
        try {
            ProductInfo productInfo = this.selectById(productInfoDTO.getProductId());
            BeanUtils.copyProperties(productInfoDTO, productInfo);
            productInfo.setUpdateTime(new Date());
            this.updateById(productInfo);
        } catch (Exception e) {
            throw RequestException.fail("更新商品失败", e);
        }
    }

    @Override
    public void updateProductStatus(String productId, Integer status) {
        try {
            this.baseMapper.updateProductStatus(status, productId);
        } catch (Exception e) {
            throw RequestException.fail("修改商品状态失败", e);
        }
    }

    @Override
    public void updateProductStock(String productId, Integer stockNum) {
        try {
            this.baseMapper.updateProductStock(stockNum, productId);
        } catch (Exception e) {
            throw RequestException.fail("修改商品库存失败", e);
        }
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = this.selectById(cartDTO.getProductId());
            if (productInfo == null) {
                throw RequestException.fail(ResponseCode.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getNumber();
            productInfo.setProductStock(result);
            Integer resultSales = productInfo.getSalesVolume() - cartDTO.getNumber();
            if (resultSales < 0) {
                resultSales = 0;
            }
            productInfo.setSalesVolume(resultSales);
            try {
                if (!this.updateById(productInfo)) {
                    throw RequestException.fail(ResponseCode.NETWORK_ERROR);
                }
            } catch (Exception e) {
                throw RequestException.fail("入库异常,请重试", e);
            }
        }
    }

    @Override
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = this.selectById(cartDTO.getProductId());
            if (productInfo == null) {
                throw RequestException.fail(ResponseCode.PRODUCT_NOT_EXIST);
            }

            Integer resultStock = productInfo.getProductStock() - cartDTO.getNumber();
            if (resultStock < 0) {
                throw RequestException.fail(ResponseCode.PRODUCT_STOCK_ERROR);
            }
            Integer resultSales = productInfo.getSalesVolume() + cartDTO.getNumber();
            productInfo.setProductStock(resultStock);
            productInfo.setSalesVolume(resultSales);

            try {
                if (!this.insert(productInfo)) {
                    throw RequestException.fail(ResponseCode.NETWORK_ERROR);
                }
            } catch (Exception e) {
                throw RequestException.fail("出库异常,请重试", e);
            }
        }
    }

    @Override
    public void onSale(String productId) {
        try {
            this.baseMapper.updateProductStatus(0, productId);
        } catch (Exception e) {
            throw RequestException.fail("上架失败,请重试", e);
        }
    }

    @Override
    public void offSale(String productId) {
        try {
            this.baseMapper.updateProductStatus(1, productId);
        } catch (Exception e) {
            throw RequestException.fail("下架失败,请重试", e);
        }
    }

    @Override
    public ProductInfoDTO findOne(String productId) {
        if (StringUtils.isEmpty(productId)) {
            throw RequestException.fail(ResponseCode.PARAM_ERROR);
        }
        try {
            ProductInfo productInfo = this.selectById(productId);
            if (productInfo == null) {
                throw RequestException.fail(ResponseCode.PRODUCT_NOT_EXIST);
            }
            return ProductInfo2ProductInfoDTOConverter.converter(productInfo);
        } catch (Exception e) {
            throw RequestException.fail("查询商品失败", e);
        }
    }

    @Override
    public Page<ProductInfoDTO> selectPageByRecommendStatus(PageProductInfoByCategoryIdDTO pageProductInfoByCategoryIdDTO) {
        Page<ProductInfoDTO> dtoPage = new Page<>();
        Page<ProductInfo> productInfoPage = new Page<>(pageProductInfoByCategoryIdDTO.getPage()
                , pageProductInfoByCategoryIdDTO.getPageSize());
        EntityWrapper<ProductInfo> wrapper = new EntityWrapper<>();
        wrapper.orderBy(pageProductInfoByCategoryIdDTO.getOrderByColumns(), pageProductInfoByCategoryIdDTO.getAsc());
        if (pageProductInfoByCategoryIdDTO.getRecommendStatus() != -1 && pageProductInfoByCategoryIdDTO.getRecommendStatus() != null) {
            wrapper.eq("recommend_status", pageProductInfoByCategoryIdDTO.getRecommendStatus());
        }
        wrapper.orderBy(pageProductInfoByCategoryIdDTO.getOrderByColumns(), pageProductInfoByCategoryIdDTO.getAsc());
        try {
            productInfoPage = this.selectPage(productInfoPage, wrapper);
            BeanUtils.copyProperties(productInfoPage, dtoPage);
            List<ProductInfoDTO> dtoList = new ArrayList<>();
            if (!productInfoPage.getRecords().isEmpty()) {
                dtoList = productInfoPage.getRecords().stream().map(e -> {
                    return ProductInfo2ProductInfoDTOConverter.converter(e);
                }).collect(Collectors.toList());
            }
            dtoPage.setRecords(dtoList);
            return dtoPage;
        } catch (Exception e) {
            throw RequestException.fail("查找商品失败", e);
        }

    }

    @Override
    public void onRecommend(List<String> ids) {
        String id = List2StringUtil.stringFormat(ids);
        this.baseMapper.updateRecommendByIds(1,ids);
    }

    @Override
    public void offRecommend(List<String> ids) {
        String id = List2StringUtil.stringFormat(ids);
        this.baseMapper.updateRecommendByIds(0,ids);
    }

    @Override
    public List<ProductInfo> selectListAll() {
        return null;
    }
}
