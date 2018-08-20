package com.ivanov.solid_t.repositories;

import com.ivanov.solid_t.entities.Discount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiscountRepository extends CrudRepository<Discount, Integer> {

    @Override
    @Query("select d from Discount d order by d.date asc")
    Iterable<Discount> findAll();

    @Query("select d from Discount d where d.product.id = ?1")
    List<Discount> findByProductId(int productId);

}