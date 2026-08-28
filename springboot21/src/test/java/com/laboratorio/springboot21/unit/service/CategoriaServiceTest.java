package com.laboratorio.springboot21.unit.service;

import com.laboratorio.springboot21.dto.CategoriaResponse;
import com.laboratorio.springboot21.repository.CategoriaRepository;
import com.laboratorio.springboot21.service.CategoriaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {
    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    @Test
    void findCategoriaByIdTest_CategoriaExista() {
        CategoriaResponse categoriaDB =
                new CategoriaResponse(
                        1,
                        "perifericos"
                        );
        when(categoriaRepository
                        .findCategoriaById(1))
                .thenReturn(Optional.of(categoriaDB));

        Optional<CategoriaResponse> categoria =
                this.categoriaService.findCategoriaById(1);

        assertTrue(categoria.isPresent());
        assertEquals("perifericos" ,categoria.get().getNombre());
        verify(this.categoriaRepository).findCategoriaById(1);
    }
    @Test
    void findCategoriaByIdTest_CategoriaNotFound() {
        when(this.categoriaRepository.findCategoriaById(1)).thenReturn(Optional.empty());

        Optional<CategoriaResponse> categoria =
                this.categoriaService.findCategoriaById(1);

        assertTrue(categoria.isEmpty());
        verify(this.categoriaRepository).findCategoriaById(1);
    }
}
