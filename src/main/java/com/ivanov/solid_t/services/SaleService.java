package com.ivanov.solid_t.services;

import com.ivanov.solid_t.dto.StatisticsDTO;
import com.ivanov.solid_t.entities.Sale;

import java.util.List;

public interface SaleService {

    Iterable<Sale> listAllSales();

    List<StatisticsDTO> getStatistics();

    Sale getSaleById(Integer id);

    Sale saveSale(Sale sale);

    void deleteSale(Integer id);

    void saveAllSales(List<Sale> sales);

    void deleteAllSales();

}
