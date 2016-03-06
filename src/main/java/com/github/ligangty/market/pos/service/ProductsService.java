package com.github.ligangty.market.pos.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.ligangty.market.pos.domain.PriceOff;
import com.github.ligangty.market.pos.domain.Product;
import com.github.ligangty.market.pos.domain.priceoff.BundleSelling;
import com.github.ligangty.market.pos.domain.priceoff.PriceDiscount;
import com.github.ligangty.market.pos.domain.priceoff.PriceOffStrategy;
import com.github.ligangty.market.pos.web.view.ProductsCheckoutView;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.*;

public class ProductsService {
	private static final Logger LOG = LoggerFactory.getLogger(ProductsService.class);

	private static final List<Product> products = new ArrayList<Product>();
	private static final List<PriceOff> priceOffs = new ArrayList<PriceOff>();

	static {
		try {
			initOrRefreshData();
		} catch (IOException e) {
			LOG.error("something wrong when init products data:", e);
			throw new RuntimeException(e);
		}
	}

	public static void initOrRefreshData() throws IOException {
		loadProducts();
		loadPriceOff();
		establishProductsWithPriceOff();
	}

	private static void loadProducts() throws IOException {
		products.clear();
		InputStream stream = ProductsService.class.getResourceAsStream("/productsData.json");
		JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
		jsonReader.beginObject();

		while (jsonReader.hasNext()) {
			String name = jsonReader.nextName();
			if (name.equals("products")) {
				jsonReader.beginArray();
				while (jsonReader.hasNext()) {
					jsonReader.beginObject();
					Product product = new Product();
					while (jsonReader.hasNext()) {
						String tokenName = jsonReader.nextName();
						if (tokenName.equals("name")) {
							product.setName(jsonReader.nextString());
						} else if (tokenName.equals("barCode")) {
							product.setBarCode(jsonReader.nextString());
						} else if (tokenName.equals("unit")) {
							product.setUnit(jsonReader.nextString());
						} else if (tokenName.equals("price")) {
							product.setPrice(Double.parseDouble(jsonReader.nextString()));
						}
					}
					products.add(product);
					jsonReader.endObject();
				}
				jsonReader.endArray();
			}
		}

		jsonReader.endObject();
		jsonReader.close();
	}

	private static void loadPriceOff() throws IOException {
		priceOffs.clear();
		InputStream stream = ProductsService.class.getResourceAsStream("/priceOffData.json");
		JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
		jsonReader.beginObject();

		while (jsonReader.hasNext()) {
			String name = jsonReader.nextName();
			if (name.equals("priceOff")) {
				jsonReader.beginArray();
				while (jsonReader.hasNext()) {
					jsonReader.beginObject();
					PriceOff priceOff = new PriceOff();
					while (jsonReader.hasNext()) {
						String tokenName = jsonReader.nextName();
						if (tokenName.equals("id")) {
							priceOff.setId(jsonReader.nextString());
						} else if (tokenName.equals("name")) {
							priceOff.setName(jsonReader.nextString());
						} else if (tokenName.equals("products")) {
							List<String> products = new ArrayList<String>();
							jsonReader.beginArray();
							while (jsonReader.hasNext()) {
								products.add(jsonReader.nextString());
							}
							jsonReader.endArray();
							priceOff.setProducts(products);
						} else if (tokenName.equals("discount")) {
							priceOff.setDiscount(jsonReader.nextString());
						}
					}
					priceOffs.add(priceOff);
					jsonReader.endObject();
				}
				jsonReader.endArray();
			}
		}

		jsonReader.endObject();
		jsonReader.close();
	}

	private static void establishProductsWithPriceOff() {
		checkState(!products.isEmpty(), "products can not be empty");
		for (PriceOff priceOff : priceOffs) {
			for (String produceBarcode : priceOff.getProducts()) {
				Product product = getProductByBarcode(produceBarcode);
				if (product != null) {
					PriceOffStrategy strategy = getPriceOffStrategy(priceOff);
					product.setPriceOff(strategy);
				}
			}
		}
	}

	static PriceOffStrategy getPriceOffStrategy(PriceOff priceOff) {
		if (priceOff.getId().equals("1")) {
			return new BundleSelling(2, 1, priceOff.getName());
		}
		if (priceOff.getId().equals("2")) {
			return new PriceDiscount(Double.parseDouble(priceOff.getDiscount()), priceOff.getName());
		}
		return null;
	}

	static Product getProductByBarcode(String barCode) {
		checkState(!products.isEmpty(), "products can not be empty");
		for (Product product : products) {
			if (product.getBarCode().equals(barCode)) {
				return product;
			}
		}
		return null;
	}

	public List<Product> getAllProducts() {
		return Collections.unmodifiableList(products);
	}

	public List<PriceOff> getAllPriceOffs() {
		return priceOffs;
	}

	public ProductsCheckoutView calculateCheckout(List<String> barCodes){
		//TODO not yet implemented.
		return null;
	}
}
