package com.saulo.vendasapi.rest.clientes;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saulo.vendasapi.models.Cliente;
import com.saulo.vendasapi.models.repository.ClienteRepository;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin("*")
public class ClienteController {

	@Autowired
	public ClienteRepository repository;

	@GetMapping
	public Page<ClienteFormRequest> getLista(
		@RequestParam("nome") String nome,
		@RequestParam("cpf") String cpf,
		Pageable pageable
	) {
		return repository
				.buscarPorNomeECpf("%"+nome+"%", "%"+cpf+"%", pageable)
				.map(ClienteFormRequest::fromModel);
	}

	@GetMapping("{id}")
	public ResponseEntity<ClienteFormRequest> getById(@PathVariable Long id) {
		
		return repository.findById(id)
				.map(ClienteFormRequest::fromModel)
				.map(ClienteFR -> ResponseEntity.ok(ClienteFR))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<ClienteFormRequest> salvar(@RequestBody ClienteFormRequest request) {
		Cliente cliente = request.toModel();
		repository.save(cliente);
		// System.out.println(cliente);
		return ResponseEntity.ok(ClienteFormRequest.fromModel(cliente));
	}

	// url: api/clientes/id_valor
	@PutMapping("{id}")
	public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody ClienteFormRequest cliente) {
		Optional<Cliente> clienteExistente = repository.findById(id);
		
		if (clienteExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Cliente entidade = cliente.toModel();
		entidade.setId(id);
		repository.save(entidade);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Object> deletar(@PathVariable Long id) {
		
		return repository
				.findById(id)
				.map(cliente -> {
					repository.delete(cliente);
					return ResponseEntity.noContent().build();
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

}
