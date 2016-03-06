package com.github.ligangty.market.pos.service;

import java.io.IOException;

import com.github.ligangty.market.pos.domain.Product;
import com.github.ligangty.market.pos.domain.priceoff.PriceOffStrategy;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductsServiceTest {

	ProductsService service = new ProductsService();

	@Test
	public void testInit() throws IOException {
		assertEquals(3, service.getAllProducts().size());
		assertEquals(2, service.getAllPriceOffs().size());
	}

	@Test
	public void testGetProductByBarcode() {
		Product product = ProductsService.getProductByBarcode("ITEM000001");
		assertEquals("ITEM000001", product.getBarCode());
		assertEquals("羽毛球", product.getName());
		assertEquals("个", product.getUnit());
		assertEquals(1.00, product.getPrice(), 0.001);

		product = ProductsService.getProductByBarcode("ITEM000004");
		assertNull(product);
	}

	@Test
	public void testStrategyEstablish() {
		Product product = ProductsService.getProductByBarcode("ITEM000001");
		PriceOffStrategy strategy = product.getPriceOff();

		assertNotNull(strategy);
		assertEquals("买2赠1商品",strategy.getName());

	}
}
