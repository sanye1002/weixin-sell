package cn.moon.sell.core.dto;

import lombok.Data;

/**
 * @Author zhaoxiang
 * @Date 2019/01/04
 * @Desc
 */
@Data
public abstract class OrderByDTO {

    private Boolean asc = false;

    private String orderByColumns = "update_time";
}
