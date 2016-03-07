package com.github.ligangty.market.pos.domain.priceoff;

import com.github.ligangty.market.pos.domain.Product;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BundleStrategyTest {

	@Test
	public void testCalculateTotalPrice() {

		PriceOffStrategy discount = new BundleSelling(2, 1);

		Product product = new Product("ITEM000001", "羽毛球", 1.00, "个");
		assertEquals(1.00, discount.calculateOffedTotalPrice(1, product), 0.01);
		assertEquals(2.00, discount.calculateOffedTotalPrice(2, product), 0.01);
		assertEquals(2.00, discount.calculateOffedTotalPrice(3, product), 0.01);
		assertEquals(4.00, discount.calculateOffedTotalPrice(6, product), 0.01);
		assertEquals(7.00, discount.calculateOffedTotalPrice(10, product), 0.01);
		assertEquals(67.00, discount.calculateOffedTotalPrice(100, product), 0.01);

		product = new Product("ITEM000005", "可口可乐", 3, "瓶");
		assertEquals(3.00, discount.calculateOffedTotalPrice(1, product), 0.01);
		assertEquals(6.00, discount.calculateOffedTotalPrice(2, product), 0.01);
		assertEquals(6.00, discount.calculateOffedTotalPrice(3, product), 0.01);
		assertEquals(12.00, discount.calculateOffedTotalPrice(6, product), 0.01);
		assertEquals(21.00, discount.calculateOffedTotalPrice(10, product), 0.01);
		assertEquals(201.00, discount.calculateOffedTotalPrice(100, product), 0.01);

		discount = new BundleSelling(5, 2);

		product = new Product("ITEM000001", "羽毛球", 1.00, "个");
		assertEquals(1.00, discount.calculateOffedTotalPrice(1, product), 0.01);
		assertEquals(2.00, discount.calculateOffedTotalPrice(2, product), 0.01);
		assertEquals(3.00, discount.calculateOffedTotalPrice(3, product), 0.01);
		assertEquals(5.00, discount.calculateOffedTotalPrice(5, product), 0.01);
		assertEquals(5.00, discount.calculateOffedTotalPrice(6, product), 0.01);
		assertEquals(5.00, discount.calculateOffedTotalPrice(7, product), 0.01);
		assertEquals(6.00, discount.calculateOffedTotalPrice(8, product), 0.01);
		assertEquals(8.00, discount.calculateOffedTotalPrice(10, product), 0.01);
		assertEquals(72.00, discount.calculateOffedTotalPrice(100, product), 0.01);
		assertEquals(75.00, discount.calculateOffedTotalPrice(104, product), 0.01);
	}

	@Test
	public void testCalculateGivenNumber() {
		BundleSelling discount = new BundleSelling(2, 1);

		assertEquals(Integer.valueOf(0), discount.calculateGivenNumber(1));
		assertEquals(Integer.valueOf(0), discount.calculateGivenNumber(2));
		assertEquals(Integer.valueOf(1), discount.calculateGivenNumber(3));
		assertEquals(Integer.valueOf(2), discount.calculateGivenNumber(6));
		assertEquals(Integer.valueOf(3), discount.calculateGivenNumber(10));
		assertEquals(Integer.valueOf(33), discount.calculateGivenNumber(100));

		discount = new BundleSelling(5, 2);

		assertEquals(Integer.valueOf(0), discount.calculateGivenNumber(1));
		assertEquals(Integer.valueOf(0), discount.calculateGivenNumber(2));
		assertEquals(Integer.valueOf(0), discount.calculateGivenNumber(3));
		assertEquals(Integer.valueOf(0), discount.calculateGivenNumber(5));
		assertEquals(Integer.valueOf(1), discount.calculateGivenNumber(6));
		assertEquals(Integer.valueOf(2), discount.calculateGivenNumber(7));
		assertEquals(Integer.valueOf(2), discount.calculateGivenNumber(8));
		assertEquals(Integer.valueOf(2), discount.calculateGivenNumber(10));
		assertEquals(Integer.valueOf(3), discount.calculateGivenNumber(13));
		assertEquals(Integer.valueOf(4), discount.calculateGivenNumber(14));
		assertEquals(Integer.valueOf(4), discount.calculateGivenNumber(15));
		assertEquals(Integer.valueOf(28), discount.calculateGivenNumber(100));
		assertEquals(Integer.valueOf(29), discount.calculateGivenNumber(104));

	}
}
