package com.example.springboot22.unit.service;

import com.example.springboot22.dto.CategoriaResponse;
import com.example.springboot22.dto.ProductoRequest;
import com.example.springboot22.dto.ProductoResponse;
import com.example.springboot22.model.Categoria;
import com.example.springboot22.model.Producto;
import com.example.springboot22.repository.CategoriaRepository;
import com.example.springboot22.repository.ProductoRepository;
import com.example.springboot22.service.CategoriaService;
import com.example.springboot22.service.ProductoService;
import com.example.springboot22.service.ProductoServiceImpl;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension .class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private ProductoServiceImpl productoService;

    @Test
    void testFindProductoById_ProductExists() {
        ProductoResponse productoResponse =
                new ProductoResponse(
                1,
                1,
                "Mouse",
                10.0,
                LocalDate.now()
        );
        when(productoRepository.findProductoById(1))
                .thenReturn(Optional.of(productoResponse));

        Optional<ProductoResponse> producto = this.productoRepository.findProductoById(1);

        assertTrue(producto.isPresent());
        assertEquals("Mouse", producto.get().getNombre());
    }

    @Test
    void findProductoById_ProductoNotFound(){
        when(this.productoRepository.findProductoById(1))
                .thenReturn(Optional.empty());

        Optional<ProductoResponse> producto = this.productoRepository.findProductoById(1);

        assertTrue(producto.isEmpty());
        verify(this.productoRepository).findProductoById(anyInt());
    }

    @Test
    void findProductoByNombre_ProductoExists(){
        ProductoResponse productoDB =
                new ProductoResponse(
                        1,
                        1,
                        "Mouse",
                        10.00,
                        LocalDate.now()
                );

        when(this.productoRepository.findProductoByNombre("Mouse"))
                .thenReturn(Optional.of(productoDB));

        Optional<ProductoResponse> producto =
                this.productoRepository.findProductoByNombre("Mouse");

        assertTrue(producto.isPresent());
        assertEquals(1,producto.get().getCodigo());
        verify(this.productoRepository).findProductoByNombre(anyString());
    }

    @Test
    void testfindAllOrderByNombreAsc(){
        List<ProductoResponse> productosDB = new ArrayList<>(
                List.of(
                        new ProductoResponse(1, 1,
                                "Mouse", 10.00, LocalDate.now()),
                        new ProductoResponse(2, 1,
                                "Teclado", 15.00, LocalDate.now()),
                        new ProductoResponse(3, 1,
                                "Disco Externo", 80.00, LocalDate.now())
                )
        );
        when(this.productoService.findAllOrderByNombreAsc())
                .thenReturn(productosDB);

        List<ProductoResponse> productos = this.productoRepository.findAllOrderByNombreAsc();

        assertFalse(productos.isEmpty());
        assertEquals(3, productos.size());
        verify(this.productoRepository).findAllOrderByNombreAsc();
    }

    @Test
    void testFindByNombreContainingIgnoreCaseOrderByNombreAsc(){
        List<ProductoResponse> productosDB = List.of(
                new ProductoResponse(2,1,
                        "Teclado",15.00,LocalDate.now())
        );

        when(this.productoRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc("Cla"))
                .thenReturn(productosDB);

        List<ProductoResponse> productos = this.productoRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc("Cla");

        assertFalse(productos.isEmpty());
        assertEquals(1, productos.size());
        verify(this.productoRepository).findByNombreContainingIgnoreCaseOrderByNombreAsc("Cla");
    }

    @Test
    void testFindByCategoriaIdOrderByNombreAsc(){
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
                this.productoService.findByCategoriaIdOrderByNombreAsc(1);

        assertFalse(productos.isEmpty());
        assertEquals(3,productos.size());
        verify(this.productoRepository).findByCategoriaIdOrderByNombreAsc(1);
    }

    @Test
    void testCreateProducto(){
        ProductoRequest request = new ProductoRequest(1,"Mouse",10.0);
        CategoriaResponse categoriaDB = new CategoriaResponse(1,"Periferico");
        Producto productoDB = new Producto(1,
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

        ProductoResponse producto = this.productoService.createProducto(request);

        assertNotNull(producto);
        assertEquals(1,producto.getCodigo());
        assertEquals("Mouse",producto.getNombre());
        verify(this.productoRepository).findProductoByNombre(anyString());
        verify(this.categoriaService).findCategoriaById(anyInt());
        verify(this.productoRepository).save(any(Producto.class));

    }

    @Test
    void testCreateProducto_ProductoExists(){
        ProductoRequest request = new ProductoRequest(1,"Mouse",10.0);
        ProductoResponse productoDB = new ProductoResponse(
                1,
                1,
                "Mouse",
                10.0,LocalDate.now());

        when(this.productoRepository.findProductoByNombre(request.getNombre()))
                .thenReturn(Optional.of(productoDB));

        ProductoResponse producto = this.productoService.createProducto(request);

        assertNotNull(producto);
        assertEquals(1,producto.getCodigo());
        assertEquals("Mouse",producto.getNombre());
        verify(this.productoRepository).findProductoByNombre(anyString());
        verify(this.categoriaService,never()).findCategoriaById(1);
        verify(this.productoRepository,never()).save(any(Producto.class));
    }

    



}
