package com.es.phoneshop.web.controller;

import com.es.core.cart.CartItemReduced;
import com.es.core.cart.CartService;
import com.es.phoneshop.web.controller.support.BindingResultErrorHandler;
import com.es.phoneshop.web.controller.validator.QuantityValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;


@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @Resource
    private QuantityValidator quantityValidator;

    @Resource
    private BindingResultErrorHandler bindingResultErrorHandler;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(quantityValidator);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> addPhone(@Valid @RequestBody CartItemReduced cartItemReduced,
                                           BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(bindingResultErrorHandler.getError(result), HttpStatus.BAD_REQUEST);
        }
        cartService.addPhone(cartItemReduced.getId(), cartItemReduced.getQuantity());
        return new ResponseEntity<>("My cart: " + cartService.getCart().getTotalQuantity() + " items $ " + cartService.getCart().getTotalCost(), HttpStatus.OK);
    }

}
