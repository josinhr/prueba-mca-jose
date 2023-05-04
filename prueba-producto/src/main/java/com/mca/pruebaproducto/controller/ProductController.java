package com.mca.pruebaproducto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mca.pruebaproducto.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    /**
     * Service that manages products.
     */
    @Autowired
    private ProductService productService;

    /**
     * Call that get aviable products Ids ordere by their sequence number.
     * 
     * @return a list of aviable product Ids
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Integer> getProducts() {
        return productService.getOrderedAviableProducts();
    }
}
