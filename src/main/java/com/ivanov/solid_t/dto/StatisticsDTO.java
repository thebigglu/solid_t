package com.ivanov.solid_t.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StatisticsDTO {

    private LocalDateTime hour;

    private int saleQuantity;

    private BigDecimal sum;

    private BigDecimal average;

    private BigDecimal sumOfDiscounts;

    private BigDecimal sumWithDiscounts;

    private BigDecimal averageWithDiscounts;

    public LocalDateTime getHour() {
        return hour;
    }

    public void setHour(LocalDateTime hour) {
        this.hour = hour;
    }

    public int getSaleQuantity() {
        return saleQuantity;
    }

    public void setSaleQuantity(int saleQuantity) {
        this.saleQuantity = saleQuantity;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getAverage() {
        return average;
    }

    public void setAverage(BigDecimal average) {
        this.average = average;
    }

    public BigDecimal getSumOfDiscounts() {
        return sumOfDiscounts;
    }

    public void setSumOfDiscounts(BigDecimal sumOfDiscounts) {
        this.sumOfDiscounts = sumOfDiscounts;
    }

    public BigDecimal getSumWithDiscounts() {
        return sumWithDiscounts;
    }

    public void setSumWithDiscounts(BigDecimal sumWithDiscounts) {
        this.sumWithDiscounts = sumWithDiscounts;
    }

    public BigDecimal getAverageWithDiscounts() {
        return averageWithDiscounts;
    }

    public void setAverageWithDiscounts(BigDecimal averageWithDiscounts) {
        this.averageWithDiscounts = averageWithDiscounts;
    }

}
