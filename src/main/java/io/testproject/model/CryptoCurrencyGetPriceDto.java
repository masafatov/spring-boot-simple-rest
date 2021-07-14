package io.testproject.model;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class CryptoCurrencyGetPriceDto {

	private BigDecimal price;		
	private String createdAt;
	
	public CryptoCurrencyGetPriceDto() {
		
	}	
	
	public CryptoCurrencyGetPriceDto(BigDecimal price, String createdAt) {
		this.price = price;
		this.createdAt = createdAt;
	}

	public CryptoCurrencyGetPriceDto(CryptoCurrency cryptoCurrency) {
		this.price = cryptoCurrency.getPrice();
		this.createdAt = cryptoCurrency.getCreatedAt();
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}	

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "CryptoCurrencyGetPriceDto [price=" + price + ", createdAt=" + createdAt + "]";
	}	
}
