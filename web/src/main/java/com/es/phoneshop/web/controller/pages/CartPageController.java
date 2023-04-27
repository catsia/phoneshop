package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartItemConverter;
import com.es.core.cart.CartItemReducedDto;
import com.es.core.cart.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;

    @Resource
    private CartItemConverter cartItemConverter;

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        model.addAttribute("cartItemReducedDto", new CartItemReducedDto());
        model.addAttribute("cart", "My cart: " + cartService.getCart().getTotalQuantity() + " items $ " + cartService.getCart().getTotalCost());
        model.addAttribute("cartItems", cartService.getCart().getCartItems());
        return "cart";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String updateCart(@Valid @ModelAttribute("cartItemReducedDto") CartItemReducedDto cartItemReducedDto, BindingResult result, Model model) {
        cartService.update(cartItemConverter.convertToCartItems(cartItemReducedDto.getCartItemReduced()));
        return "redirect:/cart";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String deleteCartItem(@RequestParam("phoneId") Long phoneId) {
        cartService.remove(phoneId);
        return "redirect:/cart";
    }
}
