package com.laboratorio.springboot25.unit.service;

import com.laboratorio.springboot25.dto.CategoriaResponse;
import com.laboratorio.springboot25.repository.CategoriaRepository;
import com.laboratorio.springboot25.repository.ProductoRepository;
import com.laboratorio.springboot25.service.CategoriaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {
    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    @Test
    void findCategoriaByIdTest_CategoriaExists() {
        CategoriaResponse response = new CategoriaResponse(
                1,
                "perifericos");
        when(categoriaRepository.findCategoriaById(1))
                .thenReturn(Optional.of(response));

        Optional<CategoriaResponse> categoria =
                categoriaService.findCategoriaById(1);

        assertTrue(categoria.isPresent());
        assertEquals("perifericos",categoria.get().getNombre());
        verify(categoriaRepository).findCategoriaById(1);
    }
    @Test
    void findCategoriaByIdTest_CategoryNotFound(){
        when(categoriaRepository.findCategoriaById(1))
                .thenReturn(Optional.empty());
        Optional<CategoriaResponse> categoria =
                categoriaService.findCategoriaById(1);
        assertTrue(categoria.isEmpty());
        verify(categoriaRepository).findCategoriaById(1);
    }
    @Test
    void findCategoriaByNombreTest_CategoriaExists() {
        CategoriaResponse response = new CategoriaResponse(
                1,"perifericos");
        when(this.categoriaRepository.findCategoriaByNombre("perifericos"))
                .thenReturn(Optional.of(response));

        Optional<CategoriaResponse> categoria =
                categoriaService.findCategoriaByNombre("perifericos");
        assertTrue(categoria.isPresent());
        assertEquals("perifericos",categoria.get().getNombre());
        verify(categoriaRepository).findCategoriaByNombre("perifericos");
    }
    @Test
    void findCategoriaByNombreTest_CategoriaNotFound(){
        when(this.categoriaRepository.findCategoriaByNombre(anyString()))
                .thenReturn(Optional.empty());
        Optional<CategoriaResponse> categoria =
                categoriaService.findCategoriaByNombre("perifericos");
        assertTrue(categoria.isEmpty());
        verify(categoriaRepository).findCategoriaByNombre("perifericos");
    }

}
