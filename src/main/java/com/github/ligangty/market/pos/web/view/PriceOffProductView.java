package com.github.ligangty.market.pos.web.view;

import java.util.List;

public class PriceOffProductView {
	private String name;
	private List<ProductView> products;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProductView> getProducts() {
		return products;
	}

	public void setProducts(List<ProductView> products) {
		this.products = products;
	}
}
