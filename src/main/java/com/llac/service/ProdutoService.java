package com.llac.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.llac.entidades.Produto;
import com.llac.repositorio.ProdutoRepositorio;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepositorio repositorio;

	public Produto inserir(Produto produto) {
		return repositorio.save(produto);
	}
	
	public List<Produto> listarPorDescricao(){
		return repositorio.findAll(Sort.by(Sort.Direction.ASC, "descricao"));
	}
	
	public Optional<Produto> buscarPorId(Long id) {
		return repositorio.findById(id);
	}
	
	public void deletar(Produto p) {
		repositorio.delete(p);
	}
	
}
