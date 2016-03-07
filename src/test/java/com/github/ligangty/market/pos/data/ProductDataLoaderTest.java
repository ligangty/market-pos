package com.github.ligangty.market.pos.data;

import com.github.ligangty.market.pos.domain.Product;
import com.github.ligangty.market.pos.domain.priceoff.PriceOffStrategy;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ProductDataLoaderTest {

	@Test
	public void testInit() throws IOException {
		assertEquals(3, ProductDataLoader.getAllProducts().size());
		assertEquals(2, ProductDataLoader.getAllPriceOffs().size());
	}

	@Test
	public void testGetProductByBarcode() {
		Product product = ProductDataLoader.getProductByBarcode("ITEM000001");
		assertEquals("ITEM000001", product.getBarCode());
		assertEquals("羽毛球", product.getName());
		assertEquals("个", product.getUnit());
		assertEquals(1.00, product.getPrice(), 0.001);

		product = ProductDataLoader.getProductByBarcode("ITEM000004");
		assertNull(product);
	}

	@Test
	public void testStrategyEstablish() {
		Product product = ProductDataLoader.getProductByBarcode("ITEM000001");
		PriceOffStrategy strategy = product.getPriceOff();

		assertNotNull(strategy);
		assertEquals("买2赠1商品", strategy.getName());

	}
}
