package com.laboratorio.springboot26.service;

import com.laboratorio.springboot26.dto.CategoriaRequest;
import com.laboratorio.springboot26.dto.CategoriaResponse;
import com.laboratorio.springboot26.model.Categoria;
import com.laboratorio.springboot26.repository.CategoriaRepository;
import com.laboratorio.springboot26.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService{
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
    public List<CategoriaResponse> findAllCategoria() {
        Sort sort = Sort.by("nombre").ascending();
        return this.categoriaRepository.findAllCategoria(sort);
    }
    @Override
    public List<CategoriaResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String infix) {
        Sort.Order order = Sort.Order.by("nombre")
                .with(Sort.Direction.ASC)
                .ignoreCase();
        Sort sort = Sort.by(order);
        return this.categoriaRepository.findByNombreContainingIgnoreCase(infix,sort);
    }
    @Override
    public CategoriaResponse createCategoria(CategoriaRequest request) {
        Optional<CategoriaResponse> categoriaDB =
                this.categoriaRepository.findCategoriaByNombre(request.getNombre());
        if(categoriaDB.isPresent()){
            return categoriaDB.get();
        }
        Categoria categoria = new Categoria();
        Categoria categoriaNueva = this.categoriaRepository.save(categoria);
        return new CategoriaResponse(categoriaNueva);
    }

}
