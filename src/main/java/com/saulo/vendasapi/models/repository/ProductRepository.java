package com.saulo.vendasapi.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saulo.vendasapi.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
