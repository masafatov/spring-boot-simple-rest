package io.testproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import io.testproject.model.CryptoCurrency;

public interface CryptoCurrencyRepository extends MongoRepository<CryptoCurrency, String> {
	
	List<CryptoCurrency> findAllByNameOrderByPriceAsc(String name);	
	
	List<CryptoCurrency> findAllByNameOrderByPriceDesc(String name);
	
	Page<CryptoCurrency> findByNameOrderByPriceAsc(String name, Pageable pageable);	
}
