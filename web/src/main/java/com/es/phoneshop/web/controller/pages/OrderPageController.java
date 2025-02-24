package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.CartService;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderReduced;
import com.es.core.order.OrderConverter;
import com.es.core.order.OrderService;
import com.es.core.order.OutOfStockException;
import com.es.phoneshop.web.controller.support.BindingResultErrorHandler;
import com.es.phoneshop.web.controller.validator.OrderValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/order")
public class OrderPageController {
    @Resource
    private OrderService orderService;

    @Resource
    private CartService cartService;

    @Resource
    private OrderValidator orderValidator;

    @Resource
    private BindingResultErrorHandler bindingResultErrorHandler;

    @Resource
    private OrderConverter orderConverter;

    @InitBinder("orderReduced")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(orderValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getOrder(Model model) throws OutOfStockException {
        if (cartService.getCart().getCartItems().isEmpty()) {
            model.addAttribute("error", "Cart is empty");
            return "orderIsEmpty";
        }
        Order order = orderService.createOrder(cartService.getCart());
        if (order.getOrderItems().isEmpty()) {
            model.addAttribute("error", "Order is empty");
            return "orderIsEmpty";
        }
        model.addAttribute("order", order);
        model.addAttribute("orderReduced", new OrderReduced());
        return "order";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String placeOrder(@Valid @ModelAttribute("orderReduced") OrderReduced orderReduced, BindingResult result, RedirectAttributes redirectAttributes, Model model) throws OutOfStockException {
        if (result.hasErrors()) {
            bindingResultErrorHandler.getErrors(result).entrySet().stream().forEach(stringStringEntry ->
                    model.addAttribute(stringStringEntry.getKey() + "Error", stringStringEntry.getValue())
            );
            model.addAttribute("order", orderConverter.convert(orderReduced));
            return "order";
        }
        Order order = orderConverter.convert(orderReduced);
        try {
            orderService.placeOrder(order);
        } catch (OutOfStockException e) {
            model.addAttribute("outOfStockError", e.getMessage());
            model.addAttribute("order", order);
            return "order";
        }
        cartService.removeAll();
        return "redirect:/orderOverview/" + order.getSecureId();
    }
}
