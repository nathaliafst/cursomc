package com.nathaliafst.cursomc.repositories;

import com.nathaliafst.cursomc.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClienteRepository extends JpaRepository <Cliente, Integer>{

    //Diminui o locking
    @Transactional(readOnly=true)
    Cliente findByEmail(String email);

}
