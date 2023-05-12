package com.mca.pruebaproducto.repository.impl;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.mca.pruebaproducto.entity.ProductEntity;
import com.mca.pruebaproducto.entity.ProductSizeEntity;
import com.mca.pruebaproducto.entity.SizeStockEntity;
import com.mca.pruebaproducto.repository.ProductRepository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    Logger logger = Logger.getLogger(ProductRepositoryImpl.class.getName());

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public List<ProductEntity> getAllProducts() {
        List<ProductEntity> products = new LinkedList<>();

        Resource productResource = resourceLoader.getResource("classpath:/product.csv");
        Resource sizeResource = resourceLoader.getResource("classpath:/size.csv");
        Resource stockResource = resourceLoader.getResource("classpath:/stock.csv");

        String productURL;
        String sizeURL;
        String stockURL;
        try {
            productURL = productResource.getURL().getFile();
            sizeURL = sizeResource.getURL().getFile();
            stockURL = stockResource.getURL().getFile();
        } catch (IOException e) {
            logger.warning("Error: Alguno de los ficheros no ha sido encontrado");
            return new LinkedList<>();
        }

        if (productURL != null && sizeURL != null && stockURL != null) {
            products = loadEntitiesFromFiles(productURL, sizeURL, stockURL);
        }
        return products;
    }

    private List<ProductEntity> loadEntitiesFromFiles(String productURL, String sizeURL, String stockURL) {
        List<ProductEntity> products = new LinkedList<>();
        List<ProductSizeEntity> sizes = new LinkedList<>();
        List<SizeStockEntity> stocks = new LinkedList<>();

        try {

            products = loadEntitiesFromFile(new File(productURL), ProductEntity.class);
            sizes = loadEntitiesFromFile(new File(sizeURL), ProductSizeEntity.class);
            stocks = loadEntitiesFromFile(new File(stockURL), SizeStockEntity.class);

        } catch (NumberFormatException | IOException e) {
            logger.warning("Error: Ha habido un problema en la lectura de los ficheros");
            return new LinkedList<>();
        }

        fillProductsWithSizesAndStocks(products, sizes, stocks);

        return products;
    }

    private void fillProductsWithSizesAndStocks(List<ProductEntity> products,
            List<ProductSizeEntity> sizes, List<SizeStockEntity> stocks) {

        for (ProductEntity product : products) {
            List<ProductSizeEntity> productSizes = sizes.stream().filter(s -> s.getProductId().equals(product.getId()))
                    .toList();
            for (ProductSizeEntity productSize : productSizes) {

                Optional<SizeStockEntity> stock = stocks.stream().filter(s -> s.getSizeId().equals(productSize.getId()))
                        .findFirst();
                if (stock.isPresent())
                    productSize.setStock(stock.get());
            }
            product.getSizes().addAll(productSizes);

        }
    }

    private <T> List<T> loadEntitiesFromFile(File file, Class<T> entityClass) throws IOException {
        MappingIterator<T> iterator = new CsvMapper().readerWithTypedSchemaFor(entityClass).readValues(file);
        return iterator.readAll();
    }
}
