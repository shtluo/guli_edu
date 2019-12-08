package cn.guliedu.eduservice.handler;

import cn.guliedu.common.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    //全局异常
    @ExceptionHandler(Exception.class) //异常处理器
    @ResponseBody //返回统一结果数据
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("出错了!!");
    }

    //特殊异常
    @ExceptionHandler(ArithmeticException.class) //异常处理器
    @ResponseBody //返回统一结果数据
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("不能为0出错了!!");
    }

    //自定义异常
    //特殊异常
    @ExceptionHandler(EduException.class) //异常处理器
    @ResponseBody //返回统一结果数据
    public R error(EduException e) {
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
