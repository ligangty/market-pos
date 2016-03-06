package com.github.ligangty.market.pos.domain.priceoff;

import com.github.ligangty.market.pos.domain.Product;

/**
 * Created by gli on 3/3/16.
 */
public class PriceDiscount implements PriceOffStrategy {
	private double discount;
	private String name;

	public PriceDiscount(final double discount) {
		this.discount = discount;
	}

	public PriceDiscount(final double discount, String name) {
		this.discount = discount;
		this.name = name;
	}

	@Override
	public double calculateOffedTotalPrice(final int amount, final Product product) {
		return product.getPrice() * discount * amount;
	}

	@Override
	public double calculateOffedTotalPrice(double amount, Product product) {
		return product.getPrice() * discount * amount;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
