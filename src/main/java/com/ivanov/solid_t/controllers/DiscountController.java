package com.ivanov.solid_t.controllers;

import com.ivanov.solid_t.Constants;
import com.ivanov.solid_t.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class DiscountController {

    private DiscountService discountService;

    @Autowired
    public void setDiscountService(DiscountService discountService) {
        this.discountService = discountService;
    }

    @ModelAttribute("discountsMenuActive")
    public String discountsMenuActive() {
        return "active";
    }

    @ModelAttribute("dateFormat")
    public String dateFormat() {
        return Constants.DATE_PATTERN;
    }

    @GetMapping("discounts")
    public String discounts(Model model) {
        model.addAttribute("discounts", discountService.listAllDiscounts());
        return "discounts";
    }

}
