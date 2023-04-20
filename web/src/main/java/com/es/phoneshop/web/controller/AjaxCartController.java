package com.es.phoneshop.web.controller;

import com.es.core.cart.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/ajaxCart")

public class AjaxCartController {
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> addPhone(@RequestParam(value = "phoneId") Long phoneId,
                                           @RequestParam(value = "quantity") Long quantity) {
        cartService.addPhone(phoneId, quantity);
        return new ResponseEntity<String>("Added to cart", HttpStatus.OK);
    }
}
