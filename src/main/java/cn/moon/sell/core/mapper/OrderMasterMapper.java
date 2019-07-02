package cn.moon.sell.core.mapper;

import cn.moon.sell.core.entity.OrderMaster;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;

/**
 * @Author zhaoxiang
 * @Date 2019/01/03
 * @Desc
 */
@Mapper
@Resource
public interface OrderMasterMapper extends BaseMapper<OrderMaster> {
}
