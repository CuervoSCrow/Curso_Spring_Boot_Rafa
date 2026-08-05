package com.laboratorio.springboot08.config;

import com.laboratorio.springboot08.service.ProductoService;
import com.laboratorio.springboot08.service.ProductoServiceImpl1;
import com.laboratorio.springboot08.service.ProductoServiceImpl2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class productoServiceConfig {
//    @Bean
//    public ProductoService productoService() {
////        Logica que permite decidir el bean inyectar
//        double aleatorio = Math.random();
//        System.out.println("Aleatorio: " + aleatorio);
//        if(aleatorio <=0.5){
//            System.out.println("Se Inyecta la implementación 1");
//            return new ProductoServiceImpl1();
//        }else{
//            System.out.println("Se Inyecta la implementacion 2");
//            return new ProductoServiceImpl2();
//        }
//    }
}
