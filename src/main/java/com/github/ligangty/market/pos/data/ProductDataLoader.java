package com.github.ligangty.market.pos.data;

import com.github.ligangty.market.pos.domain.PriceOff;
import com.github.ligangty.market.pos.domain.Product;
import com.github.ligangty.market.pos.domain.priceoff.BundleSelling;
import com.github.ligangty.market.pos.domain.priceoff.PriceDiscount;
import com.github.ligangty.market.pos.domain.priceoff.PriceOffStrategy;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

/**
 * Used to load the Product and PriceOff meta data.
 * Because all data is stored as a simple json data file,
 * to be simple, make all method as static and init the
 * meta data loading in a static block
 * <p>
 * If the data will be stored in database, need to do as normal
 * way for data fetch layer.
 */
public class ProductDataLoader {

	private static final Logger LOG = LoggerFactory.getLogger(ProductDataLoader.class);

	private static final List<Product> products = new ArrayList<>();
	private static final List<PriceOff> priceOffs = new ArrayList<>();

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

	/**
	 * Load all products meta data from classpath:productsData.json
	 *
	 * @throws IOException
	 */
	private static void loadProducts() throws IOException {
		products.clear();
		InputStream stream = ProductDataLoader.class.getResourceAsStream("/productsData.json");
		try (JsonReader jsonReader = new JsonReader(new InputStreamReader(stream))) {
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
							switch (tokenName) {
								case "name":
									product.setName(jsonReader.nextString());
									break;
								case "barCode":
									product.setBarCode(jsonReader.nextString());
									break;
								case "unit":
									product.setUnit(jsonReader.nextString());
									break;
								case "price":
									product.setPrice(Double.parseDouble(jsonReader.nextString()));
									break;
								default:
									break;
							}
						}
						products.add(product);
						jsonReader.endObject();
					}
					jsonReader.endArray();
				}
			}
			jsonReader.endObject();
		}
	}

	/**
	 * Load all price off strategies meta data from classpath:priceOffData.json
	 *
	 * @throws IOException
	 */
	private static void loadPriceOff() throws IOException {
		priceOffs.clear();
		InputStream stream = ProductDataLoader.class.getResourceAsStream("/priceOffData.json");

		try (JsonReader jsonReader = new JsonReader(new InputStreamReader(stream))) {
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
							switch (tokenName) {
								case "id":
									priceOff.setId(jsonReader.nextString());
									break;
								case "name":
									priceOff.setName(jsonReader.nextString());
									break;
								case "products":
									List<String> products = new ArrayList<>();
									jsonReader.beginArray();
									while (jsonReader.hasNext()) {
										products.add(jsonReader.nextString());
									}
									jsonReader.endArray();
									priceOff.setProducts(products);
									break;
								case "discount":
									priceOff.setDiscount(jsonReader.nextString());
									break;
								default:
									break;
							}
						}
						priceOffs.add(priceOff);
						jsonReader.endObject();
					}
					jsonReader.endArray();
				}
			}
			jsonReader.endObject();
		}
	}

	/**
	 * When products and price off meta data loading done,
	 * establish the relationship between these two meta data.
	 */
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

	/**
	 * @param priceOff - the meta data object mapping to priceOffData.json
	 * @return the PriceOffStrategy object which includes the algorithm of the price off way
	 */
	public static PriceOffStrategy getPriceOffStrategy(PriceOff priceOff) {
		switch (priceOff.getId()) {
			case "1":
				return new BundleSelling(2, 1, priceOff.getName());
			case "2":
				return new PriceDiscount(Double.parseDouble(priceOff.getDiscount()), priceOff.getName());
			case "3":
				return new PriceDiscount(Double.parseDouble(priceOff.getDiscount()), priceOff.getName());
			case "4":
				return new BundleSelling(5, 2, priceOff.getName());
			default:
				return null;
		}
	}

	/**
	 * @param barCode product bar code
	 * @return
	 */
	public static Product getProductByBarcode(String barCode) {
		checkState(!products.isEmpty(), "products can not be empty");
		for (Product product : products) {
			if (product.getBarCode().equals(barCode)) {
				return product;
			}
		}
		return null;
	}

	public static boolean hasProduct(String barCode) {
		return getProductByBarcode(barCode) != null;
	}

	static List<Product> getAllProducts() {
		return Collections.unmodifiableList(products);
	}

	static List<PriceOff> getAllPriceOffs() {
		return Collections.unmodifiableList(priceOffs);
	}
}
