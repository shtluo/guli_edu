package cn.guliedu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"cn.guliedu.common","cn.guliedu.eduservice"})
public class EduServieApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduServieApplication.class, args);
    }
}
