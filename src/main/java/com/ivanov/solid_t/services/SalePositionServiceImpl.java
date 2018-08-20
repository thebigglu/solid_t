package com.ivanov.solid_t.services;

import com.ivanov.solid_t.entities.SalePosition;
import com.ivanov.solid_t.repositories.SalePositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalePositionServiceImpl implements SalePositionService {

    private SalePositionRepository salePositionRepository;

    @Autowired
    public void setSalePositionRepository(SalePositionRepository salePositionRepository) {
        this.salePositionRepository = salePositionRepository;
    }

    @Override
    public void saveAllSalePositions(List<SalePosition> salePositions) {
        salePositionRepository.saveAll(salePositions);
    }

    @Override
    public void deleteAllSalePositions() {
        salePositionRepository.deleteAll();
    }

}
