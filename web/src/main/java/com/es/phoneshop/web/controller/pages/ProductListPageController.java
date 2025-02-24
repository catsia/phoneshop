package com.es.phoneshop.web.controller.pages;

import com.es.core.cart.Cart;
import com.es.core.model.phone.dao.PhoneDao;
import com.es.core.model.phone.dao.SortField;
import com.es.core.model.phone.dao.SortOrder;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${product.list.limit}")
    private Integer limit;

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model,
                                  @RequestParam(required = false, defaultValue = "1", value = "page") Integer page,
                                  @RequestParam(required = false, value = "sort") String sort,
                                  @RequestParam(required = false, value = "order") String order,
                                  @RequestParam(required = false, value = "query") String query) {
        int offset = (page - 1) * limit;

        SortField sortField = null;
        SortOrder sortOrder = null;
        if ((sort != null && order != null) && !(sort.isEmpty() && order.isEmpty())) {
            sortField = SortField.valueOf(sort);
            sortOrder = SortOrder.valueOf(order);
        }

        int maxPage = (int) Math.ceil(phoneDao.count(query) / (double) limit);
        if (page + 1 == maxPage) {
            model.addAttribute("nextPage", null);
        } else {
            model.addAttribute("nextPage", page + 1);
        }

        model.addAttribute("phones", phoneDao.findAll(offset, limit, sortField, sortOrder, query));
        model.addAttribute("cart", "My cart: " + cart.getTotalQuantity() + " items $ " + cart.getTotalCost());
        model.addAttribute("currentPage", page);
        model.addAttribute("previousPage", page == 1 ? page : page - 1);
        model.addAttribute("maxPage", maxPage);
        return "productList";
    }
}
