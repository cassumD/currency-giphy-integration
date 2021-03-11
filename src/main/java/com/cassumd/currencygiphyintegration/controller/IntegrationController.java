package com.cassumd.currencygiphyintegration.controller;

import com.cassumd.currencygiphyintegration.service.IntegrationService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/giphy")
public class IntegrationController {

    @Autowired
    private IntegrationService integrationService;

    @Autowired
    private Configuration freeMaker;

    @GetMapping
    public String getGiphy() throws IOException, TemplateException {
        double currentCurrency = integrationService.getCurrentCurrency();
        double yesterdayCurrency = integrationService.getYesterdayCurrency();
        String giphyUrl = integrationService.getGiphyURL(currentCurrency, yesterdayCurrency);
        Template template = freeMaker.getTemplate("index.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, Map.of("giphyUrl", giphyUrl));
    }
}
