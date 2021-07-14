package io.testproject;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import io.testproject.model.CryptoCurrencyGetPriceDto;
import io.testproject.service.CryptoCurrencyService;

@WebMvcTest
@AutoConfigureWebClient
public class CryptoCurrencyControllerTests {
	
	@MockBean
	private CryptoCurrencyService service;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void getMinPriceOk() throws Exception {
		CryptoCurrencyGetPriceDto dto = new CryptoCurrencyGetPriceDto();
		dto.setPrice(new BigDecimal("100.5"));
		dto.setCreatedAt("2021-01-01 10:15:05");
		when(service.getMinPrice(any())).thenReturn(dto);
		
		mockMvc.perform(get("/cryptocurrencies/minprice?name=ABC"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.price").value("100.5"))
				.andExpect(jsonPath("$.createdAt").value("2021-01-01 10:15:05"));
	}
	
	@Test
	void getMinPriceFailsWhenCryptoIsNotValid() throws Exception {		
		when(service.getMinPrice(any())).thenThrow(ValidationException.class);

		mockMvc.perform(get("/cryptocurrencies/minprice?name=BLA"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void getMaxPriceOk() throws Exception {
		CryptoCurrencyGetPriceDto dto = new CryptoCurrencyGetPriceDto();
		dto.setPrice(new BigDecimal("100.5"));
		dto.setCreatedAt("2021-01-01 10:15:05");
		when(service.getMaxPrice(any())).thenReturn(dto);
		
		mockMvc.perform(get("/cryptocurrencies/maxprice?name=ABC"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.price").value("100.5"))
				.andExpect(jsonPath("$.createdAt").value("2021-01-01 10:15:05"));
	}
	
	@Test
	void getMaxPriceFailsWhenCryptoIsNotValid() throws Exception {		
		when(service.getMaxPrice(any())).thenThrow(ValidationException.class);

		mockMvc.perform(get("/cryptocurrencies/maxprice?name=BLA"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void getPricesOk() throws Exception {
		List<CryptoCurrencyGetPriceDto> listDto = new ArrayList<>();
		CryptoCurrencyGetPriceDto dtoFirst = new CryptoCurrencyGetPriceDto();
		dtoFirst.setPrice(new BigDecimal("100.5"));
		dtoFirst.setCreatedAt("2021-01-01 10:15:05");
		
		CryptoCurrencyGetPriceDto dtoSecond = new CryptoCurrencyGetPriceDto();
		dtoSecond.setPrice(new BigDecimal("100.5"));
		dtoSecond.setCreatedAt("2021-01-01 10:15:15");
		listDto.add(dtoFirst);
		listDto.add(dtoSecond);
		
		when(service.getPrices(any())).thenReturn(listDto);
		
		mockMvc.perform(get("/cryptocurrencies?name=ABC&page=0&size=2"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$[0].createdAt").value("2021-01-01 10:15:05"))
				.andExpect(jsonPath("$[1].createdAt").value("2021-01-01 10:15:15"));		
	}
	
	@Test
	void getPricesFailsWhenRequestIsNotValid() throws Exception {		
		when(service.getPrices(any())).thenThrow(ValidationException.class);

		mockMvc.perform(get("/cryptocurrencies?name=BLA&page=0&size=0"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	void exportCsv() throws Exception {
		HttpHeaders header = new HttpHeaders();
		header.set(HttpHeaders.CONTENT_TYPE, "text/csv");
		ResponseEntity<Resource> responseEntity = new ResponseEntity<Resource>(
				null,
				header,
				HttpStatus.OK
				);
		when(service.exportCsv()).thenReturn(responseEntity);
		
		mockMvc.perform(get("/cryptocurrencies/csv"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/csv"));
	}
	
	@Test
	void pageNotFound() throws Exception {
		mockMvc.perform(get("/cryptocurrencies/abc"))
			.andExpect(status().isNotFound());
		
		mockMvc.perform(get("/abcde"))
			.andExpect(status().isNotFound());
	}
}
