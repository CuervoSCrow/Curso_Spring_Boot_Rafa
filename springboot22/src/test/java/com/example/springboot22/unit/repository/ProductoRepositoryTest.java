package com.example.springboot22.unit.repository;

import com.example.springboot22.dto.ProductoResponse;
import com.example.springboot22.repository.ProductoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class ProductoRepositoryTest {
    @Autowired
    private ProductoRepository productoRepository;

    @Test
    public void findProductoByIdTest() {
        Integer id = 4;
        ProductoResponse productoResponse =
                productoRepository.findProductoById(id).get();

        assertEquals(id, productoResponse.getCodigo());
        assertEquals(2, productoResponse.getCategoriaId());
    }

    @Test
    public void findProductoByNombreTest(){
        String name="Producto 7";
        ProductoResponse productoResponse =
                productoRepository.findProductoByNombre(name).get();

        assertEquals(name, productoResponse.getNombre());
        assertEquals(3, productoResponse.getCategoriaId());
    }

    @Test
    public void findAllOrderByNombreAscTest(){
        List<ProductoResponse> productos =
            productoRepository.findAllOrderByNombreAsc();
        assertEquals(9, productos.size());
    }

    @Test
    public void findByNombreContainingIgnoreCaseOrderByNombreAscTest(){
        String infix="ODUc";
        List<ProductoResponse> productosDB =
                productoRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);

        assertEquals(9, productosDB.size());
    }

    @Test
    void findByCategoriaIdOrderByNombreAscTest(){
        Integer categoriaId = 3;
        List<ProductoResponse> productosDB =
                productoRepository.findByCategoriaIdOrderByNombreAsc(categoriaId);

        assertEquals(3, productosDB.size());
    }

    @Test
    void countByCategoriaIdTest(){
        Integer categoriaId = 3;
        Long count = productoRepository.countByCategoriaId(categoriaId);
        assertEquals(3, count);
    }

}
