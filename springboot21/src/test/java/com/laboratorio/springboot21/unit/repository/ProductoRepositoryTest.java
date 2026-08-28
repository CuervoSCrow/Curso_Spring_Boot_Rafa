package com.laboratorio.springboot21.unit.repository;

import com.laboratorio.springboot21.dto.ProductoResponse;
import com.laboratorio.springboot21.repository.ProductoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class ProductoRepositoryTest {

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    public void findProductoyByIdTest() {
        Integer id = 4;
        ProductoResponse response =
                productoRepository.findProductoById(id)
                        .get();
        Assertions.assertEquals(id,response.getCodigo());
        Assertions.assertEquals(2,response.getCategoriaId());

   }

   @Test
    public void findProductoByNombreTest(){
        String nombre = "Producto 7";
        ProductoResponse response =
                productoRepository.findProductoByNombre(nombre)
                        .get();
        Assertions.assertEquals(nombre,response.getNombre());
        Assertions.assertEquals(3,response.getCategoriaId());
   }

   @Test
   void findAllOrderByNombreAscTest(){
        List<ProductoResponse> productos =
                productoRepository.findAllOrderByNombreAsc();
        Assertions.assertEquals(9,productos.size());
   }

   @Test
   void findByNombreContainingIgnoreCaseOrderByNombreAscTest(){
        String infix="oDUc";
        List<ProductoResponse> productos=
                productoRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        Assertions.assertEquals(9,productos.size());
   }

   @Test
    void findByCategoriaIdOrderByNombreAscTest(){
        Integer categoriaId = 3;
        List<ProductoResponse> productos =
                productoRepository.findByCategoriaIdOrderByNombreAsc(categoriaId);

        Assertions.assertEquals(3,productos.size());
   }
}
