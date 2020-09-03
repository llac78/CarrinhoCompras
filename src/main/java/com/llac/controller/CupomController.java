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

import com.llac.entidades.Cupom;
import com.llac.service.CupomService;

@Controller
public class CupomController {
	
	@Autowired
	private CupomService service;
	
	private List<Cupom> cupons = new ArrayList<>();
	
	@GetMapping("/cadastroCupom")
	public String cadastroCupom( Model model) {
		
		cupons = listarCupons();
		
		model.addAttribute("cupom", new Cupom());
		model.addAttribute("cupons", cupons);
		
		return "cadastroCupom";
	}

	@PostMapping("/inserirCupom")
	public String inserirCupom(@Valid Cupom cupom, BindingResult result){
		
		if(result.hasFieldErrors()) {
			return "cadastroCupom";
		}
		
		if(cupom != null) {
			service.inserir(cupom);
		}
		
		return "index";
	}
	
	private List<Cupom> listarCupons() {
		return service.listar();
	}


}
