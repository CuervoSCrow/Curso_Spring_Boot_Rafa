package com.laboratorio.springboot25.unit.repository;

import com.laboratorio.springboot25.dto.ProductoResponse;
import com.laboratorio.springboot25.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
public class ProductoRepositoryTest{
    @Autowired
    private ProductoRepository productoRepository;
//    <------------------------Busquedas------------------------>
    @Test
    public void testFindProductoById() {
        Integer id=4;
        ProductoResponse response=
                productoRepository.findProductoById(id).get();
        assertEquals(id,response.getCodigo());
        assertEquals("Producto 4",response.getNombre());
        assertEquals(2,response.getCategoriaId());
    }
    @Test
    public void testFindProductoByNombre(){
        String nombre="Producto 7";
        ProductoResponse response=
                productoRepository.findProductoByNombre(nombre).get();
        assertEquals(nombre,response.getNombre());
        assertEquals(3,response.getCategoriaId());
    }
    @Test
    public void testFindAllOrderByNombreAsc(){
        List<ProductoResponse> productos =
            this.productoRepository.findAllOrderByNombreAsc();
        assertEquals(9,productos.size());
    }
    @Test
    public void testFindByNombreContainingIgnoreCaseOrderByNombreAsc(){
        String infix = "OdUc";
        List<ProductoResponse> productos =
                this.productoRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        assertEquals(9,productos.size());
    }
    @Test
    public void testFindByCategoriaIdOrderByNombreAsc(){
        Integer categoriaId = 2;
        List<ProductoResponse> productos =
                this.productoRepository.findByCategoriaIdOrderByNombreAsc(categoriaId);
        assertEquals(3,productos.size());
    }
    @Test
    public void testCountByCategoriaId(){
        Integer categoriaId = 3;
        long count = this.productoRepository.countByCategoriaId(categoriaId);
        assertEquals(3,count);
    }
}
