package com.github.ligangty.market.pos.web.view;

import java.util.List;

public class ProductsCheckoutView {

	private List<ProductView> products;
	private List<PriceOffProductView> priceOff;
	private String total;
	private String totalSave;

	public List<ProductView> getProducts() {
		return products;
	}

	public void setProducts(List<ProductView> products) {
		this.products = products;
	}

	public List<PriceOffProductView> getPriceOff() {
		return priceOff;
	}

	public void setPriceOff(List<PriceOffProductView> priceOff) {
		this.priceOff = priceOff;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTotalSave() {
		return totalSave;
	}

	public void setTotalSave(String totalSave) {
		this.totalSave = totalSave;
	}
}
