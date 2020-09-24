package com.llac.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.llac.entidades.Cupom;

public interface CupomRepositorio extends JpaRepository<Cupom, Long> {

	@Query(value = "SELECT * FROM tb_cupom WHERE LOWER(codigo) LIKE LOWER(concat('%', ?1, '%')) ORDER BY codigo", nativeQuery = true)
	public Page<Cupom> listarPorKeyword(String keyword, Pageable pageable);
}
