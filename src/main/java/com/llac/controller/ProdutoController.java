package com.llac.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.llac.entidades.Produto;
import com.llac.paginacao.PageModel;
import com.llac.service.ProdutoService;

@Controller
public class ProdutoController {

	@Autowired
	private ProdutoService service;

	@Autowired
	private PageModel pageModel;

	@RequestMapping(path = { "/cadastroProduto", "/cadastroProduto/{id}" })
	public String salvarProdutoPorId(Model model, @PathVariable("id") Long id) {
		
		if (id != null) {
			Optional<Produto> opt = service.buscarPorId(id);
			Produto p = opt.get();
			model.addAttribute("produto", p);
		} else {
			model.addAttribute("produto", new Produto());
		}
		return "cadastroProduto";
	}

	@PostMapping("/criarProduto")
	public String criarOuAtualizarProduto(@Valid Produto produto, BindingResult result) {
		
		if (result.hasFieldErrors()) {
			return "cadastroProduto";
		}
		service.criarOuAtualizarProduto(produto);
		
		return "redirect:/";
	}

	@GetMapping("/cadastroProduto")
	public String cadastroProduto(Model model) {
		
		model.addAttribute("produto", new Produto());

		return "cadastroProduto";
	}

	@PostMapping("/{id}/deletarProduto")
	public String deletar(@PathVariable("id") Long id) {

		Optional<Produto> p = service.buscarPorId(id);
		Produto produto = p.get();

		if (produto != null) {
			service.deletar(produto);
		}
		return "index";
	}

	@GetMapping("/listaProdutos")
	public String listaProdutos(Model model) {
		pageModel.setSIZE(8);
		pageModel.initPageAndSize();
		model.addAttribute("produto", new Produto());
		model.addAttribute("listaProdutos", service.listar(PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE(), Sort.by("descricao"))));

		return "listaProdutos";
	}

}
