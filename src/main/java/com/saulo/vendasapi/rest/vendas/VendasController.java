package com.saulo.vendasapi.rest.vendas;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saulo.vendasapi.models.Venda;
import com.saulo.vendasapi.models.repository.ItemVendaRepository;
import com.saulo.vendasapi.models.repository.VendaRepository;
import com.saulo.vendasapi.service.RelatorioVendasService;
import com.saulo.vendasapi.util.DateUtils;

@RestController
@RequestMapping("/api/vendas")
@CrossOrigin("*")
public class VendasController {
	
	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private ItemVendaRepository itemVendaRepository;
	
	@Autowired
	private RelatorioVendasService relatorioVendasService;
	
	@PostMapping
	@Transactional
	public void realizarVenda(@RequestBody Venda venda) {	
		vendaRepository.save(venda);
		venda.getItens().stream().forEach(iv -> iv.setVenda(venda));
		itemVendaRepository.saveAll(venda.getItens());
	}
	
	@GetMapping("/relatorio-vendas")
	public ResponseEntity<byte[]> relatorioVendas(
		@RequestParam(value = "id", required = false, defaultValue = "") Long id,
		@RequestParam(value = "inicio", required = false, defaultValue = "") String inicio,
		@RequestParam(value = "fim", required = false, defaultValue = "") String fim
	) {
		
		Date dataInicio = DateUtils.fromString(inicio);
		Date dataFim = DateUtils.fromString(fim, true);
		
		if(dataInicio == null) {
			dataInicio = DateUtils.DATA_INICIO_PADRAO;
		}
		
		if(dataFim == null) {
			dataFim = DateUtils.hoje(true);
		}
		
		byte[] relatorioGerado = relatorioVendasService.gerarRelatorio(id, dataInicio, dataFim);
		
		/*
		System.out.println("<-------------------------------------------------------------------------------->");
		System.out.println();
		System.out.println("id: " + id + ", inicio: " + dataInicio + ", fim: " + dataFim);
		System.out.println();
		System.out.println("<-------------------------------------------------------------------------------->");
		*/
		
		HttpHeaders headers = new HttpHeaders();
		String filename = "relatorio-vendas.pdf";
		
		// inline; filename="relatorio-vendas.pdf"
		headers.setContentDispositionFormData("inline; filename=\"" +filename+ "\"", filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		var responseEntity = new ResponseEntity<>(relatorioGerado, headers, HttpStatus.OK);
		
		return responseEntity;
	}
}
