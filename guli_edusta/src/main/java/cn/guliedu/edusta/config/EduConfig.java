package cn.guliedu.edusta.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("cn.guliedu.edusta.mapper")
public class EduConfig {
}
