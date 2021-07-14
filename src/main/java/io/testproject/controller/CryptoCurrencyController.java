package io.testproject.controller;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.testproject.model.CryptoCurrencyGetPriceDto;
import io.testproject.model.ParamsGetPricesDto;
import io.testproject.service.CryptoCurrencyService;

@RestController
@RequestMapping("/cryptocurrencies")
public class CryptoCurrencyController {
	
	private CryptoCurrencyService cryptoCurrencyService;
	
	public CryptoCurrencyController(CryptoCurrencyService cryptoCurrencyService) {
		this.cryptoCurrencyService = cryptoCurrencyService;
	}

	@GetMapping("/minprice")
	public CryptoCurrencyGetPriceDto getMinPrice(@RequestParam("name") String currencyName) {		
		return cryptoCurrencyService.getMinPrice(currencyName);
	}
	
	@GetMapping("/maxprice")
	public CryptoCurrencyGetPriceDto getMaxPrice(@RequestParam("name") String currencyName) {		
		return cryptoCurrencyService.getMaxPrice(currencyName);
	}
	
	@GetMapping
	public List<CryptoCurrencyGetPriceDto> getPrices(
			@RequestParam("name") String currencyName, 
			@RequestParam(name = "page", defaultValue = "${default.page}") Integer page, 
			@RequestParam(name = "size", defaultValue = "${default.size}") Integer size) {
		ParamsGetPricesDto requestParam = new ParamsGetPricesDto(currencyName, page, size);
		return cryptoCurrencyService.getPrices(requestParam);
	}
	
	@GetMapping(value="/csv", produces = "text/csv")
	public ResponseEntity<Resource> exportCsv() { 
		return cryptoCurrencyService.exportCsv();				
	}
}
