package com.ivanov.solid_t.dto;

import java.math.BigDecimal;

public class ProductDTO {

    public ProductDTO(Integer id, Integer version, String name, BigDecimal price, Integer saleQuantity) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.price = price;
        this.saleQuantity = saleQuantity;
    }

    private Integer id;

    private Integer version;

    private String name;

    private BigDecimal price;

    private Integer saleQuantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSaleQuantity() {
        return saleQuantity;
    }

    public void setSaleQuantity(Integer saleQuantity) {
        this.saleQuantity = saleQuantity;
    }

}
