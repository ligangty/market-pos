package com.github.ligangty.market.pos.web;

import java.util.List;

import com.github.ligangty.market.pos.service.ProductsService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductsController {

	ProductsService productsService;

	private static final Logger LOG = LoggerFactory.getLogger(ProductsController.class);

	// do not add headers in @RequestMapping as it will cause response encoding problems
	@ResponseBody
	@RequestMapping(value = "products/barcode", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8", method = RequestMethod.POST)
	public ResponseEntity<String> allProducts(@RequestBody String jsonBarCode) {
		String shoppingBarcodes = jsonBarCode.replaceAll("\\\\n", "").replaceAll("\\\\r", "").replaceAll("\\s", "").substring(1);
		shoppingBarcodes = shoppingBarcodes.substring(0, shoppingBarcodes.length() - 1);
		return new ResponseEntity<String>(getCheckout(shoppingBarcodes), HttpStatus.OK);
	}

	private String getCheckout(String requestJson) {
		LOG.debug(requestJson);
		List<String> barCodes = null;
		try {
			barCodes = new Gson().fromJson(requestJson, List.class);
		} catch (JsonSyntaxException e) {
			LOG.error("json format error: ", e);
			throw e;
		}
		return "";
	}

}
