package com.mca.pruebaproducto.repository;

import java.util.List;

import com.mca.pruebaproducto.entity.ProductEntity;

public interface ProductRepository {

    /**
     * Returns a list of all products containing their sizes and stocks
     * 
     * @return list of all products containing their sizes and stocks
     */
    List<ProductEntity> getAllProducts();

}
