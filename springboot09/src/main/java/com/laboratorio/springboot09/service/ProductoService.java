package com.laboratorio.springboot09.service;

import com.laboratorio.springboot09.modelo.Producto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    Optional<Producto> findById(Integer codigo);
    List<Producto> findAll();
    Producto create(Producto producto);
    Optional<Producto> update(Integer id, Producto producto);
    boolean delete(Integer id);
}
