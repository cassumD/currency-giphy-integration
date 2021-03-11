package com.cassumd.currencygiphyintegration.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

@FeignClient(value = "currencyClient", url = "${currency.api.url}")
public interface CurrencyClient {
    @GetMapping("/latest.json?app_id=${currency.api.token}")
    String getLatest();

    @GetMapping("/historical/{date}.json?app_id=${currency.api.token}")
    String getHistorical(@PathVariable @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date);
}
