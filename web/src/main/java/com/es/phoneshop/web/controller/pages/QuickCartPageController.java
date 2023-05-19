package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.cart.QuickCart;
import com.es.phoneshop.web.controller.support.BindingResultErrorHandler;
import com.es.phoneshop.web.controller.validator.ValidatorForQuickCart;
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
@RequestMapping(value = "/quickCart")
public class QuickCartPageController {
    @Resource
    private CartService cartService;

    @Resource
    private ValidatorForQuickCart validatorForQuickCart;

    @Resource
    private BindingResultErrorHandler bindingResultErrorHandler;

    @InitBinder("quickCart")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validatorForQuickCart);
    }

    @GetMapping
    public String getCart(Model model) {
        Map<String, String> errors = (Map<String, String>) model.asMap().get("errors");
        QuickCart quickCart = (QuickCart) model.asMap().get("quickCart");
        String successes = (String) model.asMap().get("successes");
        if (errors != null && !errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("quickCartAfterErrors", quickCart);
        }
        if (successes != null && !successes.isEmpty()) {
            model.addAttribute("successes", successes);
        }
        model.addAttribute("quickCart", new QuickCart());
        model.addAttribute("cart", cartService.getCart());
        return "quickCart";
    }


    @PostMapping
    public String addCartItems(@Valid @ModelAttribute("quickCart") QuickCart quickCart, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResultErrorHandler.getErrors(result));
            redirectAttributes.addFlashAttribute("quickCart", quickCart);
        } else {
            redirectAttributes.addFlashAttribute("successes", "Successfully added");
            cartService.addQuickCart(quickCart);
        }
        return "redirect:/quickCart";
    }
}
