package com.saulo.vendasapi.models.repository.projections;

import java.math.BigDecimal;

//Spring Projection Interface

public interface VendasPorMes {
	Integer getMes();
	BigDecimal getValor();
}
