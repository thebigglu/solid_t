package com.ivanov.solid_t.services;

import com.ivanov.solid_t.entities.Discount;
import com.ivanov.solid_t.entities.Sale;
import com.ivanov.solid_t.repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    private DiscountRepository discountRepository;

    @Autowired
    public void setDiscountRepository(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public Iterable<Discount> listAllDiscounts() {
        return discountRepository.findAll();
    }

    @Override
    public Discount saveDiscount(Discount discount) {
        discountRepository.save(discount);
        return discount;
    }

    @Override
    public void saveAllDiscounts(List<Discount> discounts) {
        discountRepository.saveAll(discounts);
    }

    @Override
    public void deleteAllDiscounts() {
        discountRepository.deleteAll();
    }

    @Override
    public Sale setDiscounts(Sale sale) {
        sale.getSalePositions().forEach(salePosition -> {
            List<Discount> discounts = discountRepository.findByProductId(salePosition.getProduct().getId());
            discounts.forEach(discount -> {
                LocalDateTime discountDate = discount.getDate();
                LocalDateTime saleDate = sale.getDate();
                if (discountDate.isBefore(saleDate) && discountDate.plusHours(1).isAfter(saleDate)) {
                    salePosition.setDiscount(discount);
                }
            });
        });
        return sale;
    }

}
