package com.laboratorio.springboot20.service;

import com.laboratorio.springboot20.dto.CategoriaRequest;
import com.laboratorio.springboot20.dto.CategoriaResponse;
import com.laboratorio.springboot20.exception.InvalidOperationException;
import com.laboratorio.springboot20.exception.ResourceNotFound;
import com.laboratorio.springboot20.model.Categoria;
import com.laboratorio.springboot20.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService{

    private final CategoriaRepository categoriaRepository;

    @Override
    public Optional<CategoriaResponse> findCategoriaResponseById(Integer id) {
        return this.categoriaRepository.findCategoriaResponseById(id);
    }

    @Override
    public Optional<CategoriaResponse> findCategoriaResponseByNombre(String nombre) {
        return this.categoriaRepository.findCategoriaResponseByNombre(nombre);
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
                this.categoriaRepository.findCategoriaResponseByNombre(request.getNombre());
        if(categoriaDB.isPresent()){
            return categoriaDB.get();
        }
        Categoria categoria = new Categoria(request);
        Categoria categoriaNueva = this.categoriaRepository.save(categoria);
        return new CategoriaResponse(categoriaNueva);
    }

    @Override
    public CategoriaResponse updateCategoria(Integer id, CategoriaRequest request) {
        Optional<CategoriaResponse> categoriaDB =
                this.categoriaRepository.findCategoriaResponseById(id);
        if(categoriaDB.isEmpty()){
            throw new ResourceNotFound("No se puede efectuar la modificación, " +
                    "la categoria no existe");
        }
        Optional<CategoriaResponse> otraCategoria =
                this.categoriaRepository.findCategoriaResponseByNombre(request.getNombre());
        if(otraCategoria.isPresent() &&
                !otraCategoria.get().getId().equals(otraCategoria.get().getId())){
            throw new InvalidOperationException("No se puede efectuar la modificación, " +
                    "el nombre de la categoria ya existe");
        }
        Categoria categoria = new Categoria(id,request.getNombre());
        Categoria categoriaModificada = this.categoriaRepository.save(categoria);
        return new CategoriaResponse(categoriaModificada);
    }

    @Override
    public boolean deleteCategoria(Integer id) {
        return false;
    }
}
