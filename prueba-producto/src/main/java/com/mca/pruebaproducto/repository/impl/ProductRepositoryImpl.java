package com.mca.pruebaproducto.repository.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import com.mca.pruebaproducto.entity.ProductEntity;
import com.mca.pruebaproducto.entity.ProductSizeEntity;
import com.mca.pruebaproducto.entity.SizeStockEntity;
import com.mca.pruebaproducto.repository.ProductRepository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    Logger logger = Logger.getLogger(ProductRepositoryImpl.class.getName());

    private static final String COMMA_DELIMITER = ", ";

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
        Map<Integer, List<ProductSizeEntity>> sizes = new HashMap<>();
        Map<Integer, SizeStockEntity> stocks = new HashMap<>();

        try (Scanner productScanner = new Scanner(
                new File(productURL));
                Scanner sizeScanner = new Scanner(
                        new File(sizeURL));
                Scanner stockScanner = new Scanner(
                        new File(stockURL))) {

            products = loadProducts(productScanner);

            sizes = loadSizes(sizeScanner);

            stocks = loadStocks(stockScanner);

        } catch (FileNotFoundException e) {
            logger.warning("Error: Alguno de los ficheros no ha sido encontrado");
            return new LinkedList<>();
        } catch (NumberFormatException e) {
            logger.warning("Error: Ha habido un problema en la lectura de los ficheros");
            return new LinkedList<>();
        }

        fillProductsWithSizesAndStocks(products, sizes, stocks);

        return products;
    }

    private void fillProductsWithSizesAndStocks(List<ProductEntity> products,
            Map<Integer, List<ProductSizeEntity>> sizes, Map<Integer, SizeStockEntity> stocks) {

        for (ProductEntity product : products) {
            List<ProductSizeEntity> productSizes = sizes.get(product.getId());
            for (ProductSizeEntity productSize : productSizes) {
                productSize.setStock(stocks.get(productSize.getId()));
            }
            product.getSizes().addAll(productSizes);

        }
    }

    private List<ProductEntity> loadProducts(Scanner productScanner) {
        List<ProductEntity> products = new LinkedList<>();

        while (productScanner.hasNextLine()) {
            List<String> productProperties = getRecordFromLine(productScanner.nextLine());
            ProductEntity product = new ProductEntity(Integer.parseInt(productProperties.get(0)),
                    Integer.parseInt(productProperties.get(1)));
            products.add(product);
        }

        return products;
    }

    private Map<Integer, List<ProductSizeEntity>> loadSizes(Scanner sizeScanner) {
        Map<Integer, List<ProductSizeEntity>> sizes = new HashMap<>();

        while (sizeScanner.hasNextLine()) {

            List<String> sizeProperties = getRecordFromLine(sizeScanner.nextLine());
            ProductSizeEntity size = new ProductSizeEntity(
                    Integer.parseInt(sizeProperties.get(0)), Integer.parseInt(sizeProperties.get(1)),
                    Boolean.parseBoolean(sizeProperties.get(2)), Boolean.parseBoolean(sizeProperties.get(3)));

            if (sizes.containsKey(size.getProductId())) {
                List<ProductSizeEntity> tmp = sizes.get(size.getProductId());
                tmp.add(size);
                sizes.put(size.getProductId(), tmp);
            } else
                sizes.put(size.getProductId(), new LinkedList<>(Arrays.asList(size)));
        }
        return sizes;
    }

    private Map<Integer, SizeStockEntity> loadStocks(Scanner stockScanner) {
        Map<Integer, SizeStockEntity> stocks = new HashMap<>();

        while (stockScanner.hasNextLine()) {
            List<String> stockProperties = getRecordFromLine(stockScanner.nextLine());
            SizeStockEntity stock = new SizeStockEntity(Integer.parseInt(stockProperties.get(0)),
                    Integer.parseInt(stockProperties.get(1)));
            stocks.put(stock.getSizeId(), stock);
        }
        return stocks;
    }

    private static List<String> getRecordFromLine(String line) {
        List<String> values = new LinkedList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

}
