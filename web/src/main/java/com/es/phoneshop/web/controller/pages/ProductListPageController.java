package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.PhoneDao;
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

    @RequestMapping(method = RequestMethod.GET)
    public String showProductList(Model model, @RequestParam(required = false, defaultValue = "1", value = "page") Integer page) {
        int limit = 10;
        int offset = page * limit;
        model.addAttribute("phones", phoneDao.findAll(offset, limit));
        model.addAttribute("currentPage", page);
        model.addAttribute("previousPage", page == 1 ? page : page - 1);
        model.addAttribute("nextPage", page + 1);
        return "productList";
    }
}
