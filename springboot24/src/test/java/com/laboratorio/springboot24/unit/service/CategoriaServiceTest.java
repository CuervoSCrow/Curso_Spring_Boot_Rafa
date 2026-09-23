package com.laboratorio.springboot24.unit.service;


import com.laboratorio.springboot24.dto.CategoriaRequest;
import com.laboratorio.springboot24.dto.CategoriaResponse;

import com.laboratorio.springboot24.exception.InvalidOperationException;
import com.laboratorio.springboot24.exception.ResourceNotFoundException;
import com.laboratorio.springboot24.model.Categoria;
import com.laboratorio.springboot24.repository.CategoriaRepository;
import com.laboratorio.springboot24.repository.ProductoRepository;
import com.laboratorio.springboot24.service.CategoriaServiceImpl;
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


@ExtendWith(MockitoExtension .class)
class CategoriaServiceTest {
    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    @Test
    void findCategoriaByIdTest_CategoryExist() {
        CategoriaResponse categoriaDB = new CategoriaResponse(
                1,
                "perifericos");
        when(categoriaRepository.findCategoriaById(1))
                .thenReturn(Optional.of(categoriaDB));

        Optional<CategoriaResponse> categoria =
                categoriaService.findCategoriaById(1);

        assertTrue(categoria.isPresent());
        assertEquals("perifericos", categoria.get().getNombre());
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
    void findCategoriaByNombreTest_CategoriaExists(){
        CategoriaResponse categoriaDB = new
                CategoriaResponse(1, "perifericos");
        when(this.categoriaRepository.findCategoriaByNombre("perifericos"))
                .thenReturn(Optional.of(categoriaDB));
        Optional<CategoriaResponse> categoria =
                categoriaService.findCategoriaByNombre("perifericos");
        assertTrue(categoria.isPresent());
        assertEquals("perifericos", categoria.get().getNombre());
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
    void findAllOrderByNombreAscTest(){
        List<CategoriaResponse> categoriaDB = new ArrayList<>(
                List.of(
                        new CategoriaResponse(1, "impresoras"),
                        new CategoriaResponse(2, "monitores"),
                        new CategoriaResponse(3, "perifericos")
                )
        );
        when(this.categoriaRepository.findAllOrderByNombreAsc())
                .thenReturn(categoriaDB);
        List<CategoriaResponse> categorias =
                categoriaService.findAllOrderByNombreAsc();

        assertFalse(categorias.isEmpty());
        assertEquals(3, categorias.size());
        assertEquals(categoriaDB, categorias);
        verify(categoriaRepository).findAllOrderByNombreAsc();
    }
    @Test
    void findByNombreContainingIgnoreCaseOrderByNombreAscTest(){
        String infix="NiTo";
        List<CategoriaResponse> categoriasDB = List.of(
                new CategoriaResponse(2,"Monitores")
        );
        when(this.categoriaRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix))
                .thenReturn(categoriasDB);
        List<CategoriaResponse> categorias =
                categoriaService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);

        assertFalse(categorias.isEmpty());
        assertEquals(1, categorias.size());
        assertEquals(categoriasDB, categorias);
        verify(categoriaRepository).findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
    }
    @Test
    void createCategoriaTest_CategoriaCreated(){
        CategoriaRequest request =
                new CategoriaRequest("perifericos");
        Categoria categoriaNueva = new Categoria(1, "perifericos");

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
        CategoriaRequest request =new CategoriaRequest("perifericos");
        CategoriaResponse categoriaDB=
                new CategoriaResponse(1,"perifericos");
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
    @Test
    void categoriaUpdateTest_CategoriaUpdated(){
        CategoriaRequest request = new CategoriaRequest("periféricos");
        CategoriaResponse categoriaDB = new CategoriaResponse(1, "perifericos");
        Categoria categoriaModificada = new Categoria(1, "periféricos");

        when(this.categoriaRepository.findCategoriaById(anyInt()))
                .thenReturn(Optional.of(categoriaDB));
        when(this.categoriaRepository.findCategoriaByNombre(anyString()))
                .thenReturn(Optional.empty());
        when(this.categoriaRepository.save(any(Categoria.class)))
                .thenReturn(categoriaModificada);

        CategoriaResponse categoria = this.categoriaService.updateCategoria(1, request);

        assertNotNull(categoria);
        assertEquals(1,categoria.getId());
        verify(this.categoriaRepository).findCategoriaById(1);
        verify(this.categoriaRepository).findCategoriaByNombre("periféricos");
        verify(this.categoriaRepository).save(any(Categoria.class));
    }
    @Test
    void categoriaUpdateTest_ReturnNotFound(){
        CategoriaRequest request = new CategoriaRequest("periféricos");

        when(this.categoriaRepository.findCategoriaById(anyInt()))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                ()->{this.categoriaService.updateCategoria(1,request);}
        );
        assertEquals("No se puede efectuar la modificación, " +
                "la categoria no existe",exception.getMessage());
        verify(this.categoriaRepository).findCategoriaById(1);
        verify(this.categoriaRepository,never()).findCategoriaByNombre(anyString());
        verify(this.categoriaRepository,never()).save(any(Categoria.class));
    }
    @Test
    void categoriaUpdateTest_DuplicatedName(){
        CategoriaRequest request = new CategoriaRequest("periféricos");
        CategoriaResponse categoriaDB1 = new CategoriaResponse(1, "perifericos");
        CategoriaResponse categoriaDB2 = new CategoriaResponse(2, "periféricos");

        when(this.categoriaRepository.findCategoriaById(1))
                .thenReturn(Optional.of(categoriaDB1));
        when(this.categoriaRepository.findCategoriaByNombre(request.getNombre()))
                .thenReturn(Optional.of(categoriaDB2));

        InvalidOperationException exception = assertThrows(
                InvalidOperationException.class,
                ()->{this.categoriaService.updateCategoria(1,request);}
        );
        assertEquals("No se puede efectuar la modificación, " +
                "el nombre de la categoria ya existe",exception.getMessage());
        verify(this.categoriaRepository).findCategoriaById(1);
        verify(this.categoriaRepository).findCategoriaByNombre("periféricos");
        verify(this.categoriaRepository,never()).save(any(Categoria.class));
    }
    @Test
    void categoriaDeleteTest_CategoryDeleted(){
        CategoriaResponse categoriaDB = new CategoriaResponse(1, "perifericos");

        when(this.categoriaRepository.findCategoriaById(1))
                .thenReturn(Optional.of(categoriaDB));
        when(this.productoRepository.countByCategoriaId(1))
                .thenReturn(0L);

        boolean result = this.categoriaService.deleteCategoria(1);

        assertTrue(result);
        verify(categoriaRepository).findCategoriaById(1);
        verify(productoRepository).countByCategoriaId(1);
        verify(categoriaRepository).deleteById(1);
    }
    @Test
    void categoriaDeleteTest_CategoryNotFound(){
        when(this.categoriaRepository.findCategoriaById(1))
                .thenReturn(Optional.empty());

        boolean result = this.categoriaService.deleteCategoria(1);

        assertFalse(result);
        verify(this.categoriaRepository).findCategoriaById(1);
        verify(this.productoRepository, never()).countByCategoriaId(1);
        verify(this.categoriaRepository, never()).deleteById(1);
    }
    @Test
    void categoriaDeleteTest_HasProducts(){
        CategoriaResponse categoriaDB = new CategoriaResponse(1, "perifericos");

        when(this.categoriaRepository.findCategoriaById(1))
                .thenReturn(Optional.of(categoriaDB));
        when(this.productoRepository.countByCategoriaId(1))
                .thenReturn(2L);
        InvalidOperationException exception = assertThrows(
                InvalidOperationException.class,
                ()->{this.categoriaService.deleteCategoria(1);}
        );
        assertEquals("No se puede eliminar la categoria, " +
                "la categoria tiene productos asociados",exception.getMessage());
        verify(this.categoriaRepository).findCategoriaById(1);
        verify(this.productoRepository).countByCategoriaId(1);
        verify(this.categoriaRepository, never()).deleteById(1);
    }
}
