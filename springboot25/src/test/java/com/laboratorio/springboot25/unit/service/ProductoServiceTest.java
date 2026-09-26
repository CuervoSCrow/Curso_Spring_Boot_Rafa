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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
}
