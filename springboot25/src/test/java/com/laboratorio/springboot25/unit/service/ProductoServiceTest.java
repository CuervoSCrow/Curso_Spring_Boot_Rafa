package com.laboratorio.springboot25.unit.service;

import com.laboratorio.springboot25.dto.CategoriaResponse;
import com.laboratorio.springboot25.dto.ProductoRequest;
import com.laboratorio.springboot25.dto.ProductoResponse;
import com.laboratorio.springboot25.exception.InvalidOperationException;
import com.laboratorio.springboot25.exception.ResourceNotFoundException;
import com.laboratorio.springboot25.model.Categoria;
import com.laboratorio.springboot25.model.Producto;
import com.laboratorio.springboot25.repository.ProductoRepository;
import com.laboratorio.springboot25.service.CategoriaService;
import com.laboratorio.springboot25.service.ProductoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.InvalidAlgorithmParameterException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension .class)
public class ProductoServiceTest {
    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private ProductoServiceImpl productoService;

    @Test
    void testFindProductoById_ProductoExistente() {
        ProductoResponse response = new ProductoResponse(
                1,
                1,
                "Mouse",
                10.0,
                LocalDate.of(2026,1,1)
        );
        when(productoRepository.findProductoById(1))
                .thenReturn(Optional.of(response));
        Optional<ProductoResponse> producto = productoService.findProductoById(1);
        assertTrue(producto.isPresent());
        assertEquals(response, producto.get());
    }
    @Test
    void testFindProductoById_ProductoNotFound(){
        when(productoRepository.findProductoById(1))
                .thenReturn(Optional.empty());
        Optional<ProductoResponse> producto = productoService.findProductoById(1);
        assertTrue(producto.isEmpty());
        verify(productoRepository).findProductoById(1);
    }
    @Test
    void testFindProductoByNombre_ProductoExists(){
        ProductoResponse response = new ProductoResponse(
                1,
                1,
                "Mouse",
                10.0,
                LocalDate.of(2026,1,1));

        when(productoRepository.findProductoByNombre("Mouse"))
                .thenReturn(Optional.of(response));

        Optional<ProductoResponse> producto =
                productoService.findProductoByNombre("Mouse");

        assertTrue(producto.isPresent());
        assertEquals(1, producto.get().getCodigo());
        verify(this.productoRepository).findProductoByNombre("Mouse");
    }
    @Test
    void testFindAllOrderByNombreAsc() {
        List<ProductoResponse> productosDB = new ArrayList<>(
                List.of(
                        new ProductoResponse(1, 1,
                                "Mouse", 10.00, LocalDate.now()),
                        new ProductoResponse(2, 1,
                                "Teclado", 15.00, LocalDate.now()),
                        new ProductoResponse(3, 1,
                                "Disco Externo", 80.00, LocalDate.now())
                ));
        when(productoRepository.findAllOrderByNombreAsc())
                .thenReturn(productosDB);
        List<ProductoResponse> productos =
                productoService.findAllOrderByNombreAsc();
        assertEquals(productosDB, productos);
        assertEquals(3,productos.size());
        verify(this.productoRepository).findAllOrderByNombreAsc();
    }
    @Test
    void testFindByNombreContainingIgnoreCaseOrderByNombreAsc() {
        String infix = "CLA";
        List<ProductoResponse> productosDB = List.of(
                new ProductoResponse(2,1,
                        "Teclado",15.0,LocalDate.now())
        );
        when(this.productoRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix))
                .thenReturn(productosDB);

        List<ProductoResponse> productos =
                productoService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);

        assertFalse(productos.isEmpty());
        assertEquals(1, productos.size());
        verify(this.productoRepository).findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
    }
    @Test
    void testFindCategoriaIdOrderByNombreAsc(){
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
                productoService.findCategoriaIdOrderByNombreAsc(anyInt());
        assertEquals(productosDB, productos);
        assertEquals(3,productos.size());
        verify(this.productoRepository).findByCategoriaIdOrderByNombreAsc(anyInt());
    }
//    ------------------CRUD TESTS------------------
    @Test
    void testCreateProducto_ProductoCreated() {
        ProductoRequest request = new ProductoRequest(1,"Mouse",10.0);
        CategoriaResponse categoriaDB = new CategoriaResponse(1,"Periferico");
        Producto productoDB=new Producto(1,
                1,
                "Mouse",
                10.0,LocalDate.now(),
                new Categoria(1,"Periferico"));

        when(this.productoRepository.findProductoByNombre("Mouse"))
                .thenReturn(Optional.empty());
        when(this.categoriaService.findCategoriaById(request.getCategoriaId()))
                .thenReturn(Optional.of(categoriaDB));
        when(this.productoRepository.save(any(Producto.class)))
                .thenReturn(productoDB);

        ProductoResponse producto = productoService.createProducto(request);

        assertNotNull(producto);
        assertEquals(1,producto.getCodigo());
        assertEquals("Mouse",producto.getNombre());
        verify(this.productoRepository).findProductoByNombre("Mouse");
        verify(this.categoriaService).findCategoriaById(anyInt());
        verify(this.productoRepository).save(any(Producto.class));
    }
    @Test
    void testCreateProducto_ProductoExists() {
       ProductoRequest request = new ProductoRequest(1,"Mouse",10.0);
       ProductoResponse response = new ProductoResponse(
               1,
               1,
               "Mouse",
               10.0,
               LocalDate.now());
       when(this.productoRepository.findProductoByNombre(request.getNombre()))
               .thenReturn(Optional.of(response));
       ProductoResponse producto = productoService.createProducto(request);
       assertNotNull(producto);
       assertEquals(1,producto.getCodigo());
       assertEquals("Mouse",producto.getNombre());
       verify(this.productoRepository).findProductoByNombre(anyString());
       verify(this.categoriaService, never()).findCategoriaById(anyInt());
       verify(this.productoRepository, never()).save(any(Producto.class));
    }
    @Test
    void testCreateProducto_CategoriaNotFound() {
        ProductoRequest request = new ProductoRequest(1,"Mouse",10.0);

        when(this.productoRepository.findProductoByNombre(request.getNombre()))
                .thenReturn(Optional.empty());
        when(this.categoriaService.findCategoriaById(request.getCategoriaId()))
                .thenReturn(Optional.empty());
        assertThrows(
                ResourceNotFoundException.class,
                ()->{productoService.createProducto(request);});
    }
    @Test
    void testUpdateProducto_ProductoUpdated() {
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

        ProductoResponse producto = productoService.updateProducto(1, request);

        assertNotNull(producto);
        assertEquals(1,producto.getCodigo());
        assertEquals("Mouse",producto.getNombre());
        verify(this.productoRepository).findProductoById(1);
        verify(this.productoRepository).findProductoByNombre(anyString());
        verify(this.categoriaService).findCategoriaById(request.getCategoriaId());
        verify(this.productoRepository).save(any(Producto.class));
    }
    @Test
    void testUpdateProducto_ProductoNoFound() {
        ProductoRequest request = new ProductoRequest(
                1,"Mouse",10.0);
        when(this.productoRepository.findProductoById(anyInt()))
                .thenReturn(Optional.empty());
        ResourceNotFoundException exception =
            assertThrows(
                ResourceNotFoundException.class,
                ()->{productoService.updateProducto(1, request);});
        assertEquals("No se puede efectuar la modificacion, " +
                "el producto no existe", exception.getMessage());
        verify(this.productoRepository).findProductoById(1);
        verify(this.productoRepository, never()).findProductoByNombre(anyString());
        verify(this.categoriaService, never()).findCategoriaById(anyInt());
        verify(this.productoRepository, never()).save(any(Producto.class));
    }
    @Test
    void testUpdateProducto_ProductoDuplicatedName() {
        ProductoRequest request = new ProductoRequest(
                1,"Mouse",10.0);
        ProductoResponse productoDB = new ProductoResponse(
                1,1,"Mouse",10.0,LocalDate.now());
        Optional<ProductoResponse> otroProducto =
                Optional.of(new ProductoResponse(
                        2,1,"Generic Mouse",10.0,LocalDate.now()));

        when(this.productoRepository.findProductoById(1))
                .thenReturn(Optional.of(productoDB));
        when(this.productoRepository.findProductoByNombre(anyString()))
                .thenReturn(otroProducto);

        assertThrows(InvalidOperationException.class,
                ()->{productoService.updateProducto(1,request);});

        verify(this.productoRepository).findProductoById(1);
        verify(this.productoRepository).findProductoByNombre(anyString());
        verify(this.categoriaService, never()).findCategoriaById(anyInt());
        verify(this.productoRepository, never()).save(any(Producto.class));
    }
    @Test
    void testUpdateProducto_CategoriaNoExists() {
        ProductoRequest request=new ProductoRequest(1,"Mouse",10.0);
        ProductoResponse response = new ProductoResponse(
                1,1,"Mouse",10.0,LocalDate.now());

        when(this.productoRepository.findProductoById(1))
                .thenReturn(Optional.of(response));
        when(this.productoRepository.findProductoByNombre(request.getNombre()))
                .thenReturn(Optional.of(response));
        when(this.categoriaService.findCategoriaById(request.getCategoriaId()))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                ()->{this.productoService.updateProducto(1,request);});

        verify(this.productoRepository).findProductoById(1);
        verify(this.productoRepository).findProductoByNombre("Mouse");
        verify(this.categoriaService).findCategoriaById(1);
        verify(this.productoRepository, never()).save(any(Producto.class));

    }


}
