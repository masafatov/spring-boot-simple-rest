package io.testproject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.testproject.model.CryptoCurrency;
import io.testproject.model.CryptoCurrencyGetPriceDto;
import io.testproject.model.ParamsGetPricesDto;
import io.testproject.repository.CryptoCurrencyRepository;
import io.testproject.service.CryptoCurrencyService;
import io.testproject.service.CryptoCurrencyServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CryptoCurrencyServiceTests {
	
	@Mock
	private CryptoCurrencyRepository currencyRepository;
	
	@InjectMocks
	private CryptoCurrencyService service = new CryptoCurrencyServiceImpl("ABC/USD,EFG/USD", currencyRepository);
	
	@Test
	void save() {
		when(currencyRepository.save(any())).then(returnsFirstArg());	
		
		CryptoCurrency cryptoCurrency = currencyRepository.save(
				new CryptoCurrency("ABC", new BigDecimal("100.5"), "2021-01-01 10:15:05"));
		
		assertThat(cryptoCurrency.getPrice()).isEqualTo("100.5");
		assertThat(cryptoCurrency.getCreatedAt()).isEqualTo("2021-01-01 10:15:05");
	}
	
	@Test 
	void getMinPrice() {
		List<CryptoCurrency> prices = new ArrayList<>();
		prices.add(new CryptoCurrency("ABC", new BigDecimal("100.5"), "2021-01-01 10:15:05"));		
		
		when(currencyRepository.findAllByNameOrderByPriceAsc(any())).thenReturn(prices);
		
		CryptoCurrencyGetPriceDto fromService = service.getMinPrice("ABC");
		
		assertThat(fromService.getPrice()).isEqualTo("100.5");
		assertThat(fromService.getCreatedAt()).isEqualTo("2021-01-01 10:15:05");
	}
	
	@Test
	void getMaxPrice() {	
		List<CryptoCurrency> prices = new ArrayList<>();
		prices.add(new CryptoCurrency("ABC", new BigDecimal("100.5"), "2021-01-01 10:15:05"));		
		
		when(currencyRepository.findAllByNameOrderByPriceDesc(any())).thenReturn(prices);		
		
		CryptoCurrencyGetPriceDto fromService = service.getMaxPrice("ABC");		
		
		assertThat(fromService.getPrice()).isEqualTo("100.5");
		assertThat(fromService.getCreatedAt()).isEqualTo("2021-01-01 10:15:05");
	}
	
	@Test
	void getPrices() {
		List<CryptoCurrency> currencies = new ArrayList<>();
		currencies.add(new CryptoCurrency("ABC", new BigDecimal("100.5"), "2021-01-01 10:15:05"));
		currencies.add(new CryptoCurrency("ABC", new BigDecimal("100.5"), "2021-01-01 10:15:15"));
		
		when(currencyRepository.findByNameOrderByPriceAsc(any(), any())).thenReturn(new PageImpl<>(currencies));
		
		List<CryptoCurrencyGetPriceDto> result = service.getPrices(new ParamsGetPricesDto("ABC", 0, 2));
		
		assertThat(result.get(0).getCreatedAt()).isEqualTo("2021-01-01 10:15:05");
		assertThat(result.get(1).getCreatedAt()).isEqualTo("2021-01-01 10:15:15");
	}
	
	@Test
	void exportCsv() throws Exception {	
		List<CryptoCurrency> pricesAbc = new ArrayList<>();
		pricesAbc.add(new CryptoCurrency("ABC", new BigDecimal("100.5"), "2021-01-01 10:15:05"));		
		when(currencyRepository.findAllByNameOrderByPriceAsc(eq("ABC"))).thenReturn(pricesAbc);		
		when(currencyRepository.findAllByNameOrderByPriceDesc(eq("ABC"))).thenReturn(pricesAbc);
	
		List<CryptoCurrency> pricesEfg = new ArrayList<>();
		pricesEfg.add(new CryptoCurrency("EFG", new BigDecimal("1.005"), "2021-01-01 10:15:05"));
		when(currencyRepository.findAllByNameOrderByPriceAsc(eq("EFG"))).thenReturn(pricesEfg);
		when(currencyRepository.findAllByNameOrderByPriceDesc(eq("EFG"))).thenReturn(pricesEfg);
				
		ResponseEntity<Resource> responseEntity = service.exportCsv();
		assertThat(service.exportCsv().getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(service.exportCsv().getHeaders().getContentType().toString()).isEqualTo("text/csv");
			
		InputStream is = responseEntity.getBody().getInputStream();
		String result = new BufferedReader(new InputStreamReader(is))
				.lines().collect(Collectors.joining("\n"));
		
		assertEquals("Cryptocurrency Name,Min Price,Max Price", result.split("\\n")[0]);
		assertEquals("ABC/USD,100.5,100.5", result.split("\\n")[1]);
		assertEquals("EFG/USD,1.005,1.005", result.split("\\n")[2]);
	}
}
