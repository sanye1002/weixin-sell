package cn.moon.sell.core.converter;

import cn.moon.sell.core.dto.product.ProductInfoDTO;
import cn.moon.sell.core.dto.product.ProductStandardDTO;
import cn.moon.sell.core.entity.ProductInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;

/**
 * @Author zhaoxiang
 * @Date 2019/01/14
 * @Desc
 */
public class ProductInfo2ProductInfoDTOConverter {
    public static ProductInfoDTO converter(ProductInfo productInfo) {
        ProductInfoDTO productInfoDTO = new ProductInfoDTO();
        BeanUtils.copyProperties(productInfo,productInfoDTO);
        productInfoDTO.setStandardStatus(Integer2BoolConverter.converter(productInfo.getProductStock()));
        productInfoDTO.setUnlimitedStock(Integer2BoolConverter.converter(productInfo.getUnlimitedStock()));
        productInfoDTO.setProductStandard(JSON.parseObject(productInfo.getStandardObject(),ProductStandardDTO.class));
        return productInfoDTO;
    }
    public static ProductInfo reverse(ProductInfoDTO dto) {
        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(dto, productInfo);
        productInfo.setUnlimitedStock(Integer2BoolConverter.reverse(dto.getUnlimitedStock()));
        productInfo.setStandardStatus(Integer2BoolConverter.reverse(dto.getStandardStatus()));
        productInfo.setStandardObject(JSONObject.toJSONString(dto.getProductStandard()));
        return productInfo;
    }
}
