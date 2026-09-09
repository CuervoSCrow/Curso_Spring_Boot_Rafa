package com.laboratorio.springboot21.unit.service;

import com.laboratorio.springboot21.dto.CategoriaResponse;
import com.laboratorio.springboot21.dto.ProductoRequest;
import com.laboratorio.springboot21.dto.ProductoResponse;
import com.laboratorio.springboot21.exception.InvalidOperationException;
import com.laboratorio.springboot21.exception.ResourceNotFoundException;
import com.laboratorio.springboot21.model.Categoria;
import com.laboratorio.springboot21.model.Producto;
import com.laboratorio.springboot21.repository.ProductoRepository;
import com.laboratorio.springboot21.service.CategoriaService;
import com.laboratorio.springboot21.service.ProductoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {
    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private ProductoServiceImpl productoService;

    @Test
    void findProductoById_ProductoExists(){
        ProductoResponse productoDb =
                new ProductoResponse(
                        1,
                        1,
                        "Mouse",
                        10.00,
                        LocalDate.now());

        when(this.productoRepository.findProductoById(1))
                .thenReturn(Optional.of(productoDb));

        Optional<ProductoResponse> producto = this.productoService.findProductoById(1);

        assertTrue(producto.isPresent());
        assertEquals("Mouse", producto.get().getNombre());
        verify(this.productoRepository).findProductoById(anyInt());
    }

    @Test
    void findProductoById_ProductoNotFound(){
        when(this.productoRepository.findProductoById(1))
                .thenReturn(Optional.empty());

        Optional<ProductoResponse> producto = this.productoService.findProductoById(1);

        assertTrue(producto.isEmpty());
        verify(this.productoRepository).findProductoById(anyInt());
    }

    @Test
    void findProductoByNombre_ProductoExists(){
        ProductoResponse productoDB =
                new ProductoResponse(
                    1,
                    1,
                    "Mouse",
                    10.00,
                    LocalDate.now()
                );

        when(this.productoRepository.findProductoByNombre("Mouse"))
                .thenReturn(Optional.of(productoDB));

        Optional<ProductoResponse> producto =
                this.productoService.findProductoByNombre("Mouse");

        assertTrue(producto.isPresent());
        assertEquals(1, producto.get().getCodigo());
        verify(this.productoRepository).findProductoByNombre(anyString());
    }

    @Test
    void testFindOneByNombre_ProductoNotFount(){

        when(this.productoRepository.findProductoByNombre(anyString()))
                .thenReturn(Optional.empty());

        Optional<ProductoResponse> producto = this.productoService.findProductoByNombre("Mouse");

        assertTrue(producto.isEmpty());
        verify(this.productoRepository).findProductoByNombre(anyString());
    }

    @Test
    void testfindAllOrderByNombreAsc(){
        List<ProductoResponse> productosDB = new ArrayList<>(
                List.of(
                        new ProductoResponse(1, 1,
                                "Mouse", 10.00, LocalDate.now()),
                        new ProductoResponse(2, 1,
                                "Teclado", 15.00, LocalDate.now()),
                        new ProductoResponse(3, 1,
                                "Disco Externo", 80.00, LocalDate.now())
                )
        );
        when(productoRepository.findAllOrderByNombreAsc())
                .thenReturn(productosDB);

        List<ProductoResponse> productos =
                this.productoService.findAllOrderByNombreAsc();

        assertFalse(productos.isEmpty());
        assertEquals(3, productos.size());
        verify(this.productoRepository).findAllOrderByNombreAsc();
    }

    @Test
    void testFindByNombreContainingIgnoreCaseOrderByNombreAsc(){
        List<ProductoResponse> productosDB = List.of(
                new ProductoResponse(2,1,
                        "Teclado",15.00,LocalDate.now())
        );
        when(this.productoRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc("Cla"))
                .thenReturn(productosDB);

        List<ProductoResponse> productos = this.productoService.findByNombreContainingIgnoreCaseOrderByNombreAsc("Cla");

        assertFalse(productos.isEmpty());
        assertEquals(1, productos.size());
        verify(this.productoRepository).findByNombreContainingIgnoreCaseOrderByNombreAsc("Cla");
    }

    @Test
    void testFindByCategoriaIdOrderByNombreAsc(){
        List<ProductoResponse> productosDB = List.of(
            new ProductoResponse(
                1,1,"Mouse",10.0, LocalDate.now()),
            new ProductoResponse(
                2,1,"Teclado",15.0, LocalDate.now()),
            new ProductoResponse(
                    3,1,"Disco Externo",80.0, LocalDate.now())
        );

        when(productoRepository.findByCategoriaIdOrderByNombreAsc(anyInt()))
                .thenReturn(productosDB);

        List<ProductoResponse> productos =
                this.productoService.findByCategoriaIdOrderByNombreAsc(1);

        assertFalse(productos.isEmpty());
        assertEquals(3,productos.size());
        verify(this.productoRepository).findByCategoriaIdOrderByNombreAsc(1);
    }

    @Test
    void testCreateProduct(){
        ProductoRequest request = new ProductoRequest(1,"Mouse",10.0);
        CategoriaResponse categoriaDB = new CategoriaResponse(1,"Periferico");
        Producto productoDB = new Producto(1,1,"Mouse",10.0,LocalDate.now(),
                new Categoria(1,"Periferico"));
        when(this.productoRepository.findProductoByNombre(request.getNombre()))
                .thenReturn(Optional.empty());
        when(this.categoriaService.findCategoriaById(request.getCategoriaId()))
                .thenReturn(Optional.of(categoriaDB));
        when(this.productoRepository.save(any(Producto.class)))
                .thenReturn(productoDB);

        ProductoResponse producto = this.productoService.createProducto(request);

        assertNotNull(producto);
        assertEquals(1,producto.getCodigo());
        assertEquals("Mouse",producto.getNombre());
        verify(this.productoRepository).findProductoByNombre("Mouse");
        verify(this.categoriaService).findCategoriaById(1);
        verify(this.productoRepository).save(any(Producto.class));

    }

    @Test
    void testCreateProduct_ReturnsExists(){
        ProductoRequest request = new ProductoRequest(1,"Mouse",10.0);
        ProductoResponse productoDB = new ProductoResponse(
                1,
                1,
                "Mouse",
                10.0,LocalDate.now());
        when(this.productoRepository.findProductoByNombre(request.getNombre()))
                .thenReturn(Optional.of(productoDB));

        ProductoResponse producto = this.productoService.createProducto(request);
        assertNotNull(producto);
        assertEquals(1,producto.getCodigo());
        assertEquals("Mouse",producto.getNombre());
        verify(this.productoRepository).findProductoByNombre("Mouse");
        verify(this.categoriaService, never()).findCategoriaById(1);
        verify(this.productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void testCreateProducto_CategoriaNotFound(){
        ProductoRequest request = new ProductoRequest(1,"Mouse",10.0);
        when(this.productoRepository.findProductoByNombre(request.getNombre()))
                .thenReturn(Optional.empty());
        when(this.categoriaService.findCategoriaById(request.getCategoriaId()))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                ()->{
                    this.productoService.createProducto(request);
                });
        assertEquals("No Existe la Categoria Indicada no se puede crear el producto.",
                exception.getMessage());
        verify(this.productoRepository).findProductoByNombre("Mouse");
        verify(this.categoriaService).findCategoriaById(1);
        verify(this.productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void testUpdateProducto_ProductUpdate(){
        ProductoRequest request = new ProductoRequest(1,"Mouse",10.0);
        ProductoResponse productoDB = new ProductoResponse(
                1,
                1,
                "Mouse",
                10.0,
                LocalDate.now());
        CategoriaResponse categoriaDB = new CategoriaResponse(1,"Perifericos");
        Producto productoModificado = new Producto(1,1,"Mouse",10.0,LocalDate.now(),
                new Categoria(1,"Perifericos"));

        when(this.productoRepository.findProductoById(1))
                .thenReturn(Optional.of(productoDB));
        when(this.productoRepository.findProductoByNombre(request.getNombre()))
                .thenReturn(Optional.empty());
        when(this.categoriaService.findCategoriaById(request.getCategoriaId()))
                .thenReturn(Optional.of(categoriaDB));
        when(this.productoRepository.save(any(Producto.class)))
                .thenReturn(productoModificado);

        ProductoResponse producto = this.productoService.updateProducto(1,request);

        assertNotNull(producto);
        assertEquals(1,producto.getCodigo());
        assertEquals(10,producto.getPrecio());
        verify(this.productoRepository).findProductoById(1);
        verify(this.productoRepository).findProductoByNombre("Mouse");
        verify(this.categoriaService).findCategoriaById(1);
        verify(this.productoRepository).save(any(Producto.class));

    }

    @Test
    void testUpdateProducto_ProductNotFound(){
        ProductoRequest request = new ProductoRequest(
                1,"Mouse",10.0);
        when(this.productoRepository.findProductoById(1))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception=
                assertThrows(ResourceNotFoundException.class,
                        ()->{this.productoService.updateProducto(1,request);});

        assertEquals("No se puede efectuar la modificación, " +
                "El producto no existe.",
                exception.getMessage());
        verify(this.productoRepository).findProductoById(1);
        verify(this.productoRepository,never()).findProductoByNombre("Mouse");
        verify(this.categoriaService,never()).findCategoriaById(1);
        verify(this.productoRepository,never()).save(any(Producto.class));
    }

    @Test
    void testUpdateProducto_DuplicateName(){
        ProductoRequest request = new ProductoRequest(1,"Mouse",10.0);
        ProductoResponse productoDB1 =
                new ProductoResponse(1,1,"Generic Mouse",
                        9.0,LocalDate.now());
        ProductoResponse productoDB2 =
                new ProductoResponse(2,1,"Mouse",
                        9.0,LocalDate.now());

        when(this.productoRepository.findProductoById(1))
                .thenReturn(Optional.of(productoDB1));
        when(this.productoRepository.findProductoByNombre(request.getNombre()))
                .thenReturn(Optional.of(productoDB2));

        InvalidOperationException eception = assertThrows(
                InvalidOperationException.class,
                ()->{this.productoService.updateProducto(1,request);}
        );
        verify(this.productoRepository).findProductoById(1);
        verify(this.productoRepository).findProductoByNombre("Mouse");
        verify(categoriaService, never()).findCategoriaById(1);
        verify(this.productoRepository,never()).save(any(Producto.class));
    }

    @Test
    void testUpdateProducto_CategoriaNotExists(){
        ProductoRequest request = new ProductoRequest(1,"Mouse",10.0);
        ProductoResponse productoDB = new ProductoResponse(1,1,"Mouse",
                9.0,LocalDate.now());

        when(this.productoRepository.findProductoById(1))
                .thenReturn(Optional.of(productoDB));
        when(this.productoRepository.findProductoByNombre(request.getNombre()))
                .thenReturn(Optional.of(productoDB));
        when(this.categoriaService.findCategoriaById(request.getCategoriaId()))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                ()->{this.productoService.updateProducto(1,request);}
        );

        assertEquals("No Existe la Categoria Indicada " +
                "no se puede modificar el producto.",exception.getMessage());
        verify(this.productoRepository).findProductoById(1);
        verify(this.productoRepository).findProductoByNombre("Mouse");
        verify(this.categoriaService).findCategoriaById(1);
        verify(this.productoRepository,never()).save(any(Producto.class));
    }

    @Test
    void testDeleteProducto_ProductoDelete(){
        ProductoResponse productoDB = new ProductoResponse(
                1,1,"Mouse",10.0,LocalDate.now() );
        when(this.productoRepository.findProductoById(1))
                .thenReturn(Optional.of(productoDB));

        boolean result = this.productoService.deleteProducto(1);

        assertTrue(result);
        verify(this.productoRepository).findProductoById(1);
        verify(this.productoRepository).deleteById(1);
    }

    @Test
    void testDeleteProducto_NotFoud(){
        when(this.productoRepository.findProductoById(1))
                .thenReturn(Optional.empty());

        boolean result = this.productoService.deleteProducto(1);

        assertFalse(result);
        verify(this.productoRepository).findProductoById(1);
        verify(this.productoRepository,never()).deleteById(1);
    }


}
