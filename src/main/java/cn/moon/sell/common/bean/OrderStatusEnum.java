package cn.moon.sell.common.bean;

import lombok.Getter;

/**
 * @Author zhaoxiang
 * @Date 2019/01/03
 * @Desc 订单状态
 */
@Getter
public enum OrderStatusEnum implements CodeEnum{
    NEW(0, "新订单"),
    FINISHED(1, "完结"),
    CANCEL(2, "已取消"),
    ;

    private Integer code;
    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
