package com.nathaliafst.cursomc.services;

import com.nathaliafst.cursomc.domain.Categoria;
import com.nathaliafst.cursomc.domain.Produto;
import com.nathaliafst.cursomc.repositories.CategoriaRepository;
import com.nathaliafst.cursomc.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public void instantiateTestDatabase() throws ParseException {
        Categoria cat1 = new Categoria(null, "Software");
        Categoria cat2 = new Categoria(null, "Cloud");
        Categoria cat3 = new Categoria(null, "Software Web");

        Produto p1 = new Produto(null, "Windows", 800.00);
        Produto p2 = new Produto(null, "AWS", 2000.00);
        Produto p3 = new Produto(null, "Uol", 200.00);

        cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
        cat2.getProdutos().addAll(Arrays.asList(p2,p3));
        cat3.getProdutos().addAll(Arrays.asList(p3));

        p1.getCategorias().addAll(Arrays.asList(cat1));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
        p3.getCategorias().addAll(Arrays.asList(cat2, cat3));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
        produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
    }
}
