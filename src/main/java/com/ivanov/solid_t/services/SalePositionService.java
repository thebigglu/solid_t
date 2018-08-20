package com.ivanov.solid_t.services;

import com.ivanov.solid_t.entities.SalePosition;

import java.util.List;

public interface SalePositionService {

    void saveAllSalePositions(List<SalePosition> salePositions);

    void deleteAllSalePositions();

}
