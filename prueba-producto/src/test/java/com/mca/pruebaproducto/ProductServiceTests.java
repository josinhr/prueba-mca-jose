package com.mca.pruebaproducto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import com.mca.pruebaproducto.entity.ProductEntity;
import com.mca.pruebaproducto.entity.ProductSizeEntity;
import com.mca.pruebaproducto.entity.SizeStockEntity;
import com.mca.pruebaproducto.repository.ProductRepository;
import com.mca.pruebaproducto.service.impl.ProductServiceImpl;

@SpringBootTest
public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @Description("Case when the list of products is null")
    void filterNonAviableAndOrderProductsProductsIsNull() throws IOException {
        when(productRepository.getAllProducts()).thenReturn(new LinkedList<>());
        List<Integer> productsIds = productService.getOrderedAviableProducts();
        assertTrue(productsIds != null && productsIds.isEmpty());
    }

    @Test
    @Description("Case when the list of products is empty")
    void filterNonAviableAndOrderProductsProductsIsEmpty() throws IOException {

        when(productRepository.getAllProducts()).thenReturn(new LinkedList<>());

        List<Integer> productsIds = productService.getOrderedAviableProducts();
        assertTrue(productsIds != null && productsIds.isEmpty());
    }

    @Test
    @Description("Case when products exists sizes can not be empty")
    void filterNonAviableAndOrderProductsSizeEmpty() throws IOException {

        ProductEntity a = new ProductEntity(0, 2);
        List<ProductEntity> products = new LinkedList<>();
        products.add(a);

        when(productRepository.getAllProducts()).thenReturn(products);

        List<Integer> productsIds = productService.getOrderedAviableProducts();
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

        when(productRepository.getAllProducts()).thenReturn(products);

        List<Integer> productsIds = productService.getOrderedAviableProducts();
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

        when(productRepository.getAllProducts()).thenReturn(products);

        List<Integer> productsIds = productService.getOrderedAviableProducts();
        assertTrue(productsIds.isEmpty());

    }

    @Test
    @Description("Case when products three product with stock")
    void filterNonAviableAndOrderProductsTwoStock() throws IOException {

        List<ProductEntity> products = new LinkedList<>();

        SizeStockEntity sa = new SizeStockEntity(0, 1);

        ProductSizeEntity pa = new ProductSizeEntity(0, 0, false, false);
        pa.setStock(sa);
        List<ProductSizeEntity> sizesa = new LinkedList<>();
        sizesa.add(pa);

        ProductEntity a = new ProductEntity(0, 5);
        a.setSizes(sizesa);
        products.add(a);

        SizeStockEntity sb = new SizeStockEntity(1, 1);

        ProductSizeEntity pb = new ProductSizeEntity(1, 1, false, false);
        pb.setStock(sb);
        List<ProductSizeEntity> sizesb = new LinkedList<>();
        sizesb.add(pb);

        ProductEntity b = new ProductEntity(1, 15);
        b.setSizes(sizesb);
        products.add(b);

        SizeStockEntity sc = new SizeStockEntity(1, 1);

        ProductSizeEntity pc = new ProductSizeEntity(1, 1, false, false);
        pc.setStock(sc);
        List<ProductSizeEntity> sizesc = new LinkedList<>();
        sizesc.add(pc);

        ProductEntity c = new ProductEntity(2, 10);
        c.setSizes(sizesc);
        products.add(c);

        when(productRepository.getAllProducts()).thenReturn(products);

        List<Integer> productsIds = productService.getOrderedAviableProducts();
        assertTrue(!productsIds.isEmpty());
        assertEquals(3, productsIds.size());
        assertEquals(0, productsIds.get(0));
        assertEquals(2, productsIds.get(1));
        assertEquals(1, productsIds.get(2));

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

        when(productRepository.getAllProducts()).thenReturn(products);

        List<Integer> productsIds = productService.getOrderedAviableProducts();
        assertTrue(!productsIds.isEmpty());
        assertEquals(1, productsIds.size());
        assertEquals(3, productsIds.get(0));

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

        when(productRepository.getAllProducts()).thenReturn(products);

        List<Integer> productsIds = productService.getOrderedAviableProducts();
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

        when(productRepository.getAllProducts()).thenReturn(products);

        List<Integer> productsIds = productService.getOrderedAviableProducts();

        assertTrue(!productsIds.isEmpty());
        assertEquals(1, productsIds.size());
        assertEquals(3, productsIds.get(0));

    }

}
