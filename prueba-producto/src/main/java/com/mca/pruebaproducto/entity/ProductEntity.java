package com.mca.pruebaproducto.entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "sequence" })
public class ProductEntity implements Serializable {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("sequence")
    private Integer sequence;

    private List<ProductSizeEntity> sizes = new LinkedList<>();

    public ProductEntity() {
        super();
    }

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
