package com.saulo.vendasapi.models.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.saulo.vendasapi.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	@Query(" SELECT c FROM Cliente c WHERE UPPER(c.nome) LIKE UPPER(:nome) AND c.cpf LIKE :cpf ")
	Page<Cliente> buscarPorNomeECpf(
			@Param("nome") String nome, 
			@Param("cpf") String cpf, 
			Pageable pageable);
}
