package com.laboratorio.springboot25.unit.service;

import com.laboratorio.springboot25.dto.CategoriaRequest;
import com.laboratorio.springboot25.dto.CategoriaResponse;
import com.laboratorio.springboot25.model.Categoria;
import com.laboratorio.springboot25.repository.CategoriaRepository;
import com.laboratorio.springboot25.repository.ProductoRepository;
import com.laboratorio.springboot25.service.CategoriaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
    @Test
    void findAllOrderByNombreAscTest() {
        List<CategoriaResponse> categoriasDB = new ArrayList<>(
                List.of(
                        new CategoriaResponse(1, "impresoras"),
                        new CategoriaResponse(2, "monitores"),
                        new CategoriaResponse(3, "perifericos")
                )
        );
        when(this.categoriaRepository.findAllOrderByNombreAsc())
                .thenReturn(categoriasDB);
        List<CategoriaResponse> categorias =
                categoriaService.findAllOrderByNombreAsc();
        assertFalse(categorias.isEmpty());
        assertEquals(3,categorias.size());
        assertEquals(categoriasDB,categorias);
        verify(this.categoriaRepository).findAllOrderByNombreAsc();
    }
    @Test
    void findByNombreContainingIgnoreCaseOrderByNombreAscTest() {
        String infix="NiTO";
        List<CategoriaResponse> categoriasDB=
                List.of(
                        new CategoriaResponse(1, "impresoras"),
                        new CategoriaResponse(2, "monitores"),
                        new CategoriaResponse(3, "perifericos")
                );
        when(this.categoriaRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix))
                .thenReturn(categoriasDB);
        List<CategoriaResponse> categorias =
                categoriaService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        assertFalse(categorias.isEmpty());
        assertEquals(3,categorias.size());
        assertEquals(categoriasDB,categorias);
        verify(this.categoriaRepository).findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
    }
    @Test
    void createCategoriaTest_CategoriaCreated() {
        CategoriaRequest request = new CategoriaRequest(
                "perifericos");
        Categoria categoriaNueva = new Categoria(1,"perifericos");

        when(this.categoriaRepository.findCategoriaByNombre("perifericos"))
                .thenReturn(Optional.empty());
        when(this.categoriaRepository.save(any(Categoria.class)))
                .thenReturn(categoriaNueva);

        CategoriaResponse categoria =
                categoriaService.createCategoria(request);

        assertNotNull(categoria);
        assertEquals(1,categoria.getId());
        assertEquals("perifericos",categoria.getNombre());
        verify(this.categoriaRepository).findCategoriaByNombre(anyString());
        verify(this.categoriaRepository).save(any(Categoria.class));
    }
    @Test
    void createCategoriaTest_CategoryExists(){
        CategoriaRequest request = new CategoriaRequest("perifericos");
        CategoriaResponse categoriaDB = new
                CategoriaResponse(1,"perifericos");

        when(this.categoriaRepository.findCategoriaByNombre("perifericos"))
                .thenReturn(Optional.of(categoriaDB));
        CategoriaResponse categoria =
                categoriaService.createCategoria(request);

        assertNotNull(categoria);
        assertEquals(1,categoria.getId());
        assertEquals("perifericos",categoria.getNombre());
        verify(this.categoriaRepository).findCategoriaByNombre(anyString());
        verify(this.categoriaRepository, never()).save(any(Categoria.class));
    }

}
