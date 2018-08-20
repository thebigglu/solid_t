package com.ivanov.solid_t.controllers;

import com.ivanov.solid_t.Constants;
import com.ivanov.solid_t.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class StatisticsController {

    private SaleService saleService;

    @Autowired
    public void setSaleService(SaleService saleService) {
        this.saleService = saleService;
    }

    @ModelAttribute("statisticsMenuActive")
    public String statisticsMenuActive() {
        return "active";
    }

    @ModelAttribute("dateFormat")
    public String dateFormat() {
        return Constants.DATE_PATTERN;
    }

    @GetMapping("statistics")
    public String statistics(Model model) {
        model.addAttribute("statistics", saleService.getStatistics());
        return "statistics";
    }

}
