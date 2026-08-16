package com.laboratorio.springboot16.repository;

import com.laboratorio.springboot16.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}
