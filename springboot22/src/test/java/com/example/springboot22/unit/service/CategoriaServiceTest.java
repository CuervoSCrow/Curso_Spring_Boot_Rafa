package com.example.springboot22.unit.service;

import com.example.springboot22.dto.CategoriaResponse;
import com.example.springboot22.repository.CategoriaRepository;
import com.example.springboot22.repository.ProductoRepository;
import com.example.springboot22.service.CategoriaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {
    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    @Test
    void testFindCategoriaById_CategoriaExists() {
        CategoriaResponse categoriaDB = new CategoriaResponse(
                1,
                "perifericos");
        when(categoriaRepository.findCategoriaById(1))
                .thenReturn(Optional.of(categoriaDB));

        Optional<CategoriaResponse> categoria =
                this.categoriaService.findCategoriaById(1);

        assertTrue(categoria.isPresent());
        assertEquals("perifericos",categoria.get().getNombre());
        verify(this.categoriaRepository).findCategoriaById(1);
    }

    @Test
    void testFindCategoriaById_CategoriaNotFound(){
        when(categoriaRepository.findCategoriaById(1))
                .thenReturn(Optional.empty());

        Optional<CategoriaResponse> categoria =
                this.categoriaService.findCategoriaById(1);

        assertTrue(categoria.isEmpty());
        verify(this.categoriaRepository).findCategoriaById(1);
    }
}
