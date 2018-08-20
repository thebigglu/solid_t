package com.ivanov.solid_t.tasks;

import com.ivanov.solid_t.entities.Discount;
import com.ivanov.solid_t.entities.Product;
import com.ivanov.solid_t.services.DiscountService;
import com.ivanov.solid_t.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DiscountTask {

    private static final int MIN_DISCOUNT = 5;
    private static final int MAX_DISCOUNT = 10 + 1;

    private ProductService productService;

    private DiscountService discountService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setDiscountService(DiscountService discountService) {
        this.discountService = discountService;
    }

    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void createNewDiscount() {
        Discount discount = new Discount();
        discount.setDate(LocalDateTime.now());

        List<Product> products = new ArrayList<>();
        productService.listAllProducts().forEach(products::add);
        if (products.size() == 0) {
            return;
        }

        Random random = new Random();
        Product productForDiscount = products.get(random.nextInt(products.size()));
        discount.setProduct(productForDiscount);

        discount.setValue(MIN_DISCOUNT + random.nextInt(MAX_DISCOUNT - MIN_DISCOUNT));
        discountService.saveDiscount(discount);
    }

}
