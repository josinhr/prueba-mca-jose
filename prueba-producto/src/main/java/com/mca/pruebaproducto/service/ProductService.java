package com.mca.pruebaproducto.service;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    public abstract List<Integer> getOrderedAviableProcducts() throws IOException;

}
