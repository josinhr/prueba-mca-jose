package com.mca.pruebaproducto.service.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mca.pruebaproducto.entity.ProductEntity;
import com.mca.pruebaproducto.entity.ProductSizeEntity;
import com.mca.pruebaproducto.repository.ProductRepository;
import com.mca.pruebaproducto.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    /**
     * The repository of products.
     */
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Integer> getOrderedAviableProducts() {
        List<ProductEntity> products = productRepository.getAllProducts();
        return filterNonAviableAndOrderProducts(products);
    }

    /**
     * Getting a list of products returns the aviable products Ids ordered by their
     * sequence number.
     * 
     * @param products
     * @return the aviable products Ids ordered by their sequence number.
     */
    private List<Integer> filterNonAviableAndOrderProducts(List<ProductEntity> products) {

        if (products == null || products.isEmpty())
            return new LinkedList<>();

        List<ProductEntity> aviableProducts = filterAviableProducts(products);
        List<ProductEntity> orderedProducts = orderBySequenceListOfProducts(aviableProducts);

        return getListOfProductIds(orderedProducts);
    }

    /**
     * Se han extraido una serie de métodos que pretenden semantizar las funciones
     * "complejas" usadas en el algoritmo. Cuando la aplicación escalara estos
     * métodos
     * podrían ser llevados a otro lugar como una clase utils, al model de
     * productos, etc.
     */
    private List<ProductEntity> orderBySequenceListOfProducts(List<ProductEntity> aviableProducts) {
        List<ProductEntity> orderedProducts = new LinkedList<>(aviableProducts);
        Collections.sort(orderedProducts, (p1, p2) -> p1.getSequence().compareTo(p2.getSequence()));
        return orderedProducts;
    }

    private List<ProductEntity> filterAviableProducts(List<ProductEntity> products) {
        return products.stream().filter(this::isProductAviable).toList();
    }

    private List<Integer> getListOfProductIds(List<ProductEntity> aviableProducts) {
        return aviableProducts.stream().map(p -> p.getId())
                .collect(Collectors.toList());
    }

    /**
     * Este método realmente no debería encontrarse en el servicio. Debería poder
     * moverse
     * a una clase de dominio ya que es una de las reglas de nuestro modelo de
     * negocio.
     */
    /**
     * This method receives a product and returns if it's aviable according to
     * bussines rules.
     * 
     * @param product that we want to determine.
     * @return if the product is aviable or not.
     */
    private boolean isProductAviable(ProductEntity product) {
        if (product.getSizes().isEmpty())
            return false;

        boolean productHasStockInNormal = false;
        boolean productHasStockInSpecial = false;
        boolean productIsSpecial = false;

        for (ProductSizeEntity size : product.getSizes()) {
            productIsSpecial = productIsSpecial || size.isSpecial();

            productHasStockInNormal = productHasStockInNormal
                    || (!size.isSpecial() && size.getStock() != null
                            && (size.getStock().getQuantity() > 0 || size.isBackSoon()));
            productHasStockInSpecial = productHasStockInSpecial
                    || (size.isSpecial() && size.getStock() != null
                            && (size.getStock().getQuantity() > 0 || size.isBackSoon()));

        }
        return productIsSpecial ? productHasStockInNormal && productHasStockInSpecial : productHasStockInNormal;

    }

}
