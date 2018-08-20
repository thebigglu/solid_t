package com.ivanov.solid_t.services;

import com.ivanov.solid_t.dto.ProductDTO;
import com.ivanov.solid_t.entities.Product;
import com.ivanov.solid_t.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    private static final int ID_INDEX = 0;
    private static final int SUM_INDEX = 1;

    @PersistenceContext
    private EntityManager em;

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Iterable<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Iterable<ProductDTO> listAllProductsWithSaleQuantity() {
        Query q = em.createNativeQuery(
                "SELECT product_id, sum(quantity) FROM sale_position GROUP BY product_id;");
        @SuppressWarnings("unchecked")
        List<Object[]> result = q.getResultList();

        Map<Integer, Integer> saleQuantities = new HashMap<>();
        result.forEach(record -> {
            int productId = Integer.parseInt(record[ID_INDEX].toString());
            int saleQuantity = Integer.parseInt(record[SUM_INDEX].toString());
            saleQuantities.put(productId, saleQuantity);
        });

        Iterable<Product> products = listAllProducts();

        List<ProductDTO> productsDTOList = new ArrayList<>();
        products.forEach(product -> {
            ProductDTO productDTO = new ProductDTO(
                    product.getId(),
                    product.getVersion(),
                    product.getName(),
                    product.getPrice(),
                    saleQuantities.get(product.getId())
            );
            productsDTOList.add(productDTO);
        });

        return productsDTOList;
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) return;
        product.setDeleted(true);
        productRepository.save(product);
    }

    @Override
    public void saveAllProducts(List<Product> products) {
        productRepository.saveAll(products);
    }

    @Override
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

}