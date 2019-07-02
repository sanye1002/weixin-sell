package cn.moon.sell.core.controller.system;

import cn.moon.sell.common.annotation.SysLogs;
import cn.moon.sell.common.bean.ResponseCode;
import cn.moon.sell.common.bean.ResponseResult;
import cn.moon.sell.core.dto.product.CategoryDTO;
import cn.moon.sell.core.service.system.SysProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author zhaoxiang
 * @Date 2019/01/08
 * @Desc
 */
@RestController
@RequestMapping(value = "/system/productCategory")
@Api(tags = {"商品类目管理"})
public class ProductCategoryController {

    @Autowired
    private SysProductCategoryService categoryService;

    @PostMapping(value = {"/add"})
    @ApiOperation(value = "商品类目添加")
    @SysLogs("商品类目添加")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult add(@RequestBody @Validated @ApiParam("商品类目添加数据") CategoryDTO dto) {
        categoryService.insertCategory(dto);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/list"})
    @ApiOperation(value = "商品类目查询")
    @SysLogs("商品类目查询")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult list() {
        return ResponseResult.e(ResponseCode.OK, categoryService.listAllProductCategory());
    }

    @PostMapping(value = {"/update"})
    @ApiOperation(value = "商品类目修改")
    @SysLogs("商品类目修改")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult update(@RequestBody @Validated @ApiParam("商品类目修改数据必须传入ID") CategoryDTO dto) {
        categoryService.updateCategory(dto);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/delete"})
    @ApiOperation(value = "商品类目删除")
    @SysLogs("商品类目删除")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult delete(@RequestBody @ApiParam("商品类目ID数组") List<Integer> ids) {
        categoryService.deleteCategoryById(ids);
        return ResponseResult.e(ResponseCode.OK);
    }

}
