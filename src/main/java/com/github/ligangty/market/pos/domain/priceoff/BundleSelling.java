package com.github.ligangty.market.pos.domain.priceoff;

import com.github.ligangty.market.pos.domain.Product;

public class BundleSelling implements PriceOffStrategy {

	private int bundleBaseNum;
	private int giveNum;
	private String name;

	public BundleSelling(int bundleBaseNum, int giveNum) {
		this.bundleBaseNum = bundleBaseNum;
		this.giveNum = giveNum;
	}

	public BundleSelling(int bundleBaseNum, int giveNum, String name) {
		this.bundleBaseNum = bundleBaseNum;
		this.giveNum = giveNum;
		this.name = name;
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

	public Integer calculateGivenNumber(int soldAmount) {
		final int bundleNum = bundleBaseNum + giveNum;
		final int priceOffProductNum = (int) Math.floor(soldAmount / bundleNum);
		int result = giveNum * priceOffProductNum;
		final int modGiveNum = soldAmount % bundleNum - bundleBaseNum;
		if (modGiveNum > 0) {
			result += modGiveNum;
		}
		return result;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
