package com.ivanov.solid_t.controllers;

import com.ivanov.solid_t.Constants;
import com.ivanov.solid_t.entities.Product;
import com.ivanov.solid_t.entities.Sale;
import com.ivanov.solid_t.entities.SalePosition;
import com.ivanov.solid_t.services.DiscountService;
import com.ivanov.solid_t.services.ProductService;
import com.ivanov.solid_t.services.SalePositionService;
import com.ivanov.solid_t.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SaleController {

    private static final int MIN_COUNT_OF_SALES_POSITIONS = 1;

    private SaleService saleService;

    private SalePositionService salePositionService;

    private ProductService productService;

    private DiscountService discountService;

    @Autowired
    public void setSaleService(SaleService saleService) {
        this.saleService = saleService;
    }

    @Autowired
    public void setSalePositionService(SalePositionService salePositionService) {
        this.salePositionService = salePositionService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setDiscountService(DiscountService discountService) {
        this.discountService = discountService;
    }

    @ModelAttribute("salesMenuActive")
    public String salesMenuActive() {
        return "active";
    }

    @ModelAttribute("dateFormat")
    public String dateFormat() {
        return Constants.DATE_PATTERN;
    }

    @ModelAttribute("products")
    public Iterable<Product> products() {
        return productService.listAllProducts();
    }

    @GetMapping("sales")
    public String listSale(Model model) {
        model.addAttribute("sales", saleService.listAllSales());
        model.addAttribute("products", productService.listAllProducts());
        return "sales";
    }

    @GetMapping("sale/edit/{id}")
    public String editSale(@PathVariable Integer id, Model model) {
        Sale sale = saleService.getSaleById(id);
        model.addAttribute("sale", sale);
        return "sale_form";
    }


    @PostMapping(value="sale", params={"addPosition"})
    public String addPosition(Sale sale, Model model) {
        sale.getSalePositions().add(new SalePosition());
        model.addAttribute("sale", sale);
        return "sale_form";
    }

    @PostMapping(value="sale", params={"removePosition"})
    public String removePosition(Sale sale, HttpServletRequest req, Model model) {
        final Integer positionId = Integer.parseInt(req.getParameter("removePosition"));
        if (sale.getSalePositions().size() > MIN_COUNT_OF_SALES_POSITIONS) {
            sale.getSalePositions().remove(positionId.intValue());
        }
        model.addAttribute("sale", sale);
        return "sale_form";
    }

    @PostMapping("sale")
    public String saveSale(@Valid Sale sale, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sale_form";
        }
        discountService.setDiscounts(sale);
        salePositionService.saveAllSalePositions(sale.getSalePositions());
        saleService.saveSale(sale);
        return "redirect:/sales";
    }

    @PostMapping("sale/new")
    public String newSale(Model model) {
        Sale sale = new Sale();
        List<SalePosition> salePositions = new ArrayList<>();
        salePositions.add(new SalePosition());
        sale.setSalePositions(salePositions);
        sale.setDate(LocalDateTime.now());
        model.addAttribute("sale", sale);
        return "sale_form";
    }

    @PostMapping("sale/delete/{id}")
    public String deleteSale(@PathVariable Integer id) {
        saleService.deleteSale(id);
        return "redirect:/sales";
    }

}
