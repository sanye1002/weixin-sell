package cn.moon.sell.core.converter;

import cn.moon.sell.core.entity.ProductCategory;
import cn.moon.sell.core.entity.ProductInfo;

import java.util.List;

/**
 * @Author zhaoxiang
 * @Date 2019/01/08
 * @Desc
 */
public class CategoryNameConverter {

    public static String converter(List<ProductInfo> list){
        StringBuffer stringBuffer = new StringBuffer();
        if (!list.isEmpty()){
            list.forEach(e->{
                stringBuffer.append("类目编号:").append(e.getCategoryType()).append("存在商品").append("【")
                        .append(e.getProductName())
                        .append("】");
            });
        }
        return stringBuffer.toString();
    }

}
