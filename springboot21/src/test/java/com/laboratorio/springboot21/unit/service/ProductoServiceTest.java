package com.laboratorio.springboot21.unit.service;

import com.laboratorio.springboot21.dto.ProductoResponse;
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
    void findByNombreContainingIgnoreCaseOrderByNombreAsc(){
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


}
