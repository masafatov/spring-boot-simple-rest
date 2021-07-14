package io.testproject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import io.testproject.converter.CurrencyConverter;
import io.testproject.model.CryptoCurrencyFromCexDto;
import io.testproject.service.CryptoCurrencyService;
import io.testproject.service.TimerService;

@ExtendWith(MockitoExtension.class)
public class TimerServiceTests {

	private static final String LAST_PRICE = "https://cex.io/api/last_price/";	
	
	@Mock
	private CurrencyConverter converter;
	
	@Mock
	private CryptoCurrencyService currencyService;
	
	@Mock
	RestTemplate restTemplate;
	
	@Spy
	@InjectMocks
	private TimerService timerService = new TimerService("ABC/USD,DEF/USD", converter, currencyService, restTemplate);
	
	@Test
	void getCryptoCurrenciesIsOk() throws Exception {	
		when(restTemplate.getForObject(LAST_PRICE + "ABC/USD", CryptoCurrencyFromCexDto.class))
			.thenReturn(new CryptoCurrencyFromCexDto("ABC", "100.5"));	
		
		when(restTemplate.getForObject(LAST_PRICE + "DEF/USD", CryptoCurrencyFromCexDto.class))
			.thenReturn(new CryptoCurrencyFromCexDto("DEF", "1.005"));
		
		Logger logger = (Logger) LoggerFactory.getLogger(TimerService.class);
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();        
        logger.addAppender(listAppender);        
        timerService.getCryptoCurrencies();
        List<ILoggingEvent> logsList = listAppender.list;
        
        assertThat(logsList.size()).isEqualTo(0);
	}
	
	@Test
	void currencyConverterShouldReturnLoggerMessage() {		
		when(restTemplate.getForObject(LAST_PRICE + "ABC/USD", CryptoCurrencyFromCexDto.class))
		.thenReturn(new CryptoCurrencyFromCexDto());	
	
		when(restTemplate.getForObject(LAST_PRICE + "DEF/USD", CryptoCurrencyFromCexDto.class))
		.thenReturn(new CryptoCurrencyFromCexDto("DEF", "1.005"));
		
		Logger logger = (Logger) LoggerFactory.getLogger(TimerService.class);
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();        
        logger.addAppender(listAppender);        
        timerService.getCryptoCurrencies();
        List<ILoggingEvent> logsList = listAppender.list;
        
        assertThat(logsList.size()).isEqualTo(1);
        assertThat(logsList.get(0).getMessage().contains("ABC/USD"));
	}
}
