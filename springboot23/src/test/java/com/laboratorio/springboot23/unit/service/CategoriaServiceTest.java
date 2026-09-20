package com.laboratorio.springboot23.unit.service;

import com.laboratorio.springboot23.dto.CategoriaRequest;
import com.laboratorio.springboot23.dto.CategoriaResponse;
import com.laboratorio.springboot23.exception.InvalidOperationException;
import com.laboratorio.springboot23.exception.ResourceNotFoundException;
import com.laboratorio.springboot23.model.Categoria;
import com.laboratorio.springboot23.repository.CategoriaRepository;
import com.laboratorio.springboot23.repository.ProductoRepository;
import com.laboratorio.springboot23.service.CategoriaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

    //------------------<TESTS>------------------

    //    ---------------<BUSQUEDAS>------------------
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

    @Test
    void findCategoriaByNombre_CategoriaExists() {
        CategoriaResponse categoriaDB = new
                CategoriaResponse(1, "perifericos");
        when(categoriaRepository.findCategoriaByNombre(anyString()))
                .thenReturn(Optional.of(categoriaDB));

        Optional<CategoriaResponse> categoria =
                this.categoriaService.findCategoriaByNombre("perifericos");

        assertTrue(categoria.isPresent());
        verify(this.categoriaRepository).findCategoriaByNombre(anyString());
    }

    @Test
    void findCategoriaByNombre_CategoriaNotFound(){

        when(categoriaRepository.findCategoriaByNombre(anyString()))
                .thenReturn(Optional.empty());

        Optional<CategoriaResponse> categoria =
                this.categoriaService.findCategoriaByNombre("perifericos");

        assertTrue(categoria.isEmpty());
        verify(this.categoriaRepository).findCategoriaByNombre(anyString());
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
        when(categoriaRepository.findAllOrderByNombreAsc())
                .thenReturn(categoriaDB);

        List<CategoriaResponse> categorias =
                this.categoriaService.findAllOrderByNombreAsc();

        assertFalse(categorias.isEmpty());
        assertEquals(3, categorias.size());
        verify(this.categoriaRepository).findAllOrderByNombreAsc();
    }

    @Test
    void findByNombreContainingIgnoreCaseOrderByNombreAscTest(){
        List<CategoriaResponse> categoriasDB = List.of(
                new CategoriaResponse(2,"Monitores")
        );
        when(this.categoriaRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(anyString()))
                .thenReturn(categoriasDB);

        List<CategoriaResponse> categorias =
                this.categoriaService.findByNombreContainingIgnoreCaseOrderByNombreAsc("NitO");

        assertFalse(categorias.isEmpty());
        assertEquals(1,categorias.size());
        verify(this.categoriaRepository).findByNombreContainingIgnoreCaseOrderByNombreAsc("NitO");
    }

    //------------------<CREACION>------------------
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
                this.categoriaService.createCategoria(request);

        assertNotNull(categoria);
        assertEquals(1,categoria.getId());
        assertEquals("perifericos",categoria.getNombre());
        verify(this.categoriaRepository).findCategoriaByNombre(anyString());
        verify(this.categoriaRepository).save(any(Categoria.class));
    }

    @Test
    void createCategoriaTest_ReturnExsisting(){
        CategoriaRequest request =new CategoriaRequest("perifericos");
        CategoriaResponse categoriaDB=
                new CategoriaResponse(1,"perifericos");

        when(categoriaRepository.findCategoriaByNombre(anyString()))
                .thenReturn(Optional.of(categoriaDB));

        CategoriaResponse categoria = this.categoriaService.createCategoria(request);

        assertNotNull(categoria);
        assertEquals(1,categoria.getId());
        assertEquals("perifericos",categoria.getNombre());
        verify(this.categoriaRepository).findCategoriaByNombre("perifericos");
        verify(this.categoriaRepository, never()).save(any(Categoria.class));
    }

    //------------------<ACTUALIZACION>------------------

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

    //------------------<ELIMINACION>------------------

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
