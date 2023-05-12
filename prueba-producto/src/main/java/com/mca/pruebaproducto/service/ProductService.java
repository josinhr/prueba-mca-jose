package com.mca.pruebaproducto.service;

import java.util.List;

public interface ProductService {

    /**
     * Function that get aviable products Ids ordered by their sequence number from
     * the repository.
     * 
     * @return aviable products Ids ordered by their sequence number.
     */
    public abstract List<Integer> getOrderedAviableProducts();

}
