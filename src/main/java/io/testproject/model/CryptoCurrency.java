package io.testproject.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection="prices")
public class CryptoCurrency {
	
	@Id
	private String id;
	
	private String name;
	
	@Field(targetType = FieldType.DECIMAL128)
	private BigDecimal price;	
	
	private String createdAt;
	
	public CryptoCurrency() {
		
	}		

	public CryptoCurrency(String name, BigDecimal price, String createdAt) {
		this.name = name;
		this.price = price;
		this.createdAt = createdAt;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		return "CryptoCurrency [id=" + id + ", name=" + name + ", price=" + price + ", createdAt=" + createdAt + "]";
	}	
}
