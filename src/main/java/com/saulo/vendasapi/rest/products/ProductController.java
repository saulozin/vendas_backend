package com.saulo.vendasapi.rest.products;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saulo.vendasapi.models.Product;
import com.saulo.vendasapi.models.repository.ProductRepository;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin("*")
public class ProductController {
	
	@Autowired
	private ProductRepository repository;
	
	/*
	 * Metodo tradicional
	@GetMapping
	public List<ProductFormRequest> getLista() {
		return repository.findAll().stream().map(new Function<Product, ProductFormRequest>(){

			@Override
			public ProductFormRequest apply(Product t) {
				
				return ProductFormRequest.fromModel(t);
			}
			
		}).collect(Collectors.toList());
	}
	*/
	
	@GetMapping
	public List<ProductFormRequest> getLista() {
		return repository.findAll().stream()
				.map(produto -> ProductFormRequest.fromModel(produto)).collect(Collectors.toList());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ProductFormRequest> getById(@PathVariable Long id) {
		Optional<Product> produtoExistente = repository.findById(id);
		
		if(produtoExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		//(produto -> ProductFormRequest.fromModel(produto)) equivalente à (ProductFormRequest::fromModel)
		var produto = produtoExistente.map(ProductFormRequest::fromModel).get();
		
		return ResponseEntity.ok(produto);
	}
	
	@PostMapping
	public ProductFormRequest salvar(@RequestBody ProductFormRequest produto) {
		Product entidadeProduto = produto.toModel();
		repository.save(entidadeProduto);
		//System.out.println(entidadeProduto);
		return ProductFormRequest.fromModel(entidadeProduto);
	}
	
	//url: api/produtos/id_valor
	@PutMapping("{id}")
	public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody ProductFormRequest produto) {
		Optional<Product> produtoExistente = repository.findById(id);
		if(produtoExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Product entidade = produto.toModel();
		entidade.setId(id);
		repository.save(entidade);
		
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id){
		Optional<Product> produtoExistente = repository.findById(id);
		if(produtoExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		repository.delete(produtoExistente.get());
		
		return ResponseEntity.noContent().build();
	}

}
