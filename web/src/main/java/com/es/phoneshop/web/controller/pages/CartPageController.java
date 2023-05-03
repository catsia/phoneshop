package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartItemConverter;
import com.es.core.cart.CartItemReducedDto;
import com.es.core.cart.CartService;
import com.es.phoneshop.web.controller.support.BindingResultErrorHandler;
import com.es.phoneshop.web.controller.validator.QuantityValidatorForDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;

    @Resource
    private QuantityValidatorForDto validatorForDto;

    @Resource
    private CartItemConverter cartItemConverter;

    @Resource
    private BindingResultErrorHandler bindingResultErrorHandler;

    @InitBinder("cartItemReducedDto")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validatorForDto);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(Model model) {
        Map<String, String> errors = (Map<String, String>) model.asMap().get("errors");
        String successes = (String) model.asMap().get("successes");

        if (errors != null && !errors.isEmpty()) {
            model.addAttribute("errors", errors);
        } else if (successes != null && !successes.isEmpty()) {
            model.addAttribute("successes", successes);
        }
        model.addAttribute("cartItemReducedDto", new CartItemReducedDto());
        model.addAttribute("cart", cartService.getCart());
        model.addAttribute("cartItems", cartService.getCart().getCartItems());
        return "cart";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String updateCart(@Valid @ModelAttribute("cartItemReducedDto") CartItemReducedDto cartItemReducedDto, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResultErrorHandler.getErrorsForCart(result));
        } else {
            redirectAttributes.addFlashAttribute("successes", "Cart successfully updated");
            cartService.update(cartItemConverter.convertToCartItems(cartItemReducedDto.getCartItemReduced()));
        }
        return "redirect:/cart";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String deleteCartItem(@RequestParam("phoneId") Long phoneId) {
        cartService.remove(phoneId);
        return "redirect:/cart";
    }
}
