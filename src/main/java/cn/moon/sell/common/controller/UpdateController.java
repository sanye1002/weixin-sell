package cn.moon.sell.common.controller;

import cn.moon.sell.common.annotation.SysLogs;
import cn.moon.sell.common.bean.ResponseCode;
import cn.moon.sell.common.bean.ResponseResult;
import cn.moon.sell.common.service.UpdateService;
import cn.moon.sell.common.annotation.SysLogs;
import cn.moon.sell.common.bean.ResponseCode;
import cn.moon.sell.common.bean.ResponseResult;
import cn.moon.sell.common.annotation.SysLogs;
import cn.moon.sell.common.bean.ResponseCode;
import cn.moon.sell.common.bean.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Licoy
 * @version 2018/5/25/13:27
 */
public interface UpdateController<UID,UD,S extends UpdateService<UID,UD>> {

    S getService();

    @PostMapping("/update/{id}")
    @ApiOperation(value = "更新指定ID对象的信息")
    @SysLogs("更新指定ID对象的信息")
    @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token",required = true)
    default ResponseResult update(@PathVariable("id") UID id, @RequestBody @Validated UD updateDTO){
        getService().update(id,updateDTO);
        return ResponseResult.e(ResponseCode.OK);
    }

}
