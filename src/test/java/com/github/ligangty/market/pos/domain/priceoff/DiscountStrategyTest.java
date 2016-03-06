package com.github.ligangty.market.pos.domain.priceoff;

import com.github.ligangty.market.pos.domain.Product;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gli on 3/3/16.
 */
public class DiscountStrategyTest {

	@Test
	public void testCalculateTotalPrice() {

		PriceOffStrategy discount = new PriceDiscount(0.95);

		Product product = new Product("ITEM000001", "羽毛球", 1.00, "个");
		assertEquals(0.95, discount.calculateOffedTotalPrice(1, product), 0.01);
		assertEquals(95.00, discount.calculateOffedTotalPrice(100, product), 0.01);
		assertEquals(64.60, discount.calculateOffedTotalPrice(68, product), 0.01);

		product = new Product("ITEM000003", "苹果", 5.50, "斤");
		assertEquals(5.225, discount.calculateOffedTotalPrice(1, product), 0.01);
		assertEquals(522.50, discount.calculateOffedTotalPrice(100, product), 0.01);
		assertEquals(355.30, discount.calculateOffedTotalPrice(68, product), 0.01);
		assertEquals(379.335, discount.calculateOffedTotalPrice(72.6, product), 0.01);

	}

}
