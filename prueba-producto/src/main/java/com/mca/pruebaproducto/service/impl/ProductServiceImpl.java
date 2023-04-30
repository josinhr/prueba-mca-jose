package com.mca.pruebaproducto.service.impl;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mca.pruebaproducto.entity.ProductEntity;
import com.mca.pruebaproducto.repository.ProductRepository;
import com.mca.pruebaproducto.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Integer> getOrderedAviableProcducts() throws IOException {
        List<ProductEntity> products = productRepository.getAllProducts();
        List<Integer> aviableProducts = filterNonAviableAndOrderProducts(products);
        return aviableProducts;
    }

    private List<Integer> filterNonAviableAndOrderProducts(List<ProductEntity> products) {

        return new LinkedList<>();
    }

}
