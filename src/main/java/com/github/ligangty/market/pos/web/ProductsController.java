package com.github.ligangty.market.pos.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductsController {
    private static final Logger LOG = LoggerFactory.getLogger(ProductsController.class);

    // do not add headers in @RequestMapping as it will cause response encoding problems
    @ResponseBody
    @RequestMapping(value = "category/{categoryType}/all", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8", method = RequestMethod.GET)
    public ResponseEntity<String> allProducts(@PathVariable("") String arg) {
        String resultsJson = "";
        LOG.debug(resultsJson);
        return new ResponseEntity<String>(resultsJson, HttpStatus.OK);
    }


}
