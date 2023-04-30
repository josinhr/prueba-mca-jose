package com.mca.pruebaproducto.repository;

import java.util.List;

import com.mca.pruebaproducto.entity.ProductEntity;

public interface ProductRepository {

    List<ProductEntity> getAllProducts();

}
