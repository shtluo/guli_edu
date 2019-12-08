package cn.guliedu.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R {

    private Boolean success; //成功或者失败
    private Integer code;//状态码
    private String message;//提示信息
    private Map<String,Object> data = new HashMap<>(); //数据

    //方法返回统一的格式
    //构造方法私有化
    private R(){ }

    //成功的方法
    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.OK);
        r.setMessage("成功");
        return r;
    }
    //失败的方法
    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    //实现链式编程
    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
