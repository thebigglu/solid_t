package com.ivanov.solid_t.services;

import com.ivanov.solid_t.dto.ProductDTO;
import com.ivanov.solid_t.entities.Product;

import java.util.List;

public interface ProductService {

    Iterable<Product> listAllProducts();

    Iterable<ProductDTO> listAllProductsWithSaleQuantity();

    Product getProductById(Integer id);

    Product saveProduct(Product product);

    void deleteProduct(Integer id);

    void saveAllProducts(List<Product> products);

    void deleteAllProducts();

}
