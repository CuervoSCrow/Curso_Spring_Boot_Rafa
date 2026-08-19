package com.laboratorio.springboot17.util;

import com.laboratorio.springboot17.model.Producto;
import com.laboratorio.springboot17.repository.ProductoRepository;
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
        String nombre = "Cable de Red";
        String buscar = "ble";
        int result;
        Optional<Producto> producto;
        List<Producto> productos;

//        ============================================================
        log.info("Ejecutando el Runner");
//        ============================================================

//        ========================= Consultas Derivadas =========================

//        Buscar Registro por nombre exacto
        producto = this.productoRepository.findOneByNombre(nombre);
        if(producto.isEmpty()){
            log.info("No se encontro el producto con nombre: " + nombre);
        }else{
            log.info("Producto Encontrado: "+producto.get())    ;
        }

//      Buscar Registro por Nombre Ignorando Mayusculas y Minusculas
        producto = this.productoRepository.findOneByNombreIgnoreCase(nombre);
        if(producto.isEmpty()){
            log.info("No se encontro el producto con nombre: " + nombre);
        }else{
            log.info("Producto Encontrado: "+producto.get())    ;
        }

//      Buscar Lista de registros que contenga la variable nombre,
//      ignorando mayúsculas y minúsculas, Ordenando por nombre ascendente
        productos = this.productoRepository
                .findByNombreContainingIgnoreCaseOrderByNombreAsc(buscar);
        for(Producto p : productos){
            log.info("Producto: " + p.toString());
        }


//        =====================================================================
    }
}
