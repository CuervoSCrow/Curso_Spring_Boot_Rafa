package com.laboratorio.springboot24.unit.repository;

import com.laboratorio.springboot24.dto.ProductoResponse;
import com.laboratorio.springboot24.repository.ProductoRepository;
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
    private ProductoRepository productoRespository;

//    <-------------- Busquedas -------------->
    @Test
    public void testFindProductoById() {
        Integer id = 4;
        ProductoResponse response =
                productoRespository.findProductoById(id).get();
        assertEquals(id, response.getCodigo());
        assertEquals("Producto 4", response.getNombre());
        assertEquals(2, response.getCategoriaId());
    }
    @Test
    public void testFindProductoByNombre(){
        String nombre = "Producto 7";
        ProductoResponse response =
                productoRespository.findProductoByNombre(nombre).get();
        assertEquals(nombre, response.getNombre());
        assertEquals(3,response.getCategoriaId());
    }
    @Test
    public void testFindAllOrderByNombreAsc(){
        List<ProductoResponse> productos =
                this.productoRespository.findAllOrderByNombreAsc();
        assertEquals(9, productos.size());
    }
    @Test
    public void testFindByNombreContainingIgnoreCaseOrderByNombreAsc(){
        String infix = "OdUc";
        List<ProductoResponse> productos =
                this.productoRespository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        assertEquals(9, productos.size());
    }
    @Test
    public void testFindByCategoriaIdOrderByNOmbreAsc(){
        Integer categoriaId = 3;
        List<ProductoResponse> productos =
                this.productoRespository.findByCategoriaIdOrderByNombreAsc(categoriaId);
        assertEquals(3, productos.size());
    }
    @Test
    public void testCountByCategoriaId(){
        Integer categoriaId = 3;
        long count = this.productoRespository.countByCategoriaId(categoriaId);
        assertEquals(3, count);
    }
}
