package com.github.ligangty.market.pos.domain.priceoff;

import com.github.ligangty.market.pos.domain.Product;

public interface PriceOffStrategy {

	double calculateOffedTotalPrice(double amount, Product product);

	double calculateOffedTotalPrice(int soldAmount, Product product);

}
