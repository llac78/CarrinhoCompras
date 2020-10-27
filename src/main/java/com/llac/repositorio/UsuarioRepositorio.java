package com.llac.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.llac.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
	
	@Query(value = "SELECT * FROM tb_usuario WHERE LOWER(login) LIKE LOWER(concat('%', ?1, '%')) ORDER BY login", nativeQuery = true)
	public Page<Usuario> listarPorKeyword(String keyword, Pageable pageable);
	
}
