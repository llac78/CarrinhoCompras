package com.llac.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	public Page<Produto> listar(Pageable pageable, String keyword) {
	   if (keyword != null) {
	       return repositorio.buscarPorKeyword(keyword, pageable);
	   }
	   return repositorio.findAll(pageable);
	}

	public Optional<Produto> buscarPorId(Long id) {
		return repositorio.findById(id);
	}

	public void deletar(Produto produto) {
		repositorio.delete(produto);
	}

	public Produto criarOuAtualizarProduto(Produto p) {
		if (p.getId() == null) {
			p = repositorio.save(p);

			return p;
			
		} else {
			Optional<Produto> produto = repositorio.findById(p.getId());

			if (produto.isPresent()) {
				Produto novoProduto = produto.get();
				novoProduto.setDescricao(p.getDescricao());
				novoProduto.setPreco(p.getPreco());

				novoProduto = repositorio.save(novoProduto);

				return novoProduto;
			} else {
				p = repositorio.save(p);

				return p;
			}
		}
	}
	
}
