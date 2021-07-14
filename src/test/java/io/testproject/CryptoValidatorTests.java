package io.testproject;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.testproject.validation.CryptoValidator;

@ExtendWith(MockitoExtension.class)
public class CryptoValidatorTests {
	
	@Mock
	private ConstraintValidatorContext constraintValidatorContext;
	
	@InjectMocks
	private CryptoValidator crypto = new CryptoValidator("ABC/USD,DEF/USD");
	
	@Test
	void cryptoValidationShouldReturnTrue() {
		assertTrue(crypto.isValid("ABC", constraintValidatorContext));	
		assertTrue(crypto.isValid("DEF", constraintValidatorContext)); 	
	}
	
	@Test
	void cryptoValidationShouldReturnFalse() {
		assertFalse(crypto.isValid("EFG", constraintValidatorContext));	
		assertFalse(crypto.isValid("AB", constraintValidatorContext));
		assertFalse(crypto.isValid("USD", constraintValidatorContext));
		assertFalse(crypto.isValid("ABCD", constraintValidatorContext));
	}
}
