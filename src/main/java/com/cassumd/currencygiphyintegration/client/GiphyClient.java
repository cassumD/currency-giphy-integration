package com.cassumd.currencygiphyintegration.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "giphyCLient", url = "${giphy.api.url}")
public interface GiphyClient {

    @GetMapping("/random?api_key=${giphy.api.token}&tag=rich&random_id=${giphy.api.random-id}")
    String getRich();

    @GetMapping("/random?api_key=${giphy.api.token}&tag=broke&random_id=${giphy.api.random-id}")
    String getBroke();

}
