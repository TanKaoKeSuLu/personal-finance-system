package com.tankaokesulu.financeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinanceApiApplication {

/**
 * Spring Boot应用程序的主入口方法
 * 通过main方法启动Spring Boot应用程序
 * 这是整个应用程序的启动点，Spring框架会通过此方法初始化并启动整个应用
 *
 * @param args 命令行参数，可以在启动应用时传递
 *            这些参数可以用于配置应用程序的行为，例如指定运行环境、自定义配置等
 */
    public static void main(String[] args) {
    // 调用SpringApplication的run方法启动应用程序
    // FinanceApiApplication是应用程序的主类，包含@SpringBootApplication注解
    // args是传递给应用程序的命令行参数，可以用于应用配置
        SpringApplication.run(FinanceApiApplication.class, args);
    }

}
