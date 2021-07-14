package io.testproject.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Value;

public class CryptoValidator implements ConstraintValidator<Crypto, String> {

	private String cryptoCurrencies;	

	public CryptoValidator(@Value("${cryptocurrencies}") String cryptoCurrencies) {
		this.cryptoCurrencies = cryptoCurrencies;
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {	
		String[] pairs = cryptoCurrencies.split(",");
		for (String pair: pairs) {
			if (pair.split("/")[0].equals(value))
				return true;
		}
		return false;
	}
}
