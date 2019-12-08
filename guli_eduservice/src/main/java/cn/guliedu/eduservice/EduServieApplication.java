package cn.guliedu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient  //进行注册
@EnableFeignClients //服务调用
//@ComponentScan(basePackages = {"cn.guliedu.eduservice","cn.guliedu.common"})
public class EduServieApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduServieApplication.class, args);
    }
}
