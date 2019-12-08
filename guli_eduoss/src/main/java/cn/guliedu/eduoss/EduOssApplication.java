package cn.guliedu.eduoss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class EduOssApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduOssApplication.class, args);
    }
}
