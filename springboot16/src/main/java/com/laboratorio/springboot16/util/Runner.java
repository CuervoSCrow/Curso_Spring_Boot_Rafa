package com.laboratorio.springboot16.util;

import com.laboratorio.springboot16.model.Producto;
import com.laboratorio.springboot16.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class Runner implements CommandLineRunner {
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {
        String nombre = "cable de red";
        String buscar = "ble";

        log.info("Ejecutando Runner");
        Optional<Producto> productos;

//        Buscar un registro por nombre exacto
        productos = this.productoRepository.findOneByNombre(nombre);
        if(productos.isEmpty()){
            log.info("No se encontro el producto con nombre: " + nombre);
        } else {
            log.info("Producto encontrado: " + productos.get());
        }


    }
}
