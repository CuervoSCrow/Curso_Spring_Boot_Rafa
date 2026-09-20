package com.laboratorio.springboot23.unit.repository;

import com.laboratorio.springboot23.dto.ProductoResponse;
import com.laboratorio.springboot23.repository.ProductoRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class ProductoRepositoryTest {
    @Autowired
    private ProductoRepository productoRepository;

    @Test
    void testFindProductoById() {
        Integer id = 4;
        ProductoResponse response =
                productoRepository.findProductoById(id).get();
        assertEquals(id,response.getCodigo());
        assertEquals("Producto 4",response.getNombre());
        assertEquals(2,response.getCategoriaId());
    }

    @Test
    void testFindProductoByNombre(){
        String name="Producto 7";
        ProductoResponse response =
                productoRepository.findProductoByNombre(name).get();
        assertEquals(name,response.getNombre());
        assertEquals(3,response.getCategoriaId());
    }

    @Test
    void testFindAllOrderNombreAsc(){
        List<ProductoResponse> productos =
                productoRepository.findAllOrderNombreAsc();
        assertEquals(9,productos.size());
    }

    @Test
    void testFindByNombreContainingIgnoreCaseOrderByNombreAsc(){
        String infix = "OdUc";
        List<ProductoResponse> productos =
                productoRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        assertEquals(9,productos.size());
    }

    @Test
    void testFindByCategoriaIdOrderByNombreAsc(){
        Integer categoriaId=3;
        List<ProductoResponse> productosDB =
                productoRepository.findByCategoriaIdOrderByNombreAsc(categoriaId);
        assertEquals(3,productosDB.size());
    }

    @Test
    void testCountByCategoriaId(){
        Integer categoriaId=3;
        long count = productoRepository.countByCategoriaId(categoriaId);
        assertEquals(3,count);
    }
}
