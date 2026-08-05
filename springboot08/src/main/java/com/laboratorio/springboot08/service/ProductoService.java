package com.laboratorio.springboot08.service;

import com.laboratorio.springboot08.modelo.Producto;

import java.util.List;

public interface ProductoService {
    Producto findById(Integer id);
    List<Producto> findAll();
    Producto create(Producto producto);
    Producto update(Integer id, Producto producto);
    String delete(Integer id);
}
