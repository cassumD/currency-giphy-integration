package com.cassumd.currencygiphyintegration.service;

import com.cassumd.currencygiphyintegration.client.CurrencyClient;
import com.cassumd.currencygiphyintegration.client.GiphyClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class IntegrationService {

    @Value("${currency.comparison}")
    private String currencyComparison;

    @Autowired
    private CurrencyClient currencyClient;

    @Autowired
    private GiphyClient giphyClient;

    /**
     * Метод, который обращается к сервису валют
     *
     * @return актуальный курс валюты
     * @throws JsonProcessingException при неудачном разборе JSON ответа
     */
    public double getCurrentCurrency() throws JsonProcessingException {
        return getCurrency(currencyClient.getLatest());
    }

    /**
     * Метод, который обращается к сервису валют
     *
     * @return курс валюты за вчерашний день
     * @throws JsonProcessingException при неудачном разборе JSON ответа
     */
    public double getYesterdayCurrency() throws JsonProcessingException {
        return getCurrency(currencyClient.getHistorical(LocalDate.now().minusDays(1L)));
    }

    /**
     * Метод, который обращается к сервису gif в зависимости от курсов валют
     * если курс по отношению к рублю за сегодня стал выше вчерашнего, то отдаем gif RICH, иначе gif BROKE
     *
     * @param currentCurrency   актуальный курс
     * @param yesterdayCurrency вчерашний курс
     * @return ссылку на gif
     * @throws JsonProcessingException при неудачном разборе JSON ответа
     */
    public String getGiphyURL(double currentCurrency, double yesterdayCurrency) throws JsonProcessingException {
        String giphyAnswer = currentCurrency > yesterdayCurrency ? giphyClient.getRich() : giphyClient.getBroke();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode root = objectMapper.readValue(giphyAnswer, ObjectNode.class);
        return root.get("data").get("image_original_url").toString().replaceAll("\"", "");
    }

    private double getCurrency(String currencyAnswer) throws JsonProcessingException {
        String ratesJsonName = "rates";
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rates = objectMapper.readValue(currencyAnswer, ObjectNode.class);
        JsonNode allRates = rates.get(ratesJsonName);
        double rub = allRates.get(currencyComparison).asDouble();
        log.info("requested currency = {}", rub);

        return rub;
    }
}
