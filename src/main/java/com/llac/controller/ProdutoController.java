package com.llac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProdutoController {
	
//	@Autowired
//	private ProdutoService service;
	
	@GetMapping(path = "/")
	public String index() {
		return "index";
	}
	
	@GetMapping(path = "/cadastroProduto")
	public String cadastroProduto() {
		return "cadastroProduto";
	}
	
	

//	@PostMapping
//	public ResponseEntity<Produto> inserir(@RequestBody Produto produto){
//		
//		if(produto != null) {
//			service.inserir(produto);
//		}
//		
//		URI uri = ServletUriComponentsBuilder
//				.fromCurrentRequest()
//				.path("/{id}")
//				.buildAndExpand(produto.getId())
//				.toUri();
//		
//		return ResponseEntity.created(uri).body(produto);
//	}

}
