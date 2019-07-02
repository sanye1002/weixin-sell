package cn.moon.sell.core.controller.system;

import cn.moon.sell.common.annotation.SysLogs;
import cn.moon.sell.common.bean.ResponseCode;
import cn.moon.sell.common.bean.ResponseResult;
import cn.moon.sell.core.dto.product.PageProductInfoByCategoryIdDTO;
import cn.moon.sell.core.dto.product.ProductInfoDTO;
import cn.moon.sell.core.service.system.SysProductInfoService;
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
 * @Desc 商品后台服务操作
 */
@RestController
@RequestMapping(value = "/system/productInfo")
@Api(tags = {"商品管理"})
public class ProductInfoController {

    @Autowired
    private SysProductInfoService productInfoService;

    @PostMapping(value = {"/add"})
    @ApiOperation(value = "商品添加")
    @SysLogs("商品添加")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult add(@RequestBody @Validated @ApiParam("商品添加数据") ProductInfoDTO dto) {
        productInfoService.insertProductInfo(dto);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/update"})
    @ApiOperation(value = "商品更新")
    @SysLogs("商品更新")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult update(@RequestBody @Validated @ApiParam("商品更新数据 ID必须传入") ProductInfoDTO dto) {
        productInfoService.updateProductInfo(dto);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/detail/{id}"})
    @ApiOperation(value = "商品详情查询")
    @SysLogs("商品详情查询")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult update(@PathVariable(value = "id") @Validated @ApiParam("商品ID") String productId) {
        return ResponseResult.e(ResponseCode.OK, productInfoService.findOne(productId));
    }

    @PostMapping(value = {"/delete"})
    @ApiOperation(value = "商品删除")
    @SysLogs("商品删除")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult delete(@RequestBody @ApiParam("商品ID数组") List<String> ids) {
        productInfoService.deleteProductByIds(ids);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/select/page/categoryType"})
    @ApiOperation(value = "商品分页查询")
    @SysLogs("商品分页查询")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult selectPageProductByCategoryType(@RequestBody @Validated @ApiParam("商品分页查询参数 categoryType等于null 查询所有 productStatus 同") PageProductInfoByCategoryIdDTO dto) {
        return ResponseResult.e(ResponseCode.OK, productInfoService.selectPageProductByCategoryType(dto));
    }

    @PostMapping(value = {"/select/page/recommendStatus"})
    @ApiOperation(value = "商品上下推荐查询")
    @SysLogs("商品分页查询")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult selectPageByRecommendStatus(@RequestBody @Validated @ApiParam("商品上下推荐查询参数 等于-1 查询所有 productStatus 同") PageProductInfoByCategoryIdDTO dto) {
        return ResponseResult.e(ResponseCode.OK, productInfoService.selectPageByRecommendStatus(dto));
    }

    @PostMapping(value = {"/updateStock"})
    @ApiOperation(value = "商品库存修改")
    @SysLogs("商品库存修改")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult updateProductStock(@RequestParam(value = "productId") @ApiParam("商品ID") String productId,
                                             @RequestParam(value = "productStock") @ApiParam("商品库存数量") Integer productStock) {
        productInfoService.updateProductStock(productId, productStock);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/onSale"})
    @ApiOperation(value = "商品上架")
    @SysLogs("商品上架")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult onSale(@RequestBody @ApiParam("商品ID") String productId) {
        productInfoService.onSale(productId);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/offSale"})
    @ApiOperation(value = "商品下架")
    @SysLogs("商品下架")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult offSale(@ApiParam("商品ID") @RequestBody String productId) {
        productInfoService.offSale(productId);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/onRecommend"})
    @ApiOperation(value = "商品上推荐")
    @SysLogs("商品上推荐")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult onRecommend(@ApiParam("商品ID数组") @RequestBody List<String> ids) {
        productInfoService.onRecommend(ids);
        return ResponseResult.e(ResponseCode.OK);
    }

    @PostMapping(value = {"/offRecommend"})
    @ApiOperation(value = "商品下推荐")
    @SysLogs("商品下推荐")
    @ApiImplicitParam(paramType = "header", name = "Authorization", value = "身份认证Token")
    public ResponseResult offRecommend(@ApiParam("商品ID数组") @RequestBody List<String> ids) {
        productInfoService.offRecommend(ids);
        return ResponseResult.e(ResponseCode.OK);
    }


}
