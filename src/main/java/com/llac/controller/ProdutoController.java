package com.llac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.llac.entidades.Produto;
import com.llac.service.ProdutoService;

@Controller
public class ProdutoController {
	
	@Autowired
	private ProdutoService service;
	
	@GetMapping(path = "/")
	public String index() {
		return "index";
	}
	
	@GetMapping(path = "/cadastroProduto")
	public String cadastroProduto( Model model) {
		
		model.addAttribute("produto", new Produto());
		
		return "cadastroProduto";
	}

	@PostMapping("/inserir")
	public String inserir(Produto produto){
		
		if(produto != null) {
			service.inserir(produto);
		}
		
		return "index";
	}

}
