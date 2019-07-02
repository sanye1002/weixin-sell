package cn.moon.sell.common.handler;


import cn.moon.sell.common.bean.ResponseResult;
import cn.moon.sell.common.bean.ResponseCode;
import cn.moon.sell.common.exception.RequestException;
import cn.moon.sell.common.bean.ResponseCode;
import cn.moon.sell.common.bean.ResponseResult;
import cn.moon.sell.common.exception.RequestException;
import cn.moon.sell.common.bean.ResponseCode;
import cn.moon.sell.common.bean.ResponseResult;
import cn.moon.sell.common.exception.RequestException;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author moon.cn
 * @version 2017/11/18
 */
@ControllerAdvice(basePackages = {"cn.moon.sell"})
@Log4j
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(value = RequestException.class)
    @ResponseBody
    public ResponseResult requestExceptionHandler(RequestException e) {
//        if (e.getE() != null) e.printStackTrace();
        return ResponseResult.builder().msg(e.getMsg()).code(e.getStatus()).build();
    }


    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseResult requestExceptionHandler(DataIntegrityViolationException e) {
        return ResponseResult.builder().msg("数据操作格式异常").code(ResponseCode.FAIL.code).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseResult methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        String s = "参数验证失败";
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            s = errors.get(0).getDefaultMessage();
        }
        return ResponseResult.builder().code(ResponseCode.FAIL.code).msg(s).build();
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseResult requestExceptionHandler(Exception e) {
        e.printStackTrace();
        return ResponseResult.builder().msg("服务器飘了，管理员去拿刀修理了~").code(ResponseCode.FAIL.code).build();
    }


}
