package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.OrderStatus;
import com.es.core.order.OrderNotFound;
import com.es.core.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/admin/orders")
public class OrdersPageController {

    @Resource
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public String getOrders(Model model) {
        model.addAttribute("orders", orderService.getOrders());
        return "adminOrders";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public String getOrder(@PathVariable("id") Long id, Model model) {
        try {
            model.addAttribute("order", orderService.getOrder(id));
        } catch (OrderNotFound e) {
            return "adminOrderNotFound";
        }
        return "adminOrderOverview";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}")
    public String setStatus(@PathVariable("id") Long id, @RequestParam OrderStatus status, Model model) {
        orderService.updateStatus(id, status);
        return "redirect:/admin/orders/" + id;
    }
}
