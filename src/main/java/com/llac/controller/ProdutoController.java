package com.llac.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.llac.entidades.Produto;
import com.llac.service.ProdutoService;

@Controller
public class ProdutoController {
	
	@Autowired
	private ProdutoService service;
	
	@GetMapping(path = "/")
	public String index(Model model) {
		
		String nome = "Leo";
		model.addAttribute("nome", nome);
		
		return "index";
	}

	@PostMapping
	public ResponseEntity<Produto> inserir(@RequestBody Produto produto){
		
		service.inserir(produto);
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(produto.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(produto);
	}

}
