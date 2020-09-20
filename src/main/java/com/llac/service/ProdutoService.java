package com.llac.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

	public List<Produto> listarPorDescricao() {
		return repositorio.findAll(Sort.by(Sort.Direction.ASC, "descricao"));
	}

	public Object listar(PageRequest of) {
		return repositorio.findAll(of);
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
