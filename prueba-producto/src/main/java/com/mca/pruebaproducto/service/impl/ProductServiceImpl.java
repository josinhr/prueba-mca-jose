package com.mca.pruebaproducto.service.impl;

import java.io.IOException;
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

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Integer> getOrderedAviableProcducts() throws IOException {
        List<ProductEntity> products = productRepository.getAllProducts();
        List<Integer> aviableProducts = filterNonAviableAndOrderProducts(products);
        return aviableProducts;
    }

    @Override
    public List<Integer> filterNonAviableAndOrderProducts(List<ProductEntity> products) {

        if (products == null || products.isEmpty())
            return new LinkedList<>();

        List<ProductEntity> aviableProducts = new LinkedList<>();

        for (ProductEntity product : products) {
            if (isProductAviable(product))
                aviableProducts.add(product);
        }

        return orderAndListOfProductIds(aviableProducts);
    }

    private List<Integer> orderAndListOfProductIds(List<ProductEntity> aviableProducts) {
        Collections.sort(aviableProducts, (p1, p2) -> p1.getSequence().compareTo(p2.getSequence()));

        List<Integer> orderedFilteredProducts = aviableProducts.stream().map(p -> p.getId())
                .collect(Collectors.toList());
        return orderedFilteredProducts;
    }

    /**
     * Este método realmente no debería encontrarse en el servicio. Debería poder
     * moverse
     * a una clase de dominio ya que es una de las reglas de nuestro modelo de
     * negocio.
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
