package com.mca.pruebaproducto.service;

import java.io.IOException;
import java.util.List;

import com.mca.pruebaproducto.entity.ProductEntity;

public interface ProductService {

    public abstract List<Integer> getOrderedAviableProcducts() throws IOException;

    public abstract List<Integer> filterNonAviableAndOrderProducts(List<ProductEntity> products);
}
