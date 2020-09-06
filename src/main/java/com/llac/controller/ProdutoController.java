package com.llac.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.llac.entidades.Produto;
import com.llac.service.ProdutoService;

@Controller
public class ProdutoController {
	
	@Autowired
	private ProdutoService service;
	
	private List<Produto> produtos = new ArrayList<>();
	
	@GetMapping("/cadastroProduto")
	public String cadastroProduto(Model model) {
		
		produtos = listarProdutos();
		
		model.addAttribute("produto", new Produto());
		model.addAttribute("produtos", produtos);
		
		return "cadastroProduto";
	}

	@PostMapping("/inserirProduto")
	public String inserirProduto(@Valid Produto produto, BindingResult result){
		
		if(result.hasFieldErrors()) {
			return "cadastroProduto";
		}
		if(produto.getId() == null) {
			service.inserir(produto);
		} else {
			atualizarProduto(produto, result);
		}
		return "index";
	}
	
	@PostMapping("/{id}/deletarProduto")
	public String deletar(@PathVariable("id") Long id) {
		
		Optional<Produto> p = service.buscarPorId(id);
		Produto produto = p.get();
		
		if(produto != null) {
			service.deletar(produto);
		}
		
		return "index";
	}
	
	@PutMapping("/{id}/atualizarProduto")
	public String atualizarProduto(@Valid Produto produto, BindingResult result) {
		
		if(result.hasFieldErrors()) {
			return "cadastroProduto";
		}
		
		Optional<Produto> opt = service.buscarPorId(produto.getId());
		Produto p = opt.get();
		
		if(p != null) {
			p.setDescricao(produto.getDescricao());
			p.setPreco(produto.getPreco());
			service.inserir(p);
		}
		return "index";
	}

	public List<Produto> listarProdutos(){
		return service.listar();
	}
	
}
