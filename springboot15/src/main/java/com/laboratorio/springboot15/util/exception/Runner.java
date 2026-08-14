package com.laboratorio.springboot15.util.exception;

import com.laboratorio.springboot15.model.Producto;
import com.laboratorio.springboot15.repository.ProductoRepository;
import com.laboratorio.springboot15.service.ProductoService;
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

        log.info("Ejecutando Runner");
        Optional<Producto> producto;

//        Buscar un registro por nombre exacto
        producto = this.productoRepository.findOneByNombre(nombre);
        if(producto.isEmpty()){
            log.info("No encontré el producto: "+nombre);
        }else{
            log.info("Producto: {}",producto.get().toString());
        }

//        Buscar un registro por nombre Ignorando mayusculas y minusculas
        producto = this.productoRepository.findOneByNombreIgnoreCase(nombre);
        if(producto.isEmpty()){
            log.info("No encontré el producto: "+nombre);
        }else{
            log.info("Producto: {}",producto.get().toString());
        }

//        Buscar lista de registros que contenga la variable nombre,
//        ignorando Mayusculas y minusculas Ordenando por nombre ascendente
        List<Producto> productos = this.productoRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(buscar);
        for(Producto p : productos){
            log.info("Producto: {}",p.toString());
        }

    }
}
