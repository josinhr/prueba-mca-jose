package com.mca.pruebaproducto.service;

import java.util.List;

import com.mca.pruebaproducto.entity.ProductEntity;

public interface ProductService {

    /**
     * Function that get aviable products Ids ordered by their sequence number from
     * the repository.
     * 
     * @return aviable products Ids ordered by their sequence number.
     */
    public abstract List<Integer> getOrderedAviableProcducts();

    /**
     * Getting a list of products returns the aviable products Ids ordered by their
     * sequence number.
     * 
     * @param products
     * @return the aviable products Ids ordered by their sequence number.
     */
    public abstract List<Integer> filterNonAviableAndOrderProducts(List<ProductEntity> products);
}
