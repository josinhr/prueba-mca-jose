package com.mca.pruebaproducto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import com.mca.pruebaproducto.entity.ProductEntity;
import com.mca.pruebaproducto.entity.ProductSizeEntity;
import com.mca.pruebaproducto.entity.SizeStockEntity;
import com.mca.pruebaproducto.service.ProductService;

@SpringBootTest
public class ProductServiceTests {

    @Autowired
    private ProductService productService;

    @Test
    @Description("Case when the list of products is null")
    void filterNonAviableAndOrderProductsProductsIsNull() throws IOException {
        List<Integer> productsIds = productService.filterNonAviableAndOrderProducts(null);
        assertTrue(productsIds != null && productsIds.isEmpty());
    }

    @Test
    @Description("Case when the list of products is empty")
    void filterNonAviableAndOrderProductsProductsIsEmpty() throws IOException {
        List<Integer> productsIds = productService.filterNonAviableAndOrderProducts(new LinkedList<>());
        assertTrue(productsIds != null && productsIds.isEmpty());
        assertTrue(productsIds != null && productsIds.isEmpty());
    }

    @Test
    @Description("Case when products exists sizes can not be empty")
    void filterNonAviableAndOrderProductsSizeEmpty() throws IOException {

        ProductEntity a = new ProductEntity(0, 2);
        List<ProductEntity> products = new LinkedList<>();
        products.add(a);

        List<Integer> productsIds = productService.filterNonAviableAndOrderProducts(products);
        assertTrue(productsIds.isEmpty());

    }

    @Test
    @Description("Case when products exists sizes not empty but stock is null")
    void filterNonAviableAndOrderProductsNoStock() throws IOException {

        ProductSizeEntity pa = new ProductSizeEntity(0, 0, false, false);
        List<ProductSizeEntity> sizes = new LinkedList<>();
        sizes.add(pa);

        ProductEntity a = new ProductEntity(0, 2);
        a.setSizes(sizes);
        List<ProductEntity> products = new LinkedList<>();
        products.add(a);

        List<Integer> productsIds = productService.filterNonAviableAndOrderProducts(products);
        assertTrue(productsIds.isEmpty());

    }

    @Test
    @Description("Case when products exists sizes not empty but stock is 0")
    void filterNonAviableAndOrderProductsStockZero() throws IOException {

        SizeStockEntity stock = new SizeStockEntity(0, 0);

        ProductSizeEntity pa = new ProductSizeEntity(0, 0, false, false);
        pa.setStock(stock);
        List<ProductSizeEntity> sizes = new LinkedList<>();
        sizes.add(pa);

        ProductEntity a = new ProductEntity(0, 2);
        a.setSizes(sizes);
        List<ProductEntity> products = new LinkedList<>();
        products.add(a);

        List<Integer> productsIds = productService.filterNonAviableAndOrderProducts(products);
        assertTrue(productsIds.isEmpty());

    }

    @Test
    @Description("Case when products one product with stock")
    void filterNonAviableAndOrderProductsOneStock() throws IOException {

        List<ProductEntity> products = new LinkedList<>();

        SizeStockEntity sa = new SizeStockEntity(0, 1);

        ProductSizeEntity pa = new ProductSizeEntity(0, 0, false, false);
        pa.setStock(sa);
        List<ProductSizeEntity> sizesa = new LinkedList<>();
        sizesa.add(pa);

        ProductEntity a = new ProductEntity(0, 2);
        a.setSizes(sizesa);
        products.add(a);

        SizeStockEntity sb = new SizeStockEntity(1, 0);

        ProductSizeEntity pb = new ProductSizeEntity(1, 1, false, false);
        pb.setStock(sb);
        List<ProductSizeEntity> sizesb = new LinkedList<>();
        sizesb.add(pb);

        ProductEntity b = new ProductEntity(1, 2);
        b.setSizes(sizesb);
        products.add(b);

        List<Integer> productsIds = productService.filterNonAviableAndOrderProducts(products);
        assertTrue(!productsIds.isEmpty());
        assertTrue(productsIds.size() == 1);
        assertEquals(productsIds.get(0), 0);

    }

    @Test
    @Description("Case when products two product with stock")
    void filterNonAviableAndOrderProductsTwoStock() throws IOException {

        List<ProductEntity> products = new LinkedList<>();

        SizeStockEntity sa = new SizeStockEntity(0, 1);

        ProductSizeEntity pa = new ProductSizeEntity(0, 0, false, false);
        pa.setStock(sa);
        List<ProductSizeEntity> sizesa = new LinkedList<>();
        sizesa.add(pa);

        ProductEntity a = new ProductEntity(0, 2);
        a.setSizes(sizesa);
        products.add(a);

        SizeStockEntity sb = new SizeStockEntity(1, 1);

        ProductSizeEntity pb = new ProductSizeEntity(1, 1, false, false);
        pb.setStock(sb);
        List<ProductSizeEntity> sizesb = new LinkedList<>();
        sizesb.add(pb);

        ProductEntity b = new ProductEntity(1, 1);
        b.setSizes(sizesb);
        products.add(b);

        List<Integer> productsIds = productService.filterNonAviableAndOrderProducts(products);
        assertTrue(!productsIds.isEmpty());
        assertTrue(productsIds.size() == 2);
        assertEquals(productsIds.get(0), 1);
        assertEquals(productsIds.get(1), 0);

    }

    @Test
    @Description("Case when products no stock but backsoon")
    void filterNonAviableAndOrderProductsNoStockButBacksoon() throws IOException {

        SizeStockEntity stock = new SizeStockEntity(0, 0);

        ProductSizeEntity pa = new ProductSizeEntity(0, 3, false, false);
        pa.setStock(stock);
        ProductSizeEntity pb = new ProductSizeEntity(1, 3, true, false);
        pb.setStock(stock);
        List<ProductSizeEntity> sizes = new LinkedList<>();
        sizes.add(pa);
        sizes.add(pb);

        ProductEntity a = new ProductEntity(3, 2);
        a.setSizes(sizes);
        List<ProductEntity> products = new LinkedList<>();
        products.add(a);

        List<Integer> productsIds = productService.filterNonAviableAndOrderProducts(products);
        assertTrue(!productsIds.isEmpty());
        assertTrue(productsIds.size() == 1);
        assertEquals(productsIds.get(0), 3);

    }

    @Test
    @Description("Case when products special no stock no backsoon")
    void filterNonAviableAndOrderProductsSpecialNoStockNoBacksoon() throws IOException {

        SizeStockEntity stocka = new SizeStockEntity(0, 1);
        SizeStockEntity stockb = new SizeStockEntity(1, 0);

        ProductSizeEntity pa = new ProductSizeEntity(0, 3, false, false);
        pa.setStock(stocka);
        ProductSizeEntity pb = new ProductSizeEntity(1, 3, false, true);
        pb.setStock(stockb);
        List<ProductSizeEntity> sizes = new LinkedList<>();
        sizes.add(pa);
        sizes.add(pb);

        ProductEntity a = new ProductEntity(3, 2);
        a.setSizes(sizes);
        List<ProductEntity> products = new LinkedList<>();
        products.add(a);

        List<Integer> productsIds = productService.filterNonAviableAndOrderProducts(products);
        assertTrue(productsIds.isEmpty());

    }

    @Test
    @Description("Case when products special no stock but backsoon")
    void filterNonAviableAndOrderProductsSpecialNoStockBacksoon() throws IOException {

        SizeStockEntity stocka = new SizeStockEntity(0, 0);
        SizeStockEntity stockb = new SizeStockEntity(1, 1);

        ProductSizeEntity pa = new ProductSizeEntity(0, 3, true, false);
        pa.setStock(stocka);
        ProductSizeEntity pb = new ProductSizeEntity(1, 3, false, true);
        pb.setStock(stockb);
        List<ProductSizeEntity> sizes = new LinkedList<>();
        sizes.add(pa);
        sizes.add(pb);

        ProductEntity a = new ProductEntity(3, 2);
        a.setSizes(sizes);
        List<ProductEntity> products = new LinkedList<>();
        products.add(a);

        List<Integer> productsIds = productService.filterNonAviableAndOrderProducts(products);

        assertTrue(!productsIds.isEmpty());
        assertTrue(productsIds.size() == 1);
        assertEquals(productsIds.get(0), 3);

    }

}
