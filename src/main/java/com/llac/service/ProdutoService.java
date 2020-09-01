package com.llac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public List<Produto> listar(){
		return repositorio.findAll();
	}
}
