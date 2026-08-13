package com.laboratorio.springboot14.util;

import com.laboratorio.springboot14.modelo.Producto;
import com.laboratorio.springboot14.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
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

        log.info("Ejecutando Runner....");
        Optional<Producto> producto;

//        Buscar un registro por nombre exacto
        producto = productoRepository.findOneByNombre(nombre);
        if(producto.isEmpty()) {log.info("No encontré el producto");}
        else {log.info("Producto: {}", producto.get().toString());}

//      Buscar un registro por nombre ignorando mayusculas y minusculas
        producto = productoRepository.findOneByNombreIgnoreCase(nombre);
        if(producto.isEmpty()) {log.info("No encontré el producto");}
        else {log.info("Producto: {}", producto.get().toString());}

//
        List<Producto> productos = productoRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(buscar);
        for(Producto p : productos){
            log.info("Producto: {}", p.toString());
        }
    }


}
