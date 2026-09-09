package com.laboratorio.springboot21.unit.service;

import com.laboratorio.springboot21.dto.CategoriaResponse;
import com.laboratorio.springboot21.dto.ProductoRequest;
import com.laboratorio.springboot21.dto.ProductoResponse;
import com.laboratorio.springboot21.model.Categoria;
import com.laboratorio.springboot21.model.Producto;
import com.laboratorio.springboot21.repository.CategoriaRepository;
import com.laboratorio.springboot21.repository.ProductoRepository;
import com.laboratorio.springboot21.service.CategoriaService;
import com.laboratorio.springboot21.service.ProductoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {
    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private ProductoServiceImpl productoService;

    @Test
    void findProductoById_ProductoExists(){
        ProductoResponse productoDb =
                new ProductoResponse(
                        1,
                        1,
                        "Mouse",
                        10.00,
                        LocalDate.now());

        when(this.productoRepository.findProductoById(1))
                .thenReturn(Optional.of(productoDb));

        Optional<ProductoResponse> producto = this.productoService.findProductoById(1);

        assertTrue(producto.isPresent());
        assertEquals("Mouse", producto.get().getNombre());
        verify(this.productoRepository).findProductoById(anyInt());
    }

    @Test
    void findProductoById_ProductoNotFound(){
        when(this.productoRepository.findProductoById(1))
                .thenReturn(Optional.empty());

        Optional<ProductoResponse> producto = this.productoService.findProductoById(1);

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
                this.productoService.findProductoByNombre("Mouse");

        assertTrue(producto.isPresent());
        assertEquals(1, producto.get().getCodigo());
        verify(this.productoRepository).findProductoByNombre(anyString());
    }

    @Test
    void testFindOneByNombre_ProductoNotFount(){

        when(this.productoRepository.findProductoByNombre(anyString()))
                .thenReturn(Optional.empty());

        Optional<ProductoResponse> producto = this.productoService.findProductoByNombre("Mouse");

        assertTrue(producto.isEmpty());
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
        when(productoRepository.findAllOrderByNombreAsc())
                .thenReturn(productosDB);

        List<ProductoResponse> productos =
                this.productoService.findAllOrderByNombreAsc();

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

        List<ProductoResponse> productos = this.productoService.findByNombreContainingIgnoreCaseOrderByNombreAsc("Cla");

        assertFalse(productos.isEmpty());
        assertEquals(1, productos.size());
        verify(this.productoRepository).findByNombreContainingIgnoreCaseOrderByNombreAsc("Cla");
    }

    @Test
    void testFindByCategoriaIdOrderByNombreAsc(){
        List<ProductoResponse> productosDB = List.of(
            new ProductoResponse(
                1,1,"Mouse",10.0, LocalDate.now()),
            new ProductoResponse(
                2,1,"Teclado",15.0, LocalDate.now()),
            new ProductoResponse(
                    3,1,"Disco Externo",80.0, LocalDate.now())
        );

        when(productoRepository.findByCategoriaIdOrderByNombreAsc(anyInt()))
                .thenReturn(productosDB);

        List<ProductoResponse> productos =
                this.productoService.findByCategoriaIdOrderByNombreAsc(1);

        assertFalse(productos.isEmpty());
        assertEquals(3,productos.size());
        verify(this.productoRepository).findByCategoriaIdOrderByNombreAsc(1);
    }

    @Test
    void testCreateProduct(){
        ProductoRequest request = new ProductoRequest(1,"Mouse",10.0);
        CategoriaResponse categoriaDB = new CategoriaResponse(1,"Periferico");
        Producto productoDB = new Producto(1,1,"Mouse",10.0,LocalDate.now(),
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
        verify(this.productoRepository).findProductoByNombre("Mouse");
        verify(this.categoriaService).findCategoriaById(1);
        verify(this.productoRepository).save(any(Producto.class));

    }

    @Test
    void createProduct_ProductoExists(){
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
        verify(this.productoRepository).findProductoByNombre("Mouse");
        verify(this.categoriaService, never()).findCategoriaById(1);
        verify(this.productoRepository, never()).save(any(Producto.class));
    }


}
