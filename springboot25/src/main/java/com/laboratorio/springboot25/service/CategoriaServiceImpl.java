package com.laboratorio.springboot25.service;

import com.laboratorio.springboot25.dto.CategoriaRequest;
import com.laboratorio.springboot25.dto.CategoriaResponse;
import com.laboratorio.springboot25.model.Categoria;
import com.laboratorio.springboot25.repository.CategoriaRepository;
import com.laboratorio.springboot25.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    @Override
    public Optional<CategoriaResponse> findCategoriaById(Integer id) {
        return this.categoriaRepository.findCategoriaById(id);
    }
    @Override
    public Optional<CategoriaResponse> findCategoriaByNombre(String nombre) {
        return this.categoriaRepository.findCategoriaByNombre(nombre);
    }
    @Override
    public List<CategoriaResponse> findAllOrderByNombreAsc() {
        return this.categoriaRepository.findAllOrderByNombreAsc();
    }
    @Override
    public List<CategoriaResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre) {
        return this.categoriaRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(nombre);
    }
    @Override
    public CategoriaResponse createCategoria(CategoriaRequest request) {
        Optional<CategoriaResponse> categoriaDB =
                this.categoriaRepository.findCategoriaByNombre(request.getNombre());
        if (categoriaDB.isPresent()) {
            return categoriaDB.get();
        }
        Categoria categoria = new Categoria(request);
        Categoria categoriaNueva = this.categoriaRepository.save(categoria);
        return new CategoriaResponse(categoriaNueva);
    }
}
