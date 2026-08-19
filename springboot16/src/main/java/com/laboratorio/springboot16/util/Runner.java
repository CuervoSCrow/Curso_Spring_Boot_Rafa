package com.laboratorio.springboot16.util;

import com.laboratorio.springboot16.model.Producto;
import com.laboratorio.springboot16.repository.ProductoRepository;
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
        String nombre = "Cable de red";
        String buscar = "ble";
        int result;

        log.info("Ejecutando Runner");
        Optional<Producto> producto;
        List<Producto> productos;

//        Buscar un registro por nombre exacto
        producto = this.productoRepository.findOneByNombre(nombre);
        if(producto.isEmpty()){
            log.info("No se encontro el producto con nombre: " + nombre);
        } else {
            log.info("Producto encontrado: " + producto.get());
        }

//      Buscar un registro por nombre Ignorando mayúsculas y minúsculas
        producto = this.productoRepository.findOneByNombreIgnoreCase(nombre);
        if(producto.isEmpty()){
            log.info("No se encontro el producto con nombre: " + nombre);
        } else {
            log.info("Producto encontrado: " + producto.get());
        }

//       Buscar lista de registros que contenga la variable nombre,
//        ignorando mayúsculas y minúsculas, Ordenando por nombre ascendente
         productos =this.productoRepository
                        .findByNombreContainingIgnoreCaseOrderByNombreAsc(buscar);
        for(Producto p : productos){
            log.info("Producto: " + p.toString());
        }
/*
        int result = this.productoRepository.updateCategoriaProductos(2,3);
        log.info("Se han modificado {} registros " , result);

        result = productoRepository.deleteProductosByCategoria(2);
        log.info("Se han eliminado {} registros " , result);

        long result = this.productoRepository.deleteByCategoriaId(1);
        log.info("Se han eliminado: {} registros  ", result);
*/
//        int result = this.productoRepository.updateCategoriaProductosSQL(1,2);
//        log.info("Se han modificado {} registros " , result);//


//        Consultas Nativas


//      Busqueda findByCategoriaAndNombreSQL  =============================================
        Integer categoriaId = 3;
        nombre= "caBle";
        productos = this.productoRepository.findByCategoriaAndNombreSQL(categoriaId, nombre);
        for(Producto p : productos){

            log.info("====== Producto: " + p.toString());
        }

//      Borrado deleteProductosByCategoriaSQL  =============================================
        result = this.productoRepository.deleteProductosByCategoriaSQL(2);
        log.info("Se han eliminado {} registros " , result);
    }
}
