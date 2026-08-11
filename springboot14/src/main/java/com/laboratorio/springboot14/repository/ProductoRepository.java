package com.laboratorio.springboot14.repository;

import com.laboratorio.springboot14.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto,Integer> {
}
