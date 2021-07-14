package io.testproject.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import io.testproject.model.CryptoCurrency;
import io.testproject.model.CryptoCurrencyGetPriceDto;
import io.testproject.model.ParamsGetPricesDto;
import io.testproject.repository.CryptoCurrencyRepository;

@Service
@Validated
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService{

	/*
	 * In this implementation:
	 * only first currency pair stored into db:	 	
	 * 		example: 
	 * 		"BTC/USD" - value in apllication.properties
	 * 		"BTC" - value in db
	 */	
	
	private String cryptoCurrencies;
	private CryptoCurrencyRepository currencyRepository;
	

	public CryptoCurrencyServiceImpl(@Value("${cryptocurrencies}") String cryptoCurrencies, CryptoCurrencyRepository currencyRepository) {
		this.cryptoCurrencies = cryptoCurrencies;
		this.currencyRepository = currencyRepository;
	}
	
	@Override
	public void save(CryptoCurrency cryptoCurrency) {
		currencyRepository.save(cryptoCurrency);		
	}
	
	@Override
	public CryptoCurrencyGetPriceDto getMinPrice(String cryptoCurrency) {
		return new CryptoCurrencyGetPriceDto(currencyRepository.findAllByNameOrderByPriceAsc(cryptoCurrency).get(0));
	}
	
	@Override
	public CryptoCurrencyGetPriceDto getMaxPrice(String cryptoCurrency) {
		return new CryptoCurrencyGetPriceDto(currencyRepository.findAllByNameOrderByPriceDesc(cryptoCurrency).get(0));
	}
	
	@Override
	public List<CryptoCurrencyGetPriceDto> getPrices(ParamsGetPricesDto params) {
		Page<CryptoCurrency> page = currencyRepository.findByNameOrderByPriceAsc(
				params.getName(), 
				PageRequest.of(params.getPage(), params.getSize())
				);
		List<CryptoCurrencyGetPriceDto> prices = page.getContent().stream()
				.map(CryptoCurrencyGetPriceDto::new)
				.collect(Collectors.toList());	
		return prices;
	}

	@Override
	public ResponseEntity<Resource> exportCsv() {
		String[] csvHeader = new String[]{"Cryptocurrency Name", "Min Price", "Max Price"};
				
		List<List<String>> csvBody = new ArrayList<>();
		String[] curr = cryptoCurrencies.split(",");
		for (String s : curr) {
			List<String> line = new ArrayList<>();
			line.add(s);
			String currencyName = s.split("/")[0];
			line.add(getMinPrice(currencyName).getPrice().toString());
			line.add(getMaxPrice(currencyName).getPrice().toString());
			csvBody.add(line);
		}
				
		ByteArrayInputStream byteArrayOutputStream;
				
		try (
				ByteArrayOutputStream out = new ByteArrayOutputStream();
			    CSVPrinter csvPrinter = new CSVPrinter(
			    		new PrintWriter(out),
			            CSVFormat.DEFAULT.withHeader(csvHeader)
			     );
		) {
			for (List<String> record : csvBody)
				csvPrinter.printRecord(record);
	
			csvPrinter.flush();

			byteArrayOutputStream = new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}

		InputStreamResource fileInputStream = new InputStreamResource(byteArrayOutputStream);

		String csvFileName = "cryptocurrency.csv";

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + csvFileName);
		headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");

		return new ResponseEntity<>(
				fileInputStream,
			    headers,
			    HttpStatus.OK
		);
	}
}
