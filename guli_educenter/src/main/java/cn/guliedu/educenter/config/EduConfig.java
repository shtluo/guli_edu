package cn.guliedu.educenter.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cn.guliedu.educenter.mapper")
public class EduConfig {
}
