package com.laboratorio.springboot17.util;

import com.laboratorio.springboot17.dto.ProductoDTO;
import com.laboratorio.springboot17.model.Producto;
import com.laboratorio.springboot17.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
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
        log.info("=========================== Consultas Derivadas ===========================");

//        Buscar Registro por nombre exacto
        log.info("=========================== Buscar Registro por nombre exacto");
        producto = this.productoRepository.findOneByNombre(nombre);
        if(producto.isEmpty()){
            log.info("No se encontro el producto con nombre: " + nombre);
        }else{
            log.info("Producto Encontrado: "+producto.get())    ;
        }

//      Buscar Registro por Nombre Ignorando Mayusculas y Minusculas
        log.info("=========================== Buscar Registro por Nombre Ignorando Mayusculas y Minusculas");
        producto = this.productoRepository.findOneByNombreIgnoreCase(nombre);
        if(producto.isEmpty()){
            log.info("No se encontro el producto con nombre: " + nombre);
        }else{
            log.info("Producto Encontrado: "+producto.get())    ;
        }

//      Buscar Lista de registros que contenga la variable nombre,
//      ignorando mayúsculas y minúsculas, Ordenando por nombre ascendente
        log.info("=========================== Buscar Lista de registros que contenga la variable nombre, ignorando mayúsculas y minúsculas, Ordenando por nombre ascendente");
        productos = this.productoRepository
                .findByNombreContainingIgnoreCaseOrderByNombreAsc(buscar);
        for(Producto p : productos){
            log.info("Producto: " + p.toString());
        }
//        =====================================================================

//        ========================= Consultas JPQL =========================
        log.info("=========================== Consultas JPQL ============================");

//        Busca una lista de registros que la categoria sea n y el nombre contenga la variable buscar
        log.info("===========================  Busca una lista de registros que la categoria sea n y el nombre contenga la variable buscar");
        result =1;
        buscar="ble";
        productos = this.productoRepository.findByCategoriaAndNombre(result,buscar);
        for(Producto p : productos){
            log.info("Producto: " + p.toString());
        }

//        Actualiza la categoria de un producto
        log.info("=========================== Actualiza la categoria de un producto");
        result = this.productoRepository.updateCategoriaProductos(1, 2);
        log.info("Registros actualizados: " + result);
//        =====================================================================

//        Elimina Productos por categoriaId
        log.info("=========================== Elimina Productos por categoriaId");
        result = this.productoRepository.deleteByProductosByCategoriaId(2);
        log.info("Registros eliminados: " + result);
//        =====================================================================

//      Insertamos 4 registros
        insertarRegistros();

//        =====================================================================

//       ========================== Consultas Nativas =========================
        log.info("=========================== Consultas Nativas ===========================");

//        Busqueda findByCategoriaAndNombreSQL ================================
        log.info("=========================== Busqueda findByCategoriaAndNombreSQL");
        Integer categoriaId = 1;
        nombre = "caBLe";
        productos=this.productoRepository.findByCategoriaAndNombre(categoriaId,nombre);
        for(Producto p : productos){
            log.info("====== Producto: " + p.toString());
        }
//        =====================================================================

//        Borrado deleteProductosByCategoriaSQL
        result = this.productoRepository.deleteByProductosByCategoriaIdSQL(1);
        log.info("Registros eliminados: " + result);

//       =====================================================================
//      Insertamos 4 registros

        insertarRegistros();

//        ====================== Proyecciones Personalizadas =========================
        log.info("============= Proyecciones Personalizadas ========================");

        log.info("************************************************");
        log.info("\nEjemplo de proyeccion personalizada por constructor DTO ************");
        log.info("************************************************");
        List<ProductoDTO> productosDTO = this.productoRepository.findListadoProductos();
        for(ProductoDTO p : productosDTO){
            log.info("====== ProductoDTO: " + p.toString());
        }
//       =====================================================================
    }
    public void insertarRegistros(){
        List<String[]> datos = Arrays.asList(
                new String[]{"Cable de Red", "10.0"},
                new String[]{"Monitor", "200.0"},
                new String[]{"Teclado", "100.0"},
                new String[]{"Cable de HDMI", "15.0"}
        );
        for (String[] d : datos) {
            Producto p = new Producto();
            p.setNombre(d[0]);
            p.setPrecio(Double.parseDouble(d[1]));
            p.setCategoriaId(1);
            p.setFechaIngreso(LocalDate.of(2026, 8, 10));
            productoRepository.save(p);
        }
        log.info("Se insertaron "+datos.size()+" registros");
    }
}
