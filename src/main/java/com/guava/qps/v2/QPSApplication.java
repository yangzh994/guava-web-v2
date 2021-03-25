package com.guava.qps.v2;

import com.guava.app.annotations.EnableQPS;
import com.guava.qps.v2.listener.CustomerListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableQPS
@MapperScan(basePackages = "com.guava.qps.v2.mapper")
public class QPSApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(QPSApplication.class,args);
    }


    @Override
    public void run(String... args) throws Exception {
        CustomerListener customerListener = new CustomerListener();
        customerListener.startCustomer();
    }
}
