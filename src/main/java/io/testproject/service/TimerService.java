package io.testproject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.testproject.converter.CurrencyConverter;
import io.testproject.model.CryptoCurrencyFromCexDto;
import io.testproject.model.CryptoCurrency;

@Component
public class TimerService {

	private static final Logger log = LoggerFactory
			.getLogger(TimerService.class);	
	
	private static final String LAST_PRICE = "https://cex.io/api/last_price/";	

	private String cryptoCurrencies;	
	private CurrencyConverter currencyConverter;
	private CryptoCurrencyService currencyService;
	private RestTemplate restTemplate;	
	
	public TimerService(@Value("${cryptocurrencies}")String cryptoCurrencies, CurrencyConverter currencyConverter,
			CryptoCurrencyService currencyService, RestTemplate restTemplate) {
		this.cryptoCurrencies = cryptoCurrencies;
		this.currencyConverter = currencyConverter;
		this.currencyService = currencyService;
		this.restTemplate = restTemplate;
	}

	@Scheduled(cron="${cron.expression}")
	public void getCryptoCurrencies() {
		String[] currencies = cryptoCurrencies.split(",");
		for (String pair : currencies) {					
			CryptoCurrencyFromCexDto cryptoCurrencyFromCexDto = restTemplate.getForObject(
					LAST_PRICE + pair, CryptoCurrencyFromCexDto.class);
			if (cryptoCurrencyFromCexDto.getLastPrice() != null) {
				CryptoCurrency cryptoCurrency = currencyConverter.convert(cryptoCurrencyFromCexDto);			
				currencyService.save(cryptoCurrency);
			}
			else
				log.info("There is no price for pair " + pair + 
						". Please check name currency in application.properties or/and cex.io API URL.");
		}
	}	
}
