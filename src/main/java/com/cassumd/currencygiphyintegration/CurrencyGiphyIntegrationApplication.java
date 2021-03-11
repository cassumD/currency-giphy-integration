package com.cassumd.currencygiphyintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CurrencyGiphyIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyGiphyIntegrationApplication.class, args);
    }

}
