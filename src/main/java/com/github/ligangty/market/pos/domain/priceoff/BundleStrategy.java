package com.github.ligangty.market.pos.domain.priceoff;

import com.github.ligangty.market.pos.domain.Product;

/**
 * Created by gli on 3/3/16.
 */
public class BundleStrategy implements PriceOffStrategy {
	private int bundleBaseNum;
	private int giveNum;

	public BundleStrategy(int bundleBaseNum, int giveNum) {
		this.bundleBaseNum = bundleBaseNum;
		this.giveNum = giveNum;
	}

	@Override
	public double calculateOffedTotalPrice(int soldAmount, Product product) {
		final double bundlePrice = bundleBaseNum * product.getPrice();
		final int bundleNum = bundleBaseNum + giveNum;
		final int priceOffProductNum = (int) Math.floor(soldAmount / bundleNum);
		int notBundled = soldAmount % bundleNum;
		if (notBundled > bundleBaseNum) {
			notBundled = bundleBaseNum;
		}
		return priceOffProductNum * bundlePrice + product.getPrice() * notBundled;
	}

	@Override
	public double calculateOffedTotalPrice(double soldAmount, Product product) {
		throw new RuntimeException("This strategy does support this method");
	}
}
