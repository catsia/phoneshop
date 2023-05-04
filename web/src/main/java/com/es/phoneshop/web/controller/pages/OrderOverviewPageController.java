package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.Order;
import com.es.core.order.OrderNotFound;
import com.es.core.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/orderOverview/{id}")
public class OrderOverviewPageController {

    @Resource
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public String showOrderOverview(@PathVariable("id") Long id, Model model) {
        Order order;
        try {
            order = orderService.getOrder(id);
        } catch (OrderNotFound e) {
            model.addAttribute("error", e.getMessage());
            return "orderNotFound";
        }
        model.addAttribute("order", order);
        return "orderOverview";
    }
}
