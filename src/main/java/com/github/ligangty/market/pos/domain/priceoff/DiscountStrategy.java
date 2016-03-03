package com.github.ligangty.market.pos.domain.priceoff;

import com.github.ligangty.market.pos.domain.Product;

/**
 * Created by gli on 3/3/16.
 */
public class DiscountStrategy implements PriceOffStrategy {
	private double discount;

	public DiscountStrategy(final double discount) {
		this.discount = discount;
	}

	@Override
	public double calculateOffedTotalPrice(final int amount, final Product product) {
		return product.getPrice() * discount * amount;
	}

	@Override
	public double calculateOffedTotalPrice(double amount, Product product) {
		return product.getPrice() * discount * amount;
	}
}
