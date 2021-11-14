package com.nathaliafst.cursomc;

import com.nathaliafst.cursomc.domain.Categoria;
import com.nathaliafst.cursomc.domain.Produto;
import com.nathaliafst.cursomc.repositories.CategoriaRepository;
import com.nathaliafst.cursomc.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication{

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

}
