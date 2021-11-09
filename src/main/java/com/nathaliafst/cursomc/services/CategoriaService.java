package com.nathaliafst.cursomc.services;

import com.nathaliafst.cursomc.domain.Categoria;
import com.nathaliafst.cursomc.repositories.CategoriaRepository;
import com.nathaliafst.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired //Instancia automaticamente a classe
    private CategoriaRepository categoriaRepository;

    public Categoria find(Integer id) {
        Optional<Categoria> obj = categoriaRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }

}
