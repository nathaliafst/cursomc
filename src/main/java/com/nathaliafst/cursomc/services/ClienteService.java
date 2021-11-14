package com.nathaliafst.cursomc.services;

import com.nathaliafst.cursomc.domain.Cliente;
import com.nathaliafst.cursomc.dto.ClienteDTO;
import com.nathaliafst.cursomc.repositories.ClienteRepository;
import com.nathaliafst.cursomc.services.exceptions.DataIntegrityException;
import com.nathaliafst.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

        public Cliente find(Integer id) {
            Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
        }

        public Cliente update(Cliente cliente) {
            find(cliente.getId());
            return clienteRepository.save(cliente);
        }

        public void delete(Integer id) {
            find(id);
            try{
                clienteRepository.deleteById(id);
            } catch (DataIntegrityViolationException e) {
                throw new DataIntegrityException("Não é possível excluir uma cliente que possui produtos.");
            }
        }

        public List<Cliente> findAll() {
            List<Cliente> clientes = clienteRepository.findAll();
            return clientes;
        }

        public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
            PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
            return clienteRepository.findAll(pageRequest);
        }

        public Cliente fromDTO(ClienteDTO clienteDTO){
            return new Cliente (clienteDTO.getId(), clienteDTO.getNome(), null, clienteDTO.getEmail(), clienteDTO.getSenha());
        }
}
