package com.laboratorio.springboot23.integration.service;

import com.laboratorio.springboot23.dto.ProductoRequest;
import com.laboratorio.springboot23.dto.ProductoResponse;
import com.laboratorio.springboot23.exception.InvalidOperationException;
import com.laboratorio.springboot23.exception.ResourceNotFoundException;
import com.laboratorio.springboot23.service.ProductoService;
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
    void testFindProductoById_ProductoExists() {
        Integer productoId=4;
        Optional<ProductoResponse> producto = productoService.findProductoById(productoId);
        assertTrue(producto.isPresent());
        assertEquals("Producto 4",producto.get().getNombre());
    }
    @Test
    @Order(2)
    void testFindProductoById_ProductoNotFound(){
        Integer productoId=100;
        Optional<ProductoResponse> producto = productoService.findProductoById(productoId);

        assertTrue(producto.isEmpty());
    }
    @Test
    @Order(3)
    void testFindProductoByNombre_ProductoExists() {
        String nombre="Producto 2";
        Optional<ProductoResponse> producto = productoService.findProductoByNombre(nombre);

        assertTrue(producto.isPresent());
        assertEquals(2,producto.get().getCodigo());
    }
    @Test
    @Order(4)
    void testFindAllOrderByNombreAsc(){
        List<ProductoResponse> productos = productoService.findAllOrderByNombreAsc();
        assertEquals(9,productos.size());
    }
    @Test
    @Order(5)
    void testFindByNombreContainingIgnoreCaseOrderByNombreAsc(){
        String infix = "DucTo 1";
        List<ProductoResponse> productos = productoService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        assertEquals(1,productos.size());
    }
    @Test
    @Order(6)
    void testFindByCategoriaIdOrderByNombreAsc(){
        Integer categoriaId=2;
        List<ProductoResponse> productos = productoService.findByCategoriaIdOrderByNombreAsc(categoriaId);
        assertEquals(3,productos.size());
    }
    //------------------<CREACION>------------------
    @Test
    @Order(7)
    void testCreateProducto_ProductoCreated(){
        ProductoRequest request = new ProductoRequest(
                1,"Producto 10",10.0);
        ProductoResponse producto =
                productoService.createProducto(request);
        createId = producto.getCodigo();
        System.out.println("Producto creado: " + producto.getCodigo());
        assertNotNull(producto);
        assertTrue(producto.getCodigo()>9);
    }
    @Test
    @Order(8)
    void testCreateProducto_ProductoExists(){
        ProductoRequest request = new ProductoRequest(
                1,"Producto 1",10.0);
        ProductoResponse producto =
                productoService.createProducto(request);
        assertNotNull(producto);
        assertEquals(1,producto.getCodigo());
    }
    @Test
    @Order(9)
    void testCreateProducto_CategoriaNotFound(){
        ProductoRequest request =
                new ProductoRequest(10,"Producto 11",11.0);
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> {productoService.createProducto(request);
        });
        assertEquals("No existe la categoria indicada," +
                "no se puede crear el producto",exception.getMessage());
    }
    //------------------<ACTUALIZACION>------------------
    @Test
    @Order(10)
    void testUpdateProducto_ProductoUpdated(){
        Integer productoId = createId;
        ProductoRequest request = new ProductoRequest(
                1,"Producto 10",10.25);
        ProductoResponse producto =
                productoService.updateProducto(productoId,request);
        assertNotNull(producto);
//        assertEquals(productoId,producto.getCodigo());
    }
    @Test
    @Order(11)
    void testUpdateProducto_ProductoNotFound(){
        Integer productoId = 14;
        ProductoRequest request = new ProductoRequest(
                3,"Producto 14",12.25);
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> {productoService.updateProducto(productoId,request);
        });
        assertEquals("No se puede efectuar la modificacion, " +
                "El producto no existe.",exception.getMessage());

    }
    @Test
    @Order(12)
    void testUpdateProducto_DuplicateName(){
        Integer productoId = 1;
        ProductoRequest request = new ProductoRequest(
                1,"Producto 2",10.25);
        InvalidOperationException exception = assertThrows(
                InvalidOperationException.class,
                ()->{this.productoService.updateProducto(productoId,request);}
        );
        assertEquals("No se puede modificar el producto por que " +
                "existe otro con el mismo nombre ",exception.getMessage());
    }
    @Test
    @Order(13)
    void testUpdateProducto_CategoryNotExists(){
        Integer productoId=createId;
        ProductoRequest request = new ProductoRequest(
                5,"Producto 10",10.25);
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                ()->{productoService.updateProducto(productoId,request);});
        assertEquals("No existe la categoria indicada," +
                "no se puede modificar el producto",exception.getMessage());

    }
    //------------------<ELIMINACION>------------------
    @Test
    @Order(14)
    void DeleteProducto_ProductoDeleted(){
        Integer productoId = createId;
        boolean deleted = productoService.deleteProducto(productoId);
        assertTrue(deleted);
    }
    @Test
    @Order(15)
    void DeleteProducto_ProductoNotFound(){
        Integer productoId = 14;
        boolean deleted = productoService.deleteProducto(productoId);
        assertFalse(deleted);
    }

}
