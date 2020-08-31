package com.llac.controller;

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
	public String inserir(@Valid Produto produto, BindingResult result){
		
		if(result.hasFieldErrors()) {
			return "cadastroProduto";
		}
		
		if(produto != null) {
			service.inserir(produto);
		}
		
		return "index";
	}

}
