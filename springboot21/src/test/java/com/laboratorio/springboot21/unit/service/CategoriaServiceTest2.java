package com.laboratorio.springboot21.unit.service;

import com.laboratorio.springboot21.dto.CategoriaResponse;
import com.laboratorio.springboot21.repository.CategoriaRepository;
import com.laboratorio.springboot21.service.CategoriaServiceImpl;
import com.laboratorio.springboot21.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest2 {
    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    @Test
    void testFindCategoriaById_CategoriaExist(){
        CategoriaResponse categoriaDB =
                new CategoriaResponse(
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
}
