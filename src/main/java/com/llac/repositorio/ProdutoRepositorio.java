package com.llac.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.llac.entidades.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {
	
	@Query(value = "SELECT * FROM tb_produto WHERE LOWER(descricao) LIKE LOWER(concat('%', ?1, '%')) ORDER BY descricao", nativeQuery = true)
	public Page<Produto> buscarPorKeyword(String keyword, Pageable pageable);
	
}
