package com.github.ligangty.market.pos.web.view;

public class ProductView {

	private String name;
	private String number;
	private String giveNumber;
	private String unit;
	private String price;
	private String total;
	private String save;

	public ProductView() {
	}

	public ProductView(String name, String number, String unit, String price, String total) {
		this.name = name;
		this.number = number;
		this.unit = unit;
		this.price = price;
		this.total = total;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getGiveNumber() {
		return giveNumber;
	}

	public void setGiveNumber(String giveNumber) {
		this.giveNumber = giveNumber;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getSave() {
		return save;
	}

	public void setSave(String save) {
		this.save = save;
	}
}
