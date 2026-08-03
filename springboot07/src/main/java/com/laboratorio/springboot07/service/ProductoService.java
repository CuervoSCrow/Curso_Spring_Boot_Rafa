package com.laboratorio.springboot07.service;

import com.laboratorio.springboot07.modelo.Producto;

import java.util.List;

public interface ProductoService {
    Producto findById(Integer id);
    List<Producto> findAll();
    Producto create(Producto producto);
    Producto update(Integer id,Producto producto);
    String delete(Integer id);
}
