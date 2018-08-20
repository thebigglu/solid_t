package com.ivanov.solid_t.services;

import com.ivanov.solid_t.entities.Discount;
import com.ivanov.solid_t.entities.Sale;

import java.util.List;

public interface DiscountService {

    Iterable<Discount> listAllDiscounts();

    Discount saveDiscount(Discount discount);

    void saveAllDiscounts(List<Discount> discounts);

    void deleteAllDiscounts();

    Sale setDiscounts(Sale sale);

}
