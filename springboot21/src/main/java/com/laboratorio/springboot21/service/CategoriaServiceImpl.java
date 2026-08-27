package com.laboratorio.springboot21.service;

import com.laboratorio.springboot21.dto.CategoriaRequest;
import com.laboratorio.springboot21.dto.CategoriaResponse;
import com.laboratorio.springboot21.dto.ProductoResponse;
import com.laboratorio.springboot21.exception.InvalidOperationException;
import com.laboratorio.springboot21.exception.ResourceNotFoundException;
import com.laboratorio.springboot21.model.Categoria;
import com.laboratorio.springboot21.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoService productoService;


    @Override
    public Optional<CategoriaResponse> findCategoriaById(Integer id) {
        return  this.categoriaRepository.findCategoriaById(id);
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
        Optional<CategoriaResponse> categoriaDB=
                this.categoriaRepository.findCategoriaByNombre(request.getNombre());
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
                this.categoriaRepository.findCategoriaById(id);
        if(categoriaDB.isEmpty()){
            throw new ResourceNotFoundException("No se puede efectuarla modificacion, " +
                    "la Categoria no existe");
        }
        Optional<CategoriaResponse> otraCategoria =
                this.categoriaRepository.findCategoriaByNombre(request.getNombre());
        if(otraCategoria.isPresent() &&
                !otraCategoria.get().getId().equals(otraCategoria.get().getId())){
            throw new InvalidOperationException("No se puede efectuar la modificación, "+
                    "el nombre de la categoria ya existe");
        }
        Categoria categoria = new Categoria(id, request.getNombre());
        Categoria categoriaModificada = this.categoriaRepository.save(categoria);
        return new CategoriaResponse(categoriaModificada);
    }

    @Override
    public boolean deleteCategoria(Integer id) {
        Optional<CategoriaResponse> categoriaDB =
                this.findCategoriaById(id);
        if(categoriaDB.isEmpty()){
            return false;
        }
        List<ProductoResponse> productos =
                this.productoService.findByCategoriaIdOrderByNombreAsc(id);
        if(productos.isEmpty()){
            throw new InvalidOperationException("No se puede eliminar la categoria, " +
                    "la categoria tiene productos asociados");
        }
        this.categoriaRepository.deleteById(id);
        return true;
    }


}
