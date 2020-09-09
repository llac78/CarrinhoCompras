package com.llac.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.llac.entidades.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {
	
}
