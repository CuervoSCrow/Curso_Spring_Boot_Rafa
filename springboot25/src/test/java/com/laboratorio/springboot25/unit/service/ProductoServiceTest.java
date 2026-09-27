package com.laboratorio.springboot25.unit.service;

import com.laboratorio.springboot25.dto.ProductoResponse;
import com.laboratorio.springboot25.repository.ProductoRepository;
import com.laboratorio.springboot25.service.ProductoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension .class)
public class ProductoServiceTest {
    @Mock
    private ProductoRepository productoRepository;

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
}
