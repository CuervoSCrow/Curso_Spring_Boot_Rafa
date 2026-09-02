package com.laboratorio.springboot21.unit.service;

import com.laboratorio.springboot21.dto.CategoriaRequest;
import com.laboratorio.springboot21.dto.CategoriaResponse;
import com.laboratorio.springboot21.dto.ProductoResponse;
import com.laboratorio.springboot21.exception.InvalidOperationException;
import com.laboratorio.springboot21.exception.ResourceNotFoundException;
import com.laboratorio.springboot21.model.Categoria;
import com.laboratorio.springboot21.repository.CategoriaRepository;
import com.laboratorio.springboot21.service.CategoriaServiceImpl;
import com.laboratorio.springboot21.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {
    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private CategoriaServiceImpl categoriaService;

    @Test
    void testfindCategoriaById_CategoriaExist() {
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

    @Test
    void testFindCategoriaByNombre_CategoriaExist(){
        CategoriaResponse categoriaDB =
                new CategoriaResponse(1, "perifericos");

        when(categoriaRepository
                .findCategoriaByNombre("perifericos"))
                        .thenReturn(Optional.of(categoriaDB));
        Optional<CategoriaResponse>categoria=
                this.categoriaService.findCategoriaByNombre("perifericos");
        assertTrue(categoria.isPresent());
        assertEquals(1,categoria.get().getId());
        verify(this.categoriaRepository).findCategoriaByNombre("perifericos");
    }

    @Test
    void testFindCategoriaByNombre_CategoriaNotFound(){
        when(this.categoriaRepository
                .findCategoriaByNombre("perifericos"))
                    .thenReturn(Optional.empty());
        Optional<CategoriaResponse> categoria =
                this.categoriaService.findCategoriaByNombre("perifericos");
        assertTrue(categoria.isEmpty());
        verify(this.categoriaRepository).findCategoriaByNombre("perifericos");
    }

    @Test
    void testFindAllOrderByNombreAsc(){
        List<CategoriaResponse> categoriasDB =
                List.of(
                        new CategoriaResponse(1, "Impresoras"),
                        new CategoriaResponse(2, "Monitores"),
                        new CategoriaResponse(3, "Perifericos")
                );

        when(this.categoriaRepository
                .findAllOrderByNombreAsc())
                    .thenReturn(categoriasDB);
        List<CategoriaResponse> categorias = this.categoriaService.findAllOrderByNombreAsc();

        assertFalse(categorias.isEmpty());
        assertEquals(3, categorias.size());
        verify(this.categoriaRepository).findAllOrderByNombreAsc();
    }

    @Test
    void testFindByNombreContainingIgnoreCaseOrderByNombreAsc(){
        List<CategoriaResponse> categoriasDB = List.of(
                new CategoriaResponse(2, "Monitores")
        );
        when(this.categoriaRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc("Nito"))
                                     .thenReturn(categoriasDB);
        List<CategoriaResponse> categorias =
                this.categoriaService.findByNombreContainingIgnoreCaseOrderByNombreAsc("Nito");

        assertFalse(categorias.isEmpty());
        assertEquals(1, categorias.size());
        verify(this.categoriaRepository).findByNombreContainingIgnoreCaseOrderByNombreAsc("Nito");
    }

    @Test
    void testCreateCategoria_CategoriaCreated(){
        CategoriaRequest request = new CategoriaRequest("Perifericos");
        Categoria categoriaNueva = new Categoria(1,"Perifericos");
        when(this.categoriaRepository.findCategoriaByNombre("Perifericos"))
                .thenReturn(Optional.empty());
        when(this.categoriaRepository
                .save(any(Categoria.class)))
                        .thenReturn(categoriaNueva);
        CategoriaResponse categoria = this.categoriaService.createCategoria(request);

        assertNotNull(categoria);
        assertEquals(1, categoria.getId());
        assertEquals("Perifericos", categoria.getNombre());
        verify(this.categoriaRepository).findCategoriaByNombre("Perifericos");
        verify(this.categoriaRepository).save(any(Categoria.class));
    }

    @Test
    void testCreateCategoria_ReturnExisting(){
        CategoriaRequest request  = new CategoriaRequest("Perifericos");
        CategoriaResponse categoriaDB = new CategoriaResponse(1,"Perifericos");

        when(this.categoriaRepository
                 .findCategoriaByNombre("Perifericos"))
                 .thenReturn(Optional.of(categoriaDB));

        CategoriaResponse categoria = this.categoriaService.createCategoria(request);

        assertNotNull(categoria);
        assertEquals(1, categoria.getId());
        assertEquals("Perifericos", categoria.getNombre());
        verify(this.categoriaRepository).findCategoriaByNombre("Perifericos");
        verify(this.categoriaRepository,never()).save(any(Categoria.class));
    }

    @Test
    void testUpdateCategoria_CategoriaUpdated(){
        CategoriaRequest request = new CategoriaRequest("Perifericos");
        CategoriaResponse categoriaDB = new CategoriaResponse(1,"Perifericos");
        Categoria categoriaModificada = new Categoria(1,"Perifericos");

        when(this.categoriaRepository.findCategoriaById(1))
                .thenReturn(Optional.of(categoriaDB));
        when(this.categoriaRepository.findCategoriaByNombre("Perifericos"))
                .thenReturn(Optional.empty());
        when(this.categoriaRepository.save(any(Categoria.class)))
                .thenReturn(categoriaModificada);

        CategoriaResponse categoria = this.categoriaService.updateCategoria(1, request);

        assertNotNull(categoria);
        assertEquals("Perifericos",categoria.getNombre());
        verify(this.categoriaRepository).findCategoriaById(1);
        verify(this.categoriaRepository).findCategoriaByNombre("Perifericos");
        verify(this.categoriaRepository).save(any(Categoria.class));

    }

    @Test
    void testUpdateCategoria_CategoryNotFound(){
        CategoriaRequest request = new CategoriaRequest("Perifericos");
        when(this.categoriaRepository.findCategoriaById(1))
                .thenReturn(Optional.empty());
        ResourceNotFoundException exception =
                assertThrows(ResourceNotFoundException.class, () -> {
            this.categoriaService.updateCategoria(
                    1,
                    request
            );
        });
        assertEquals("No se puede efectuarla modificacion, " +
                "la Categoria no existe", exception.getMessage());
        verify(this.categoriaRepository).findCategoriaById(1);
        verify(this.categoriaRepository, never()).findCategoriaByNombre(anyString());
        verify(this.categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    void testUpdateCategoria_CategoryAlreadyExists(){
        CategoriaRequest request = new CategoriaRequest("Periféricos");

        CategoriaResponse categoriaDB = new CategoriaResponse(1,"Perifericos");
        CategoriaResponse otraCategoria = new CategoriaResponse(2,"Periféricos");

        when(this.categoriaRepository.findCategoriaById(1))
                .thenReturn(Optional.of(categoriaDB));
        when(this.categoriaRepository.findCategoriaByNombre(request.getNombre()))
                .thenReturn(Optional.of(otraCategoria));

        InvalidOperationException exception =
                assertThrows(InvalidOperationException.class,
                        ()->{
                            this.categoriaService.updateCategoria(1,request);
                        });

        assertEquals("No se puede efectuar la modificación, " +
                "el nombre de la categoria ya existe", exception.getMessage());
        verify(this.categoriaRepository).findCategoriaById(1);
        verify(this.categoriaRepository).findCategoriaByNombre(request.getNombre());
        verify(this.categoriaRepository).findCategoriaByNombre("Periféricos");
        verify(this.categoriaRepository, never()).save(any(Categoria.class));

    }

    @Test
    void testDeleteCategoria_CategoriaDeleted(){
        CategoriaResponse categoriaDB = new CategoriaResponse(1,"Periféricos");
        when(this.categoriaRepository.findCategoriaById(1))
                .thenReturn(Optional.of(categoriaDB));
        when(this.productoService.findByCategoriaIdOrderByNombreAsc(1))
                .thenReturn(Collections.emptyList());

        boolean result = this.categoriaService.deleteCategoria(1);
        assertTrue(result);
        verify(this.categoriaRepository).findCategoriaById(1);
        verify(this.productoService).findByCategoriaIdOrderByNombreAsc(1);
        verify(this.categoriaRepository).deleteById(1);
    }

    @Test
    void testDeleteCategoria_CategoriaNotFound(){
        when(this.categoriaRepository.findCategoriaById(1))
                .thenReturn(Optional.empty());
        boolean result =this.categoriaService.deleteCategoria(1);

        assertFalse(result);
        verify(this.categoriaRepository).findCategoriaById(1);
        verify(this.productoService,never()).findByCategoriaIdOrderByNombreAsc(anyInt());
        verify(this.categoriaRepository,never()).deleteById(anyInt());
    }

    @Test
    void testDeleteCategoria_HasProductos(){
        CategoriaResponse  categoriaDB = new CategoriaResponse(1,"Periféricos");
        List<ProductoResponse> productosDB = List.of(
                new ProductoResponse(
                        1,
                        1,
                        "Mouse",
                        10.00,
                        LocalDate.now())
        );
        when(this.categoriaRepository.findCategoriaById(1))
                .thenReturn(Optional.of(categoriaDB));
        when(this.productoService.findByCategoriaIdOrderByNombreAsc(1))
                .thenReturn(productosDB);

        InvalidOperationException exception =
                assertThrows(InvalidOperationException.class,()->{
                    this.categoriaService.deleteCategoria(1);
                });

        assertEquals("No se puede eliminar una categoria con productos asociados",
                exception.getMessage());

        verify(this.categoriaRepository).findCategoriaById(1);
        verify(this.productoService).findByCategoriaIdOrderByNombreAsc(1);
        verify(this.categoriaRepository,never()).deleteById(anyInt());

    }
}
