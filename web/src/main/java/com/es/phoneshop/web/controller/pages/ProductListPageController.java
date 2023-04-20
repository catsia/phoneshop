package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.model.phone.dao.PhoneDao;
import com.es.core.model.phone.dao.SortField;
import com.es.core.model.phone.dao.SortOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private Cart cart;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model,
                                  @RequestParam(required = false, defaultValue = "1", value = "page") Integer page,
                                  @RequestParam(required = false, value = "sort") String sort,
                                  @RequestParam(required = false, value = "order") String order) {
        int limit = 10;
        int offset = (page - 1) * limit;

        if (sort != null && order != null) {
            SortField sortField = SortField.valueOf(sort);
            SortOrder sortOrder = SortOrder.valueOf(order);
            model.addAttribute("phones", phoneDao.findAllWithSortParameters(offset, limit, sortField, sortOrder));
        } else {
            model.addAttribute("phones", phoneDao.findAll(offset, limit));
        }
        model.addAttribute("cart", "My cart: " + cart.getTotalQuantity() + " items $ " + cart.getTotalCost());
        model.addAttribute("currentPage", page);
        model.addAttribute("previousPage", page == 1 ? page : page - 1);
        model.addAttribute("nextPage", page + 1);
        return "productList";
    }
}
