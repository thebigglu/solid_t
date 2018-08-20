package com.ivanov.solid_t.services;

import com.ivanov.solid_t.entities.Discount;
import com.ivanov.solid_t.entities.Product;
import com.ivanov.solid_t.entities.Sale;
import com.ivanov.solid_t.entities.SalePosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class TestDataBuilderServiceImpl implements TestDataBuilderService {

    private static final int COUNT_OF_PRODUCTS = 10;
    private static final int COUNT_OF_DISCOUNTS = 20;
    private static final int COUNT_OF_SALES = 20;
    private static final int MAX_PRODUCT_NAME_VALUE = 100;
    private static final int MAX_PRODUCT_PRICE_VALUE = 100;
    private static final int MAX_PRODUCT_QUANTITY_VALUE = 10;
    private static final int MIN_DISCOUNT_VALUE = 5;
    private static final int MAX_DISCOUNT_VALUE = 10 + 1;
    private static final int VARIATION_HOURS_FOR_SALE = 20;
    private static final int VARIATION_MINUTES_FOR_SALE = 50;
    private static final int MAX_COUNT_OF_SALE_POSITIONS = 5;

    private ProductService productService;

    private DiscountService discountService;

    private SaleService saleService;

    private SalePositionService salePositionService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setDiscountService(DiscountService discountService) {
        this.discountService = discountService;
    }

    @Autowired
    public void setSaleService(SaleService saleService) {
        this.saleService = saleService;
    }

    @Autowired
    public void setSalePositionService(SalePositionService salePositionService) {
        this.salePositionService = salePositionService;
    }

    @Override
    public void buildTestData() {
        deleteAllData();
        Random random = new Random();

        List<Product> products = buildProducts(random);
        productService.saveAllProducts(products);

        List<Discount> discounts = buildDiscounts(random, products);
        discountService.saveAllDiscounts(discounts);

        List<Sale> sales = buildSales(random, products, discounts);
        saleService.saveAllSales(sales);
    }

    @Override
    public void deleteAllData() {
        saleService.deleteAllSales();
        salePositionService.deleteAllSalePositions();
        discountService.deleteAllDiscounts();
        productService.deleteAllProducts();
    }

    private List<Product> buildProducts(Random random) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < COUNT_OF_PRODUCTS; i++) {
            Product product = new Product();
            product.setName("Product " + random.nextInt(MAX_PRODUCT_NAME_VALUE));
            product.setPrice(new BigDecimal(1 + random.nextInt(MAX_PRODUCT_PRICE_VALUE)));
            products.add(product);
        }
        return products;
    }

    private List<Discount> buildDiscounts(Random random, List<Product> products) {
        List<Discount> discounts = new ArrayList<>();
        for (int i = 0; i < COUNT_OF_DISCOUNTS; i++) {
            Discount discount = new Discount();
            discount.setProduct(products.get(random.nextInt(products.size())));
            discount.setValue(MIN_DISCOUNT_VALUE + random.nextInt(MAX_DISCOUNT_VALUE - MIN_DISCOUNT_VALUE));
            discount.setDate(LocalDateTime.now().minusHours(i));
            discounts.add(discount);
        }
        return discounts;
    }

    private List<Sale> buildSales(Random random, List<Product> products, List<Discount> discounts) {
        List<Sale> sales = new ArrayList<>();
        for (int i = 0; i < COUNT_OF_SALES; i++) {
            Sale sale = new Sale();
            sale.setDate(LocalDateTime.now()
                    .minusHours(random.nextInt(VARIATION_HOURS_FOR_SALE))
                    .minusMinutes(random.nextInt(VARIATION_MINUTES_FOR_SALE)));

            List<SalePosition> salePositionList = buildSalePositions(random, products, discounts, sale);
            salePositionService.saveAllSalePositions(salePositionList);
            sale.setSalePositions(salePositionList);
            sales.add(sale);
        }
        return sales;
    }

    private List<SalePosition> buildSalePositions(Random random, List<Product> products,
                                                  List<Discount> discounts, Sale sale) {
        int countOfSalePositions = 1 + random.nextInt(MAX_COUNT_OF_SALE_POSITIONS);
        List<SalePosition> salePositionList = new ArrayList<>();
        List<Product> tempProducts = new ArrayList<>(products);
        for (int j = 0; j < countOfSalePositions; j++) {
            SalePosition salePosition = new SalePosition();

            Product product = tempProducts.get(random.nextInt(tempProducts.size()));
            salePosition.setProduct(product);
            tempProducts.remove(product);

            LocalDateTime saleDate = sale.getDate();
            for (Discount discount : discounts) {
                if (Objects.equals(product.getId(), discount.getProduct().getId())) {
                    LocalDateTime discountDate = discount.getDate();
                    if (discountDate.isBefore(saleDate) && discountDate.plusHours(1).isAfter(saleDate)) {
                        salePosition.setDiscount(discount);
                    }
                }
            }

            salePosition.setQuantity(1 + random.nextInt(MAX_PRODUCT_QUANTITY_VALUE));
            salePositionList.add(salePosition);
        }
        return salePositionList;
    }

}
