package com.ivanov.solid_t.repositories;

import com.ivanov.solid_t.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SaleRepository extends CrudRepository<Sale, Integer> {

    @Override
    @Query("select s from Sale s order by s.updatedAt desc")
    Iterable<Sale> findAll();

}
