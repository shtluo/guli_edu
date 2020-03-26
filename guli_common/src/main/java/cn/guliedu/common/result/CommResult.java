package cn.guliedu.common.result;

import cn.guliedu.common.enums.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(value = "公用返回vo类")
@Data
@AllArgsConstructor
public class CommResult<T> implements Serializable {

	@ApiModelProperty("20000成功 20001失败 401未登录或无权限")
	public int code;

	@ApiModelProperty("code为20001时为错误信息内容")
	public String message;

	@ApiModelProperty("返回对象内容")
	public T content;

	public CommResult(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public static CommResult ok(){
		return new CommResult(ResultCodeEnum.OK.getValue(),ResultCodeEnum.OK.getText(),"");
    }

    public static CommResult ok(Object content){
        return new CommResult(ResultCodeEnum.OK.getValue(),ResultCodeEnum.OK.getText(),content);
    }

	public static CommResult error(){
		return new CommResult(ResultCodeEnum.ERROR.getValue(),ResultCodeEnum.ERROR.getText());
	}

    public static CommResult error(String errormsg){
        return new CommResult(ResultCodeEnum.ERROR.getValue(),errormsg);
    }

    public static CommResult error(int code, String errormsg, Object content){
        return new CommResult(code,errormsg,content);
    }
}