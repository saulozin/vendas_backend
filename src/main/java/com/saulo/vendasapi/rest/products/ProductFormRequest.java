package com.saulo.vendasapi.rest.products;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.saulo.vendasapi.models.Product;

public class ProductFormRequest {
	
	private Long id;
	private String descricao;
	private String nome;
	private BigDecimal preco;
	private String sku;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Manaus")
	private Instant dtCadastro;
	
	public ProductFormRequest() {
	}

	public ProductFormRequest(String descricao, String nome, BigDecimal preco, String sku) {
		super();
		this.descricao = descricao;
		this.nome = nome;
		this.preco = preco;
		this.sku = sku;
	}
	
	public ProductFormRequest(Long id, String descricao, String nome, 
			BigDecimal preco, String sku, Instant dtCadastro) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.nome = nome;
		this.preco = preco;
		this.sku = sku;
		this.dtCadastro = dtCadastro;
	}

	public Product toModel() {
		return new Product(id, nome, descricao, preco, sku);
	}
	
	public static ProductFormRequest fromModel(Product produto) {
		return new ProductFormRequest(
				produto.getId(), produto.getNome(), produto.getDescricao(), 
				produto.getPreco(), produto.getSku(), produto.getDtCadastro()
		);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Instant getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Instant dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductFormRequest other = (ProductFormRequest) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "ProductFormRequest [id=" + id + ", descricao=" + descricao + ", nome=" + nome + ", preco=" + preco
				+ ", sku=" + sku + "]";
	}

}
