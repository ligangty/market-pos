package com.github.ligangty.market.pos.service;

import com.github.ligangty.market.pos.data.ProductDataLoader;
import com.github.ligangty.market.pos.domain.Product;
import com.github.ligangty.market.pos.domain.priceoff.BundleSelling;
import com.github.ligangty.market.pos.web.view.PriceOffProductView;
import com.github.ligangty.market.pos.web.view.ProductView;
import com.github.ligangty.market.pos.web.view.ProductsCheckoutView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductsService {

	public ProductsCheckoutView calculateCheckout(List<String> barCodes) {
		final Map<String, Number> productSellingResult = new HashMap<>();
		for (String originalBarcode : barCodes) {
			String barCode = originalBarcode;
			final Number result = productSellingResult.get(barCode);
			if (originalBarcode.contains("-")) {
				final String[] itemWithNumber = analyzeBarcode(originalBarcode);
				barCode = itemWithNumber[0];
				if (ProductDataLoader.hasProduct(barCode)) {
					final Double itemNumber = Double.parseDouble(itemWithNumber[1]);
					if (result == null) {
						productSellingResult.put(barCode, itemNumber);
					} else {
						productSellingResult.put(barCode, itemNumber + result.doubleValue());
					}
				}
			} else {
				if (ProductDataLoader.hasProduct(barCode)) {
					if (result == null) {
						productSellingResult.put(barCode, 1);
					} else {
						productSellingResult.put(barCode, result.intValue() + 1);
					}
				}
			}
		}

		final ProductsCheckoutView checkoutView = new ProductsCheckoutView();

		final List<ProductView> productViews = new ArrayList<>();
		final Map<String, PriceOffProductView> priceOffProductViews = new HashMap<>();
		Double totalSaved = 0.0;
		Double total = 0.0;
		for (Map.Entry<String, Number> entry : productSellingResult.entrySet()) {
			final String barCode = entry.getKey();
			final Number number = entry.getValue();
			final Product product = ProductDataLoader.getProductByBarcode(barCode);
			final ProductView productView = new ProductView(barCode, number.toString(), product.getUnit(),
			                                                "" + product.getPrice(), null);
			Double actualTotal = 0.0;
			if (number instanceof Double) {
				actualTotal = roundHalfUpWithTwoPlacies(
					product.getPriceOff().calculateOffedTotalPrice(number.doubleValue(), product));
			} else if (number instanceof Integer) {
				actualTotal = roundHalfUpWithTwoPlacies(
					product.getPriceOff().calculateOffedTotalPrice(number.intValue(), product));
			}
			productView.setTotal(actualTotal + "");

			final double saved = roundHalfUpWithTwoPlacies(product.getPrice() * number.doubleValue() - actualTotal);
			productView.setSave("" + saved);

			totalSaved += saved;
			total += actualTotal;

			productViews.add(productView);

			if (product.getPriceOff() instanceof BundleSelling) {
				final BundleSelling priceOff = (BundleSelling)product.getPriceOff();
				final String priceOffName = priceOff.getName();
				PriceOffProductView priceOffProductView = priceOffProductViews.get(priceOffName);
				if (priceOffProductView == null) {
					priceOffProductView = new PriceOffProductView();
					priceOffProductView.setName(priceOffName);
				}
				final ProductView priceOffProduct = new ProductView(product.getName(), number.intValue() + "",
				                                                    product.getUnit(), null, null);
				priceOffProduct.setGiveNumber(priceOff.calculateGivenNumber(number.intValue()).toString());
				priceOffProductView.getProducts().add(priceOffProduct);
				priceOffProductViews.put(priceOff.getName(), priceOffProductView);
			}
		}

		checkoutView.setProducts(productViews);
		checkoutView.setTotalSave(totalSaved.toString());
		checkoutView.setTotal(total.toString());
		checkoutView.setPriceOff(new ArrayList<>(priceOffProductViews.values()));

		return checkoutView;
	}

	private Double roundHalfUpWithTwoPlacies(Double original) {
		BigDecimal b = new BigDecimal(original);
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public String[] analyzeBarcode(String originalBarcode) {
		checkNotNull(originalBarcode);
		return originalBarcode.split("-");
	}
}
