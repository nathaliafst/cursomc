package com.nathaliafst.cursomc.resources;

import com.nathaliafst.cursomc.domain.Cliente;
import com.nathaliafst.cursomc.dto.ClienteDTO;
import com.nathaliafst.cursomc.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService clienteService;

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert (@Valid @RequestBody ClienteDTO clienteDTO) { //@RequestBody faz o json ser convertido para objeto java automaticamente
        Cliente cliente = clienteService.fromDTO(clienteDTO);
        cliente = clienteService.insert(cliente);
        URI uri  = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(clienteDTO.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    public ResponseEntity<Cliente> find (@PathVariable Integer id){
        Cliente cliente = clienteService.find(id);
        return ResponseEntity.ok().body(cliente);
    }

    @RequestMapping(value="/{id}", method= RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @PathVariable Integer id, @RequestBody ClienteDTO clienteDTO ){
        Cliente cliente = clienteService.fromDTO(clienteDTO);
        cliente.setId(id);
        cliente = clienteService.update(cliente);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<Cliente> clientes = clienteService.findAll();
        List<ClienteDTO> clienteDTOS = clientes.stream().map(obj -> new ClienteDTO(obj))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(clienteDTOS);
    }
    @RequestMapping(value="/page", method=RequestMethod.GET)
    public ResponseEntity<Page> findPage(
            @RequestParam (value="page", defaultValue="0") Integer page,
            @RequestParam (value="linesPerPage", defaultValue="24") Integer linesPerPage,
            @RequestParam (value="orderBy", defaultValue="nome") String orderBy,
            @RequestParam (value="direction", defaultValue="ASC") String direction) {
        Page<Cliente> cliente = clienteService.findPage(page, linesPerPage, orderBy, direction);
        Page<ClienteDTO> clienteDTOS = cliente.map(obj -> new ClienteDTO(obj));
        return ResponseEntity.ok().body(clienteDTOS);
    }
}
