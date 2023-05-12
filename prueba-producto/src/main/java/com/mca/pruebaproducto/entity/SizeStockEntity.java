package com.mca.pruebaproducto.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "sizeId", "quantity" })
public class SizeStockEntity implements Serializable {

    @JsonProperty("sizeId")
    private Integer sizeId;

    @JsonProperty("quantity")
    private Integer quantity;

    public SizeStockEntity() {
        super();
    }

    public SizeStockEntity(Integer sizeId, Integer quantity) {
        this.sizeId = sizeId;
        this.quantity = quantity;
    }

    public Integer getSizeId() {
        return sizeId;
    }

    public void setSizeId(Integer sizeId) {
        this.sizeId = sizeId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
