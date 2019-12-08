package cn.guliedu.eduservice.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //有参数构造
@NoArgsConstructor  // 无参数构造
public class EduException extends RuntimeException {
    private Integer code; //状态码
    private String msg;//错误提示信息
}
