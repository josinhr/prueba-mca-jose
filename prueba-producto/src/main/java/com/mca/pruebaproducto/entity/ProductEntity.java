package com.mca.pruebaproducto.entity;

import java.util.LinkedList;
import java.util.List;

public class ProductEntity {
    private Integer id;
    private Integer sequence;
    private List<ProductSizeEntity> sizes = new LinkedList<>();

    public ProductEntity(Integer id, Integer sequence) {
        this.id = id;
        this.sequence = sequence;
    }

    public List<ProductSizeEntity> getSizes() {
        return sizes;
    }

    public void setSizes(List<ProductSizeEntity> sizes) {
        this.sizes = sizes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}
