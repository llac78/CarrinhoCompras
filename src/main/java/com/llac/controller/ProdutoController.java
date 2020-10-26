package com.llac.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.llac.entidades.Produto;
import com.llac.service.ProdutoService;

@Controller
public class ProdutoController {

	@Autowired
	private ProdutoService service;

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

    @GetMapping("/listaProdutos/{page}")
    public ModelAndView listarProdutosPaginacao(@PathVariable("page") int page, @Param("keyword") String keyword) {
	   
        ModelAndView modelAndView = new ModelAndView("listaProdutos");
        Pageable pageable = PageRequest.of(page - 1, 8, Sort.by("descricao"));
        
        Page<Produto> produtoPage = service.listar(pageable, keyword);
        List<Produto> listaProdutos = new ArrayList<>();
        
        listaProdutos = produtoPage.getContent();
        
        int totalPages = produtoPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("keyword", keyword);
        modelAndView.addObject("listaProdutos", listaProdutos);
        
        return modelAndView;
    }
    
	@PostMapping("/carrinho/{id}")
	public ModelAndView addCarrinho(@PathVariable("id") Long id) {
		
		
		List<Produto> produtosCarrinho = new ArrayList<>();

		ModelAndView mv = new ModelAndView("carrinho");
		Optional<Produto> p = service.buscarPorId(id);
		Produto produto = p.get();
		
		if (produto != null) {
			produtosCarrinho.add(produto);
			mv.addObject("produtosCarrinho", produtosCarrinho);
		}
		return mv;
	}

}
