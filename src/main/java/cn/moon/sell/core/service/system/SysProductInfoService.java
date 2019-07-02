package cn.moon.sell.core.service.system;

import cn.moon.sell.core.dto.order.CartDTO;
import cn.moon.sell.core.dto.product.PageProductInfoByCategoryIdDTO;
import cn.moon.sell.core.dto.product.ProductInfoDTO;
import cn.moon.sell.core.entity.ProductInfo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * @Author zhaoxiang
 * @Date 2019/01/03
 * @Desc 后台商品service
 */
public interface SysProductInfoService extends IService<ProductInfo> {

    /**
     * 添加商品详情
     * @param productInfoDTO 商品详情数据
     */
    void insertProductInfo(ProductInfoDTO productInfoDTO);

    /**
     * 分页
     * @param pageProductInfoByCategoryIdDTO
     * @return
     */
    Page<ProductInfoDTO> selectPageProductByCategoryType(PageProductInfoByCategoryIdDTO pageProductInfoByCategoryIdDTO);

    /**
     * 删除ids
     * @param ids
     */
    void deleteProductByIds(List<String> ids);

    /**
     * 更新商品详情
     * @param productInfoDTO
     */
    void updateProductInfo(ProductInfoDTO productInfoDTO);

    /**
     * 修改商品状态
     * @param productId
     * @param status
     */
    void updateProductStatus(String productId,Integer status);

    /**
     * 修改商品库存
     * @param productId
     * @param stockNum
     */
    void updateProductStock(String productId,Integer stockNum);

    /**
     * 加库存
     * @param cartDTOList
     */
    void increaseStock(List<CartDTO> cartDTOList);

    /**
     * 减库存
     * @param cartDTOList
     */
    void decreaseStock(List<CartDTO> cartDTOList);

    /**
     * 上架
     * @param productId
     * @return
     */
    void onSale(String productId);

    /**
     * 下架
     * @param productId
     * @return
     */
    void offSale(String productId);

    /**
     * 商品查找
     * @param productId
     * @return
     */
    ProductInfoDTO findOne(String productId);

    /**
     * 分页查找推荐商品
     * @param pageProductInfoByCategoryIdDTO
     * @return
     */
    Page<ProductInfoDTO> selectPageByRecommendStatus(PageProductInfoByCategoryIdDTO pageProductInfoByCategoryIdDTO);


    /**
     * 列表上推荐
     * @param ids
     */
    void onRecommend(List<String> ids);

    /**
     * 列表下推荐
     * @param ids
     */
    void offRecommend(List<String> ids);

    /**
     * 查询所有
     * @return
     */
    List<ProductInfo> selectListAll();

}
