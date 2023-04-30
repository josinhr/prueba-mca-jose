package com.mca.pruebaproducto.entity;

public class SizeStockEntity {
    private Integer sizeId;
    private Integer quantity;

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
