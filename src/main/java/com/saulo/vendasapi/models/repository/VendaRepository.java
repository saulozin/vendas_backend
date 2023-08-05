package com.saulo.vendasapi.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.saulo.vendasapi.models.Venda;
import com.saulo.vendasapi.models.repository.projections.VendasPorMes;

public interface VendaRepository extends JpaRepository<Venda, Long> {
	
	@Query(value = 
			  " select "
			+ "	extract ( month from v.data_venda ) as mes, "
			+ "	sum(v.total) as valor "
			+ " from tb_venda as v "
			+ " where extract ( year from v.data_venda ) = :ano "
			+ " group by extract ( month from v.data_venda ) "
			+ " order by extract ( month from v.data_venda )",
			nativeQuery = true)
	List<VendasPorMes> obterSomatoriaVendasPorMes(@Param("ano") Integer ano);

}
