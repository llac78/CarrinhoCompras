package com.llac.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.llac.entidades.Produto;
import com.llac.service.ProdutoService;

@Controller
public class MainController {

	@Autowired
	private ProdutoService service;

	List<Produto> listaProdutos = new ArrayList<>();

	@GetMapping("/")
	public ModelAndView abrirPaginaInicial() {

		ModelAndView mv = new ModelAndView("index");
		listaProdutos = service.listarTodos();
		
		mv.addObject("listaProdutos", listaProdutos);

		return mv;
	}
	
	@GetMapping("/{page}")
	public ModelAndView listarProdutosPaginacao(@PathVariable("page") int page, @Param("keyword") String keyword) {

		ModelAndView modelAndView = new ModelAndView("index");
		Pageable pageable = PageRequest.of(page - 1, listaProdutos.size());

		Page<Produto> produtoPage = service.listar(pageable, keyword);
		List<Produto> produtos = new ArrayList<>();

		produtos = produtoPage.getContent();

		modelAndView.addObject("keyword", keyword);
		modelAndView.addObject("listaProdutos", produtos);

		return modelAndView;
	}

}
