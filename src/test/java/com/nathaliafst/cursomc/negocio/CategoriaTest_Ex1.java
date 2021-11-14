package com.nathaliafst.cursomc.negocio;

import com.nathaliafst.cursomc.domain.Categoria;
import com.nathaliafst.cursomc.resources.CategoriaResource;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;

public class CategoriaTest_Ex1 {

    public void testFindCategoria(){
        Categoria cat1 = new Categoria(null, "Software");
        Categoria cat2 = new Categoria(null, "Cloud");
        Categoria cat3 = new Categoria(null, "Software Web");

        List<Categoria> categoriaList = new ArrayList<>();
        categoriaList.add(cat1);
        categoriaList.add(cat2);
        categoriaList.add(cat3);

        CategoriaResource categoriaResource = new CategoriaResource();

        ResponseEntity<?> categoria = categoriaResource.find(1);
    }
}



