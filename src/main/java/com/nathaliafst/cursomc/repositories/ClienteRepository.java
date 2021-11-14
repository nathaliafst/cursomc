package com.nathaliafst.cursomc.repositories;

import com.nathaliafst.cursomc.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository <Cliente, Integer>{

}
