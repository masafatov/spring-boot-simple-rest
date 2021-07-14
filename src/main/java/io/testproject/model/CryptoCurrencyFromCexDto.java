package io.testproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoCurrencyFromCexDto {
	
	@JsonProperty("lprice")
	private String lastPrice;	
	
	@JsonProperty("curr1")
	private String currencyName;
	
	public CryptoCurrencyFromCexDto() {
		
	}	

	public CryptoCurrencyFromCexDto(String lastPrice, String currencyName) {
		this.lastPrice = lastPrice;
		this.currencyName = currencyName;
	}

	public String getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(String lastPrice) {
		this.lastPrice = lastPrice;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	@Override
	public String toString() {
		return "CryptoCurrencyFromCexDto [lastPrice=" + lastPrice + ", currencyName=" + currencyName + "]";
	}	
}
