package cn.moon.sell.common.bean;

import lombok.Getter;

/**
 * @Author zhaoxiang
 * @Date 2019/01/03
 * @Desc 商品状态
 */
@Getter
public enum ProductStatusEnum implements CodeEnum{
    UP(0, "在架"),
    DOWN(1, "下架")
    ;
    private Integer code;
    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
