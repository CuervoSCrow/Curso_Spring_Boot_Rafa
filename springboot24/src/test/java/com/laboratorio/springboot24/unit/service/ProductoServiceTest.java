package com.laboratorio.springboot24.unit.service;

import com.laboratorio.springboot24.dto.CategoriaResponse;
import com.laboratorio.springboot24.dto.ProductoRequest;
import com.laboratorio.springboot24.dto.ProductoResponse;
import com.laboratorio.springboot24.exception.InvalidOperationException;
import com.laboratorio.springboot24.exception.ResourceNotFoundException;
import com.laboratorio.springboot24.model.Categoria;
import com.laboratorio.springboot24.model.Producto;
import com.laboratorio.springboot24.repository.ProductoRepository;
import com.laboratorio.springboot24.service.CategoriaService;
import com.laboratorio.springboot24.service.ProductoServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {
    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private ProductoServiceImpl productoService;

    @Test
    void testFindProductoById_ProductExistsTest() {
        ProductoResponse productoDB = new ProductoResponse(
                1,
                1,
                "Mouse",
                10.0,
                LocalDate.of(2026,1,1)
        );
        when(productoRepository.findProductoById(1))
                .thenReturn(Optional.of(productoDB));

        Optional<ProductoResponse> producto = this.productoService.findProductoById(1);
        assertTrue(producto.isPresent());
        assertEquals(productoDB,producto.get());
    }
    @Test
    void findProductoById_ProductNotFoundTest(){
        when(productoRepository.findProductoById(1))
                .thenReturn(Optional.empty());

        Optional<ProductoResponse> producto = this.productoService.findProductoById(1);
        assertTrue(producto.isEmpty());
        verify(this.productoRepository).findProductoById(anyInt());
    }
    @Test
    void findProductoByNombre_ProductExistsTest(){
        ProductoResponse productoDB = new ProductoResponse(
                1,
                1,
                "Mouse",
                10.0,
                LocalDate.of(2026,1,1)
        );
        when(this.productoRepository.findProductoByNombre("Mouse"))
                .thenReturn(Optional.of(productoDB));

        Optional<ProductoResponse> producto =
                this.productoService.findProductoByName("Mouse");

        assertTrue(producto.isPresent());
        assertEquals(1,producto.get().getCodigo());
        verify(this.productoRepository).findProductoByNombre("Mouse");
    }
    @Test
    void findAllOrderByNombreAscTest() {
        List<ProductoResponse> productosDB = new ArrayList<>(
                List.of(
                        new ProductoResponse(1, 1,
                                "Mouse", 10.00, LocalDate.now()),
                        new ProductoResponse(2, 1,
                                "Teclado", 15.00, LocalDate.now()),
                        new ProductoResponse(3, 1,
                                "Disco Externo", 80.00, LocalDate.now())
                ));

        when(this.productoRepository.findAllOrderByNombreAsc())
                .thenReturn(productosDB);

        List<ProductoResponse> productos = this.productoService.findAllOrderByNombreAsc();

        assertEquals(productosDB, productos);
        assertEquals(3,productos.size());
        verify(this.productoRepository).findAllOrderByNombreAsc();
    }
    @Test
    void findByNombreContainingIgnoreCaseOrderByNombreAscTest() {
        List<ProductoResponse> productosDB = List.of(
                new ProductoResponse(2,1,
                        "Teclado",15.0,LocalDate.now())
        );
        when(this.productoRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc("CLA"))
                .thenReturn(productosDB);

        List<ProductoResponse> productos =
                this.productoService.findByNombreContainingIgnoreCaseOrderByNombreAsc("CLA");

        assertFalse(productos.isEmpty());
        assertEquals(1,productos.size());
        verify(this.productoRepository)
                .findByNombreContainingIgnoreCaseOrderByNombreAsc("CLA");
    }
    @Test
    void findByCategoriaIdOrderByNombreAscTest(){
        List<ProductoResponse> productosDB = List.of(
                new ProductoResponse(
                        1,1,"Mouse",10.0,LocalDate.now()),
                new ProductoResponse(
                        2,1,"Teclado",15.0,LocalDate.now()),
                new ProductoResponse(
                        3,1,"Disco Externo",80.0,LocalDate.now())
        );
        when(this.productoRepository.findByCategoriaIdOrderByNombreAsc(anyInt()))
                .thenReturn(productosDB);

        List<ProductoResponse> productos =
                this.productoService.findByCategoriaIdOrderByNombreAsc(anyInt());

        assertEquals(productosDB,productos);
        assertEquals(3,productos.size());
        verify(this.productoRepository)
                .findByCategoriaIdOrderByNombreAsc(anyInt());
    }
    @Test
    void createProductTest_ProductCreated() {
        ProductoRequest request = new ProductoRequest(1,"Mouse",10.0);
        CategoriaResponse categoriaDB = new CategoriaResponse(1,"Periferico");
        Producto productoDB=new Producto(1,
                1,
                "Mouse",
                10.0,LocalDate.now(),
                new Categoria(1,"Periferico"));
        when(this.productoRepository.findProductoByNombre(request.getNombre()))
                .thenReturn(Optional.empty());
        when(this.categoriaService.findCategoriaById(request.getCategoriaId()))
                .thenReturn(Optional.of(categoriaDB));
        when(this.productoRepository.save(any(Producto.class)))
                .thenReturn(productoDB);

        ProductoResponse producto = this.productoService.createProduct(request);

        assertNotNull(producto);
        assertEquals(1,producto.getCodigo());
        assertEquals("Mouse",producto.getNombre());
        verify(this.productoRepository).findProductoByNombre(anyString());
        verify(this.categoriaService).findCategoriaById(anyInt());
        verify(this.productoRepository).save(any(Producto.class));
    }
    @Test
    void createProductTest_ProductExists() {
        ProductoRequest request = new ProductoRequest(1,"Mouse",10.0);
        ProductoResponse response = new ProductoResponse(
                1,
                1,
                "Mouse",
                10.0,
                LocalDate.now());
        when(this.productoRepository.findProductoByNombre(request.getNombre()))
                .thenReturn(Optional.of(response));
        ProductoResponse producto = this.productoService.createProduct(request);

        assertNotNull(producto);
        assertEquals(1,producto.getCodigo());
        assertEquals("Mouse",producto.getNombre());
        verify(this.productoRepository).findProductoByNombre(anyString());
        verify(this.categoriaService,never()).findCategoriaById(1);
        verify(this.productoRepository,never()).save(any(Producto.class));
    }
    @Test
    void createProductTest_CategoryNotFound() {
        ProductoRequest request = new ProductoRequest(1, "Mouse", 10.0);

        when(this.productoRepository.findProductoByNombre(request.getNombre()))
                .thenReturn(Optional.empty());
        when(this.categoriaService.findCategoriaById(request.getCategoriaId()))
                .thenReturn(Optional.empty());
        assertThrows(
                ResourceNotFoundException.class,
                () -> this.productoService.createProduct(request)
        );
    }
    @Test
    void updateProductTest_ProductUpdated() {
        ProductoRequest request = new ProductoRequest(1,"Mouse",10.0);
        ProductoResponse productoDB = new ProductoResponse(
                1,1,"Mouse",10.0,LocalDate.now());
        CategoriaResponse categoriaDB = new CategoriaResponse(1,"Periferico");
        Producto productoModificado = new Producto(
                1,1,"Mouse",10.0,LocalDate.now(),
                new Categoria(1,"Periferico"));

        when(this.productoRepository.findProductoById(1))
                .thenReturn(Optional.of(productoDB));
        when(this.productoRepository.findProductoByNombre(anyString()))
                .thenReturn(Optional.empty());
        when(this.categoriaService.findCategoriaById(request.getCategoriaId()))
                .thenReturn(Optional.of(categoriaDB));
        when(this.productoRepository.save(any(Producto.class)))
                .thenReturn(productoModificado);

        ProductoResponse producto = this.productoService.updateProduct(1,request);

        assertNotNull(producto);
        assertEquals(1,producto.getCodigo());
        assertEquals("Mouse",producto.getNombre());
        verify(this.productoRepository).findProductoById(1);
        verify(this.productoRepository).findProductoByNombre("Mouse");
        verify(this.categoriaService).findCategoriaById(1);
        verify(this.productoRepository).save(any(Producto.class));

    }
    @Test
    void updateProductTest_ProductNotFound() {
        ProductoRequest request = new ProductoRequest(
                1,"Mouse",10.0);
        when(this.productoRepository.findProductoById(anyInt()))
                .thenReturn(Optional.empty());
        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> this.productoService.updateProduct(1,request)
                );
        assertEquals("No se puede efectuar la modificacion, " +
                "el producto no existe", exception.getMessage());
        verify(this.productoRepository).findProductoById(1);
        verify(this.productoRepository,never()).findProductoByNombre(anyString());
        verify(this.categoriaService,never()).findCategoriaById(anyInt());
        verify(this.productoRepository,never()).save(any(Producto.class));
    }
    @Test
    void updateProductTest_DuplicatedName() {
        ProductoRequest request = new ProductoRequest(1,"Mouse",10.0);
        ProductoResponse productoDB = new ProductoResponse(
                1,1,"Mouse",10.0,LocalDate.now());
        Optional<ProductoResponse> otroProducto =
                Optional.of(new ProductoResponse(
                        2,1,"Generic Mouse",10.0,LocalDate.now()));

        when(this.productoRepository.findProductoById(1))
                .thenReturn(Optional.of(productoDB));
        when(this.productoRepository.findProductoByNombre(anyString()))
                .thenReturn(otroProducto);

        assertThrows(
                InvalidOperationException.class,
                () -> this.productoService.updateProduct(1,request)
        );
        verify(this.productoRepository).findProductoById(1);
        verify(this.productoRepository).findProductoByNombre("Mouse");
        verify(this.categoriaService,never()).findCategoriaById(anyInt());
        verify(this.productoRepository,never()).save(any(Producto.class));
    }
    @Test
    void updateProductTest_CategoryNotExists() {
        ProductoRequest request = new ProductoRequest(1,"Mouse",10.0);
        ProductoResponse productoDB = new ProductoResponse(
                1,1,"Mouse",10.0,LocalDate.now());

        when(this.productoRepository.findProductoById(1))
                .thenReturn(Optional.of(productoDB));
        when(this.productoRepository.findProductoByNombre(request.getNombre()))
                .thenReturn(Optional.of(productoDB));
        when(this.categoriaService.findCategoriaById(request.getCategoriaId()))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                ()->{this.productoService.updateProduct(1,request);});
        verify(this.productoRepository).findProductoById(1);
        verify(this.productoRepository).findProductoByNombre("Mouse");
        verify(this.categoriaService).findCategoriaById(1);
        verify(this.productoRepository,never()).save(any(Producto.class));

    }
    @Test
    void deleteProductTest_ProductDeleted() {
        ProductoResponse productoDB = new ProductoResponse(
                1,1,"Mouse",10.0,LocalDate.now());
        when(this.productoRepository.findProductoById(1))
                .thenReturn(Optional.of(productoDB));
        boolean result = this.productoService.deleteProduct(1);

        assertTrue(result);
        verify(this.productoRepository).findProductoById(1);
        verify(this.productoRepository).deleteById(1);
    }
    @Test
    void deleteProductTest_ProductNotFound() {
        when(this.productoRepository.findProductoById(1))
                .thenReturn(Optional.empty());
        boolean result = this.productoService.deleteProduct(1);

        assertFalse(result);
        verify(this.productoRepository).findProductoById(1);
        verify(this.productoRepository,never()).deleteById(1);
    }


}
