package io.testproject.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import io.testproject.model.CryptoCurrency;
import io.testproject.model.CryptoCurrencyGetPriceDto;
import io.testproject.model.ParamsGetPricesDto;
import io.testproject.validation.Crypto;

public interface CryptoCurrencyService {
	
	public void save(CryptoCurrency cryptoCurrency);
	
	public CryptoCurrencyGetPriceDto getMinPrice(@Crypto String cryptoCurrency);
	
	public CryptoCurrencyGetPriceDto getMaxPrice(@Crypto String cryptoCurrency);
	
	public List<CryptoCurrencyGetPriceDto> getPrices(@Valid ParamsGetPricesDto params);
	
	public ResponseEntity<Resource> exportCsv();	
}
