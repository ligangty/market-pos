package com.github.ligangty.market.pos.service;

import com.github.ligangty.market.pos.data.ProductDataLoader;
import com.github.ligangty.market.pos.domain.Product;
import com.github.ligangty.market.pos.domain.priceoff.BundleSelling;
import com.github.ligangty.market.pos.domain.priceoff.PriceDiscount;
import com.github.ligangty.market.pos.domain.priceoff.PriceOffStrategy;
import com.github.ligangty.market.pos.web.view.PriceOffProductView;
import com.github.ligangty.market.pos.web.view.ProductView;
import com.github.ligangty.market.pos.web.view.ProductsCheckoutView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Service class to provide product check out logic.
 */
public class ProductsService {

	public ProductsCheckoutView calculateCheckout(List<String> barCodes) {
		// collect the product ordered numbers with a <barcode, number> map
		final Map<String, Number> productsOrderedResult = collectProductOrderedNum(barCodes);

		final ProductsCheckoutView checkoutView = new ProductsCheckoutView();

		final List<ProductView> productViews = new ArrayList<>();
		final Map<String, PriceOffProductView> priceOffProductViews = new HashMap<>();
		Double totalSaved = 0.0;
		Double total = 0.0;
		for (Map.Entry<String, Number> entry : productsOrderedResult.entrySet()) {
			final String barCode = entry.getKey();
			final Number orderedNumber = entry.getValue();
			final Product product = ProductDataLoader.getProductByBarcode(barCode);

			final ProductView productView = new ProductView(product.getName(), orderedNumber.toString(), product.getUnit(),
			                                                "" + product.getPrice(), null);
			Double actualTotal = calculateActualTotal(product, orderedNumber);
			productView.setTotal(actualTotal + "");

			final double saved = roundHalfUpWithTwoPlacies(product.getPrice() * orderedNumber.doubleValue() - actualTotal);
			if (product.getPriceOff() instanceof PriceDiscount) {
				productView.setSave("" + saved);
			}

			totalSaved += saved;
			total += actualTotal;

			productViews.add(productView);

			makePriceOffProductViewForOrder(product, orderedNumber, priceOffProductViews);
		}

		totalSaved = roundHalfUpWithTwoPlacies(totalSaved);
		total = roundHalfUpWithTwoPlacies(total);

		checkoutView.setProducts(productViews);
		checkoutView.setTotalSave(totalSaved.toString());
		checkoutView.setTotal(total.toString());
		checkoutView.setPriceOff(new ArrayList<>(priceOffProductViews.values()));

		return checkoutView;
	}

	private Map<String, Number> collectProductOrderedNum(List<String> barCodes) {
		final Map<String, Number> productsOrderedResult = new HashMap<>();
		for (String originalBarcode : barCodes) {
			String barCode = originalBarcode;
			final Number result = productsOrderedResult.get(barCode);
			if (originalBarcode.contains("-")) {
				final String[] itemWithNumber = analyzeBarcode(originalBarcode);
				barCode = itemWithNumber[0];
				if (ProductDataLoader.hasProduct(barCode)) {
					final Double itemNumber = Double.parseDouble(itemWithNumber[1]);
					if (result == null) {
						productsOrderedResult.put(barCode, itemNumber);
					} else {
						productsOrderedResult.put(barCode, itemNumber + result.doubleValue());
					}
				}
			} else {
				if (ProductDataLoader.hasProduct(barCode)) {
					if (result == null) {
						productsOrderedResult.put(barCode, 1);
					} else {
						productsOrderedResult.put(barCode, result.intValue() + 1);
					}
				}
			}
		}

		return productsOrderedResult;
	}

	private Double calculateActualTotal(Product product, Number orderedNumber) {
		final PriceOffStrategy priceOff = product.getPriceOff();
		Double actualTotal = 0.0;
		if (priceOff != null) {
			if (orderedNumber instanceof Double) {
				actualTotal = roundHalfUpWithTwoPlacies(
					priceOff.calculateOffedTotalPrice(orderedNumber.doubleValue(), product));
			} else if (orderedNumber instanceof Integer) {
				actualTotal = roundHalfUpWithTwoPlacies(priceOff.calculateOffedTotalPrice(orderedNumber.intValue(), product));
			}
		} else {
			actualTotal = roundHalfUpWithTwoPlacies(product.getPrice() * orderedNumber.doubleValue());
		}
		return actualTotal;
	}

	private void makePriceOffProductViewForOrder(Product product, Number orderedNumber,
	                                             Map<String, PriceOffProductView> priceOffProductViews) {
		if (product.getPriceOff() instanceof BundleSelling) {
			final BundleSelling bundlePriceOff = (BundleSelling) product.getPriceOff();
			final String priceOffName = bundlePriceOff.getName();
			PriceOffProductView priceOffProductView = priceOffProductViews.get(priceOffName);
			if (priceOffProductView == null) {
				priceOffProductView = new PriceOffProductView();
				priceOffProductView.setName(priceOffName);
			}
			final ProductView priceOffProduct = new ProductView(product.getName(), null, product.getUnit(), null, null);
			priceOffProduct.setGiveNumber(bundlePriceOff.calculateGivenNumber(orderedNumber.intValue()).toString());
			priceOffProductView.getProducts().add(priceOffProduct);
			priceOffProductViews.put(bundlePriceOff.getName(), priceOffProductView);
		}
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
