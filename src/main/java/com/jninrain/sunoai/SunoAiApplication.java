package com.jninrain.sunoai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@EnableSwagger2
@MapperScan(basePackages = {"com.jninrain.sunoai.dao"})
@SpringBootApplication
public class SunoAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SunoAiApplication.class, args);
    }

}
