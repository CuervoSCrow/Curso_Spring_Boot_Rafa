package com.laboratorio.springboot19.repository;

import com.laboratorio.springboot19.dto.ProductoResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class ProductoRepositoryTest {

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    void findProductoByIdTest(){
        Integer id = 4;

        ProductoResponse response = this.productoRepository.findProductoById(id).get();

        Assertions.assertEquals(id,response.getCodigo());
        Assertions.assertEquals(2,response.getCategoriaId());
    }
}
