package com.llac.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.llac.entidades.Produto;
import com.llac.paginacao.PageModel;
import com.llac.service.ProdutoService;

@Controller
public class ProdutoController {
	
	@Autowired
	private ProdutoService service;
	
	@Autowired
    private PageModel pageModel;
	
	private List<Produto> produtos = new ArrayList<>();
	
	@GetMapping("/cadastroProduto")
	public String cadastroProduto(Model model, Long id) {
		
//		produtos = listarProdutos();
		
		if(id != null) {
			Optional<Produto> opt = service.buscarPorId(id);
			Produto p = opt.get();
			model.addAttribute("produto", p);
		}
		
		model.addAttribute("produto", new Produto());
		
		return "cadastroProduto";
	}
	
	@PostMapping("/inserirProduto")
	public ModelAndView inserirProduto(@Valid Produto produto, BindingResult result){

		ModelAndView model = new ModelAndView("cadastroProduto");
		
		if(result.hasFieldErrors()) {
			produtos = listarProdutos();
			model.addObject("produtos", produtos);
			
			return model;
		}
		if(produto.getId() == null) {
			service.inserir(produto);
		} else {
			atualizarProduto(produto, result);
		}
		ModelAndView index = new ModelAndView("index");

		return index;
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
	
	@PostMapping("/{id}/cadastroProduto")
	public String preencherForm(@PathVariable("id") Long id, Model model) {
		
		Optional<Produto> p = service.buscarPorId(id);
		Produto produto = p.get();
		
		if(produto != null) {
			model.addAttribute("p", produto);
		}
		
		model.addAttribute("produto", new Produto());
		
		return "cadastroProduto";
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
		return service.listarPorDescricao();
	}
	

	
    @GetMapping("/listaProdutos")
    public String listaProdutos(Model model) {
    	
        pageModel.setSIZE(8);
        pageModel.initPageAndSize();
        model.addAttribute("produto", new Produto());
        model.addAttribute("listaProdutos", service.listar(PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE())));
        return "listaProdutos";
    }
    
//    @RequestMapping("produto/{id}")
//    public String produto(@PathVariable("id") Long id, ModelMap model) {
//    	Optional<Produto> p = service.buscarPorId(id);
//		Produto produto = p.get();
//		
//		model.addAttribute("produto", produto);
//		
//		return "listaProdutos :: modalEditar";
//    }

}
