package cn.guliedu.eduek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer  //注册中心服务
public class EkApplication {
    public static void main(String[] args) {
        SpringApplication.run(EkApplication.class);
    }
}
