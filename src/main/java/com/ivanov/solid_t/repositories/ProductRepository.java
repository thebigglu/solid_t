package com.ivanov.solid_t.repositories;

import com.ivanov.solid_t.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Override
    @Query("select p from Product p where p.deleted <> true order by p.updatedAt desc")
    Iterable<Product> findAll();

}