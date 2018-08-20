package com.ivanov.solid_t.controllers;

import com.ivanov.solid_t.entities.Product;
import com.ivanov.solid_t.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }


    @ModelAttribute("productsMenuActive")
    public String productsMenuActive() {
        return "active";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/products";
    }

    @GetMapping("products")
    public String listProduct(Model model) {
        model.addAttribute("products", productService.listAllProductsWithSaleQuantity());
        return "products";
    }

    @PostMapping("product/edit/{id}")
    public String editProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "product_form";
    }

    @PostMapping("product/new")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product());
        return "product_form";
    }

    @PostMapping("product")
    public String saveProduct(@Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product_form";
        }
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @PostMapping("product/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

}
