package com.nathaliafst.cursomc.services;

import com.nathaliafst.cursomc.domain.Categoria;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Cliente insert(Cliente cliente) {
        cliente.setId(null);
        cliente.setSenha(bCryptPasswordEncoder.encode(cliente.getSenha()));
        return clienteRepository.save(cliente);
    }

    public Cliente find(Integer id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
    return cliente.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public Cliente update(Cliente cliente) {
        Cliente newCliente  = find(cliente.getId());
        updateData(newCliente, cliente);
        return clienteRepository.save(cliente);
    }

    public void delete(Integer id) {
        find(id);
        try{
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir o cliente que realizou pedidos.");
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

    private void updateData(Cliente newCliente, Cliente cliente) {
        newCliente.setNome(cliente.getNome());
        newCliente.setEmail(cliente.getEmail());
    }


}
