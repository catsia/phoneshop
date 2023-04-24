package com.es.phoneshop.web.controller;

import com.es.core.cart.CartService;
import com.es.phoneshop.web.controller.validator.QuantityValidator;
import com.es.phoneshop.web.controller.validator.ValidatedJsonInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    private QuantityValidator validator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> addPhone(@Valid @RequestBody ValidatedJsonInfo info,
                                           BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(getError(result), HttpStatus.BAD_REQUEST);
        }
        cartService.addPhone(info.getPhoneId(), info.getQuantity());
        return new ResponseEntity<>("My cart: " + cartService.getCart().getTotalQuantity() + " items $ " + cartService.getCart().getTotalCost(), HttpStatus.OK);
    }

    private String getError(BindingResult bindingResult) {
        String error = "";
        for (Object object : bindingResult.getAllErrors()) {
            if (object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;
                error += fieldError.getCode() + '\n';
            }
        }
        return error;
    }
}
