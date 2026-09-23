package com.laboratorio.springboot24.Integration.service;

import com.laboratorio.springboot24.dto.ProductoRequest;
import com.laboratorio.springboot24.dto.ProductoResponse;
import com.laboratorio.springboot24.exception.InvalidOperationException;
import com.laboratorio.springboot24.exception.ResourceNotFoundException;
import com.laboratorio.springboot24.service.ProductoService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductoServiceTest {
    @Autowired
    private ProductoService productoService;

    private static Integer createId;

    @Test
    @Order(1)
    void findProductoById_ProductExistTest() {
        Integer productoId = 4;
        Optional<ProductoResponse> producto = this.productoService.findProductoById(productoId);
        assertTrue(producto.isPresent());
        assertEquals(productoId, producto.get().getCodigo());
    }
    @Test
    @Order(2)
    void findProductoById_ProductNotFoundTest() {
        Integer productoId = 100;
        Optional<ProductoResponse> producto = this.productoService.findProductoById(productoId);
        assertTrue(producto.isEmpty());
    }
    @Test
    @Order(3)
    void findProductoByNombre_ProductoExistxTest(){
        String nombre = "Producto 2";
        Optional<ProductoResponse> producto = this.productoService.findProductoByName(nombre);

        assertTrue(producto.isPresent());
        assertEquals(nombre, producto.get().getNombre());
        assertEquals(2, producto.get().getCodigo());
    }
    @Test
    @Order(4)
    void findAllOrderByNombreAscTest() {
        List<ProductoResponse> productos = this.productoService.findAllOrderByNombreAsc();
        assertEquals(9,productos.size());
    }
    @Test
    @Order(5)
    void findByNombreContainingIngnoreCaseOrderByNombreAscTest() {
        String infix="DucTo 1";
        List<ProductoResponse> productos =
                this.productoService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        assertEquals(1,productos.size());
    }
    @Test
    @Order(6)
    void findByCategoriaIdOrderByNombreAsc(){
        Integer categoriaId = 2;
        List<ProductoResponse> productos =
                this.productoService.findByCategoriaIdOrderByNombreAsc(categoriaId);
        assertEquals(3,productos.size());
    }
    @Test
    @Order(7)
    void createProducto_ProductoCreatedTest(){
        ProductoRequest request = new ProductoRequest(
                1,"Producto 10",10.0);
        ProductoResponse producto =
                productoService.createProduct(request);
        createId = producto.getCodigo();
        assertNotNull(producto);
        assertTrue(producto.getCodigo()>9);
    }
    @Test
    @Order(8)
    void createProducto_ProductoExistsTest(){
        ProductoRequest request= new ProductoRequest(
                1,"Producto 1",10.0);
        ProductoResponse producto =
                productoService.createProduct(request);
        assertNotNull(producto);
        assertEquals(1,producto.getCodigo());
    }
    @Test
    @Order(9)
    void createProducto_CategoriaNotFoundTest(){
        ProductoRequest request =
            new ProductoRequest(10,"Producto 11",11.0);
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                ()->{productoService.createProduct(request);});
        assertEquals("No existe la categoria indicada, " +
                "no se puede crear el producto",exception.getMessage());
    }
    @Test
    @Order(10)
    void UpdateProducto_ProductoUpdatedTest(){
        Integer productoId = createId;
    ProductoRequest request = new ProductoRequest(
            1,"Producto 10",10.25);
    ProductoResponse producto =
            productoService.updateProduct(productoId,request);
    assertNotNull(producto);
    assertEquals(10.25,producto.getPrecio());
    assertEquals("Producto 10",producto.getNombre());
    }
    @Test
    @Order(11)
    void updateProducto_ProductoNotFoundTest(){
        Integer productoId=14;
        ProductoRequest request = new ProductoRequest(
                3,"Producto 14",12.25);
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                ()->{productoService.updateProduct(productoId,request);});
        assertEquals("No se puede efectuar la modificacion, " +
                "el producto no existe",exception.getMessage());
    }
    @Test
    @Order(12)
    void updateProducto_DuplicatedNameTest(){
        Integer productoId=1;
        ProductoRequest request = new ProductoRequest(
                1,"Producto 2",10.25);
        InvalidOperationException exception = assertThrows(
                InvalidOperationException.class,
                ()->{this.productoService.updateProduct(productoId,request);});
        assertEquals("No se puede modificar el producto, " +
                "existe otro con el mismo nombre",exception.getMessage());
    }
    @Test
    @Order(13)
    void updateProducto_CategoryNotExistsTest(){
        Integer productoId = createId;
        ProductoRequest request = new ProductoRequest(
                5,"Producto 10",10.25);
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                ()->{productoService.updateProduct(productoId,request);});
        assertEquals("No existe la categoria indicada, " +
                "no se puede modificar el producto",exception.getMessage());
    }
    @Test
    @Order(14)
    void deleteProduct_ProductoDeletedTest(){
        Integer productoId = createId;
        boolean deleted = this.productoService.deleteProduct(productoId);
        assertTrue(deleted);
    }
    @Test
    @Order(15)
    void deleteProduct_ProductoNotFoundTest(){
        Integer productoId = 14;
        boolean deleted = this.productoService.deleteProduct(productoId);
        assertFalse(deleted);
    }

}
