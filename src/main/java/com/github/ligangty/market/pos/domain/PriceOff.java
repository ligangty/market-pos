package com.github.ligangty.market.pos.domain;

import java.util.List;

/**
 * Mapping object for priceOffData.json
 */
public class PriceOff {
	private String id;
	private String name;
	private String discount;
	private List<String> products;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public List<String> getProducts() {
		return products;
	}

	public void setProducts(List<String> products) {
		this.products = products;
	}
}
