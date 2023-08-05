package com.saulo.vendasapi.rest.dashboard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.saulo.vendasapi.models.repository.projections.VendasPorMes;

public class DashboardData {
	private Long produtos;
	private Long clientes;
	private Long vendas;
	private List<VendasPorMes> vendasPorMes = new ArrayList<>();
	
	public DashboardData() {
		super();
	}

	public DashboardData(Long produtos, Long clientes, Long vendas, List<VendasPorMes> vendasPorMes) {
		super();
		this.produtos = produtos;
		this.clientes = clientes;
		this.vendas = vendas;
		this.vendasPorMes = vendasPorMes;
		this.preencherMesesFaltantes();
	}

	public Long getProdutos() {
		return produtos;
	}

	public void setProdutos(Long produtos) {
		this.produtos = produtos;
	}

	public Long getClientes() {
		return clientes;
	}

	public void setClientes(Long clientes) {
		this.clientes = clientes;
	}

	public Long getVendas() {
		return vendas;
	}

	public void setVendas(Long vendas) {
		this.vendas = vendas;
	}

	public List<VendasPorMes> getVendasPorMes() {
		return vendasPorMes;
	}

	public void setVendasPorMes(List<VendasPorMes> vendasPorMes) {
		this.vendasPorMes = vendasPorMes;
	}
	
	public void preencherMesesFaltantes() {
		Integer mesMaximo = getVendasPorMes().stream().mapToInt(VendasPorMes::getMes).max().getAsInt();
		
		//Criar um array de inteiros do mes minimo ao mes maximo
		List<Integer> listaMeses = IntStream.rangeClosed(1, mesMaximo).boxed().collect(Collectors.toList());
		List<Integer> mesesAdicionados = getVendasPorMes().stream().map(VendasPorMes::getMes).collect(Collectors.toList());
		
		listaMeses.stream().forEach(mes -> {
			if(!mesesAdicionados.contains(mes)) {
				VendasPorMes vendasPorMes = new VendasPorMes() {

					@Override
					public Integer getMes() {
						return mes;
					}

					@Override
					public BigDecimal getValor() {
						return BigDecimal.ZERO;
					}
					
				};
				
				getVendasPorMes().add(vendasPorMes);
			}
		});
		
		getVendasPorMes().sort( Comparator.comparing(VendasPorMes::getMes) );
	}

	@Override
	public String toString() {
		return "DashboardData [produtos=" + produtos + ", clientes=" + clientes + ", vendas=" + vendas + "]";
	}
	
}
