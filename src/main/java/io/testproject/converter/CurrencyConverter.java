package io.testproject.converter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import io.testproject.model.CryptoCurrencyFromCexDto;
import io.testproject.model.CryptoCurrency;

@Component
public class CurrencyConverter implements Converter<CryptoCurrencyFromCexDto, CryptoCurrency> {

	private static final DateTimeFormatter formatter = 
			DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss");
	
	public CryptoCurrency convert(CryptoCurrencyFromCexDto source) {
		CryptoCurrency cryptoCurrency = new CryptoCurrency();
		cryptoCurrency.setName(source.getCurrencyName());
		cryptoCurrency.setPrice(new BigDecimal(source.getLastPrice()));
		cryptoCurrency.setCreatedAt(LocalDateTime.now().format(formatter));
		return cryptoCurrency;
	}
}
