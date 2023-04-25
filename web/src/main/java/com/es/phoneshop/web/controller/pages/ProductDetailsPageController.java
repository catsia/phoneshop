package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.model.phone.Phone;
import com.es.core.model.phone.dao.PhoneDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
@RequestMapping(value = "/productDetails/{phoneId}")
public class ProductDetailsPageController {
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private Cart cart;

    @RequestMapping(method = RequestMethod.GET)
    public String showDetails(Model model,
                              @PathVariable(value = "phoneId") Long phoneId) {
        Optional<Phone> phone = phoneDao.get(phoneId);
        if (!phone.isPresent()) {
            return "productNotFound";
        }
        model.addAttribute("phone", phoneDao.get(phoneId).get());
        model.addAttribute("cart", "My cart: " + cart.getTotalQuantity() + " items $ " + cart.getTotalCost());
        return "productDetails";
    }
}
