package com.saulo.vendasapi.rest.dashboard;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saulo.vendasapi.models.repository.ClienteRepository;
import com.saulo.vendasapi.models.repository.ProductRepository;
import com.saulo.vendasapi.models.repository.VendaRepository;

@RestController
@RequestMapping("/api/dashboard")
public class DashBoardController {
	
	@Autowired
	private VendaRepository vendas;
	
	@Autowired
	private ClienteRepository clientes;
	
	@Autowired
	private ProductRepository produtos;
	
	@GetMapping
	public DashboardData getDashboardData() {
		Long vendasCount = vendas.count();
		Long clientesCount = clientes.count();
		Long produtosCount = produtos.count();
		
		Integer anoCorrente = LocalDate.now().getYear();
		var vendasPorMes = vendas.obterSomatoriaVendasPorMes(anoCorrente);
		
		return new DashboardData(produtosCount, clientesCount, vendasCount, vendasPorMes);
	}
}
