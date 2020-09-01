package com.llac.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.llac.entidades.Produto;
import com.llac.service.ProdutoService;

@Controller
public class ProdutoController {
	
	@Autowired
	private ProdutoService service;
	
	private List<Produto> produtos = new ArrayList<>();
	
	@GetMapping("/cadastroProduto")
	public String cadastroProduto( Model model) {
		
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
		if(produto != null) {
			service.inserir(produto);
		}
		return "index";
	}

	public List<Produto> listarProdutos(){
		return service.listar();
	}
	
}
