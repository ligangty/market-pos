package com.github.ligangty.market.pos.domain;

import com.github.ligangty.market.pos.domain.priceoff.PriceOffStrategy;

public class Product {
	private String barCode;
	private String name;
	private Double price;
	private String unit;
	private PriceOffStrategy priceOff;

	public Product(){
	}

	public Product(String barCode, String name, double price, String unit) {
		this.barCode = barCode;
		this.name = name;
		this.price = price;
		this.unit = unit;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public PriceOffStrategy getPriceOff() {
		return priceOff;
	}

	public void setPriceOff(PriceOffStrategy priceOff) {
		this.priceOff = priceOff;
	}
}
