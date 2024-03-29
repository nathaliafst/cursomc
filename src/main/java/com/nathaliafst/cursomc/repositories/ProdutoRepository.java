package com.nathaliafst.cursomc.repositories;

import com.nathaliafst.cursomc.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository <Produto, Integer> {

}
