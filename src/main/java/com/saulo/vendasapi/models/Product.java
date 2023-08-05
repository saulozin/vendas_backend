package com.saulo.vendasapi.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_produto")
public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nome", length = 100)
	private String nome;
	
	@Column(name="descricao", length = 255)
	private String descricao;
	
	@Column(name="preco", precision = 16, scale = 2)
	private BigDecimal preco;
	
	@Column(name="sku")
	private String sku;
	
	@Column(name="dt_cadastro")
	private Instant dtCadastro;
	
	public Product() {
	}

	public Product(String nome, String descricao, BigDecimal preco, String sku) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.sku = sku;
	}

	public Product(Long id, String nome, String descricao, BigDecimal preco, String sku) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.sku = sku;
	}
	
	@PrePersist
	public void prePersist() {
		setDtCadastro(Instant.now());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", preco=" + preco + ", sku=" + sku
				+ "]";
	}
}
