package com.mca.pruebaproducto.entity;

public class ProductSizeEntity {
    private Integer id;
    private Integer productId;
    private boolean backSoon;
    private boolean special;

    private SizeStockEntity stock;

    public ProductSizeEntity(Integer id, Integer productId, boolean backSoon, boolean special) {
        this.id = id;
        this.productId = productId;
        this.backSoon = backSoon;
        this.special = special;
    }

    public SizeStockEntity getStock() {
        return stock;
    }

    public void setStock(SizeStockEntity stock) {
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public boolean isBackSoon() {
        return backSoon;
    }

    public void setBackSoon(boolean backSoon) {
        this.backSoon = backSoon;
    }

    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

}
