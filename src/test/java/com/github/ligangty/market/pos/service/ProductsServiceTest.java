package com.github.ligangty.market.pos.service;

import com.github.ligangty.market.pos.web.view.ProductsCheckoutView;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ProductsServiceTest {
	final ProductsService service = new ProductsService();

	@Test
	public void test() {
		List<String> barCodes = Arrays
			.asList("ITEM000001", "ITEM000001", "ITEM000001", "ITEM000001", "ITEM000001", "ITEM000001", "ITEM000003-2",
			        "ITEM000005", "ITEM000005", "ITEM000005");

		ProductsCheckoutView view = service.calculateCheckout(barCodes);

		assertNotNull(view);
		assertEquals(3, view.getProducts().size());
		assertEquals(1, view.getPriceOff().size());
		assertEquals(2, view.getPriceOff().get(0).getProducts().size());
		assertEquals("20.45", view.getTotal());
		assertEquals("5.55", view.getTotalSave());

		barCodes = Arrays.asList("ITEM000003-2");

		view = service.calculateCheckout(barCodes);

		assertNotNull(view);
		assertEquals(1, view.getProducts().size());
		assertTrue(view.getPriceOff().isEmpty());
		assertEquals("10.45", view.getTotal());
		assertEquals("0.55", view.getTotalSave());


		barCodes = Arrays
			.asList("ITEM000001", "ITEM000001", "ITEM000001", "ITEM000001", "ITEM000001", "ITEM000001");

		view = service.calculateCheckout(barCodes);

		assertNotNull(view);
		assertEquals(1, view.getProducts().size());
		assertEquals(1, view.getPriceOff().size());
		assertEquals(1, view.getPriceOff().get(0).getProducts().size());
		assertEquals("2", view.getPriceOff().get(0).getProducts().get(0).getGiveNumber());
		assertEquals("4.0", view.getTotal());
		assertEquals("2.0", view.getTotalSave());

		barCodes = Arrays
			.asList("ITEM000001", "ITEM000001", "ITEM000001", "ITEM000001", "ITEM000001", "ITEM000001", "ITEM000005",
			        "ITEM000005", "ITEM000005");

		view = service.calculateCheckout(barCodes);

		assertNotNull(view);
		assertEquals(2, view.getProducts().size());
		assertEquals(1, view.getPriceOff().size());
		assertEquals(2, view.getPriceOff().get(0).getProducts().size());
		assertEquals("10.0", view.getTotal());
		assertEquals("5.0", view.getTotalSave());

	}

}
