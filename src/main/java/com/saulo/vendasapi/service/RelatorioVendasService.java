package com.saulo.vendasapi.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

@Service
public class RelatorioVendasService {
	
	@Value("classpath:reports/relatorio-vendas.jrxml")
	private Resource relatorioVendasSource;
	
	@Value("classpath:reports/relatorio-vendas.jasper")
	private Resource relatorioVendasCompilado;
	
	@Autowired
	private DataSource dataSource;
	
	public byte[] gerarRelatorio(Long idCliente, Date dataInicio, Date dataFim) {
		
		//try with resources
		try (
			Connection connection = dataSource.getConnection(); //Abre a conexão com o banco de dados
		) {
			
			//JasperReport compiledReport = JasperCompileManager.compileReport(relatorioVendasSource.getInputStream());
			
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("ID_CLIENTE", idCliente);
			parametros.put("DATA_INICIO", dataInicio);
			parametros.put("DATA_FIM", dataFim);
			//JasperPrint print = JasperFillManager.fillReport(compiledReport, parametros, connection);
			
			//return JasperExportManager.exportReportToPdf(print);
			
			/*
			System.out.println("<-------------------------------------------------------------------------------->");
			System.out.println();
			System.out.println(parametros);
			System.out.println();
			System.out.println("<-------------------------------------------------------------------------------->");
			*/
			
			return JasperRunManager.runReportToPdf(relatorioVendasCompilado.getInputStream(), parametros, connection);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
