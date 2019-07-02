package cn.moon.sell.core.dto;

import lombok.Data;

/**
 * @author Licoy
 * @version 2018/4/18/14:17
 */
@Data
public abstract class SplitPageDTO extends OrderByDTO{

    private Integer page = 1;

    private Integer pageSize = 10;

}
