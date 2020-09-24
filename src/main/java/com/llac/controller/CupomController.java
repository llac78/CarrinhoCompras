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

import com.llac.entidades.Cupom;
import com.llac.service.CupomService;

@Controller
public class CupomController {
	
	@Autowired
	private CupomService service;
	
	@RequestMapping(path = { "/cadastroCupom", "/cadastroCupom/{id}" })
	public String salvarCupomPorId(Model model, @PathVariable("id") Long id) {
		
		if (id != null) {
			Optional<Cupom> opt = service.buscarPorId(id);
			Cupom p = opt.get();
			model.addAttribute("cupom", p);
		} else {
			model.addAttribute("cupom", new Cupom());
		}
		return "cadastroCupom";
	}

	@PostMapping("/criarCupom")
	public String criarOuAtualizarCupom(@Valid Cupom cupom, BindingResult result) {
		
		if (result.hasFieldErrors()) {
			return "cadastroCupom";
		}
		service.criarOuAtualizarCupom(cupom);
		
		return "redirect:/";
	}

	@GetMapping("/cadastroCupom")
	public String cadastroCupom(Model model) {
		
		model.addAttribute("cupom", new Cupom());

		return "cadastroCupom";
	}

	@PostMapping("/{id}/deletarCupom")
	public String deletar(@PathVariable("id") Long id) {

		Optional<Cupom> p = service.buscarPorId(id);
		Cupom cupom = p.get();

		if (cupom != null) {
			service.deletar(cupom);
		}
		return "index";
	}

    @GetMapping("/listaCupons/{page}")
    public ModelAndView listarCuponsPaginacao(@PathVariable("page") int page, @Param("keyword") String keyword) {
	   
        ModelAndView modelAndView = new ModelAndView("listaCupons");
        Pageable pageable = PageRequest.of(page - 1, 8, Sort.by("codigo"));
        
        Page<Cupom> cupomPage = service.listar(pageable, keyword);
        List<Cupom> listaCupons = new ArrayList<>();
        
        listaCupons = cupomPage.getContent();
        
        int totalPages = cupomPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("keyword", keyword);
        modelAndView.addObject("listaCupons", listaCupons);
        
        return modelAndView;
    }

}
