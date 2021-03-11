package com.cassumd.currencygiphyintegration;

import com.cassumd.currencygiphyintegration.client.CurrencyClient;
import com.cassumd.currencygiphyintegration.client.GiphyClient;
import com.cassumd.currencygiphyintegration.service.IntegrationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

@SpringBootTest
class CurrencyGiphyIntegrationApplicationTests {

    @Autowired
    private IntegrationService integrationService;

    @MockBean
    private CurrencyClient currencyClient;

    @MockBean
    private GiphyClient giphyClient;

    @Test
    void givenCorrectCurrentCurrency_whenGetCurrentCurrency_thenReturnCorrectDoubleCurrency() throws JsonProcessingException {
        Mockito.when(currencyClient.getLatest()).thenReturn(IntegrationJsonAnswers.CORRECT_CURRENCY_JSON_70);
        Assertions.assertEquals(70.0, integrationService.getCurrentCurrency());
    }

    @Test
    void givenIncorrectCurrentCurrency_whenGetCurrentCurrency_thenThrowsJsonException() {
        Mockito.when(currencyClient.getLatest()).thenReturn(IntegrationJsonAnswers.INCORRECT_JSON);
        Assertions.assertThrows(JsonProcessingException.class, () -> integrationService.getCurrentCurrency());
    }

    @Test
    void givenCorrectYesterdayCurrency_whenGetYesterdayCurrency_thenReturnCorrectDoubleCurrency() throws JsonProcessingException {
        Mockito.when(currencyClient.getHistorical(LocalDate.now().minusDays(1L))).thenReturn(IntegrationJsonAnswers.CORRECT_CURRENCY_JSON_70);
        Assertions.assertEquals(70.0, integrationService.getYesterdayCurrency());
    }

    @Test
    void givenIncorrectYesterdayCurrency_whenGetYesterdayCurrency_thenThrowsJsonException() {
        Mockito.when(currencyClient.getHistorical(LocalDate.now().minusDays(1L))).thenReturn(IntegrationJsonAnswers.INCORRECT_JSON);
        Assertions.assertThrows(JsonProcessingException.class, () -> integrationService.getYesterdayCurrency());
    }

    @Test
    void givenDecreasedCurrency_whenGetGiphy_thenGetRichGiphy() throws JsonProcessingException {
        Mockito.when(giphyClient.getBroke()).thenReturn(IntegrationJsonAnswers.CORRECT_GIPHY_JSON_BROKE);
        Assertions.assertEquals("BROKE_URL", integrationService.getGiphyURL(70, 75));
    }

    @Test
    void givenIncreasedCurrency_whenGetGiphy_thenGetRichGiphy() throws JsonProcessingException {
        Mockito.when(giphyClient.getRich()).thenReturn(IntegrationJsonAnswers.CORRECT_GIPHY_JSON_RICH);
        Assertions.assertEquals("RICH_URL", integrationService.getGiphyURL(75, 70));
    }

    @Test
    void givenIncorrectGiphy_whenGetGiphy_thenThrowsJsonException() {
        Mockito.when(giphyClient.getRich()).thenReturn(IntegrationJsonAnswers.INCORRECT_JSON);
        Assertions.assertThrows(JsonProcessingException.class, () -> integrationService.getGiphyURL(75, 70));
    }

}
