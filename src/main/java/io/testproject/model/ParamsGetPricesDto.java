package io.testproject.model;

import javax.validation.constraints.Min;

import io.testproject.validation.Crypto;

public class ParamsGetPricesDto {

	@Crypto
	private String name;
	
	@Min(0)
	private Integer page;
	
	@Min(1)
	private Integer size;
	
	public ParamsGetPricesDto() {
		
	}

	public ParamsGetPricesDto(String name, Integer page, Integer size) {
		this.name = name;
		this.page = page;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "ParamsGetPricesDto [name=" + name + ", page=" + page + ", size=" + size + "]";
	}	
}
