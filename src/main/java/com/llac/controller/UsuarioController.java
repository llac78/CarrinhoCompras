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

import com.llac.entidades.Usuario;
import com.llac.service.UsuarioService;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@RequestMapping(path = { "/cadastroUsuario", "/cadastroUsuario/{id}" })
	public String salvarUsuarioPorId(Model model, @PathVariable("id") Long id) {
		
		if (id != null) {
			Optional<Usuario> opt = service.buscarPorId(id);
			Usuario u = opt.get();
			model.addAttribute("usuario", u);
		} else {
			model.addAttribute("usuario", new Usuario());
		}
		return "cadastroUsuario";
	}

	@PostMapping("/criarUsuario")
	public String criarOuAtualizarUsuario(@Valid Usuario usuario, BindingResult result) {
		
		if (result.hasFieldErrors()) {
			return "cadastroUsuario";
		}
		service.criarOuAtualizarUsuario(usuario);
		
		return "redirect:/";
	}

	@GetMapping("/cadastroUsuario")
	public String cadastroUsuario(Model model) {
		
		model.addAttribute("usuario", new Usuario());

		return "cadastroUsuario";
	}

	@PostMapping("/{id}/deletarUsuario")
	public String deletar(@PathVariable("id") Long id) {

		Optional<Usuario> u = service.buscarPorId(id);
		Usuario usuario = u.get();

		if (usuario != null) {
			service.deletar(usuario);
		}
		return "index";
	}

    @GetMapping("/listaUsuarios/{page}")
    public ModelAndView listarUsuariosPaginacao(@PathVariable("page") int page, @Param("keyword") String keyword) {
	   
        ModelAndView modelAndView = new ModelAndView("listaUsuarios");
        Pageable pageable = PageRequest.of(page - 1, 8, Sort.by("login"));
        
        Page<Usuario> usuarioPage = service.listar(pageable, keyword);
        List<Usuario> listaUsuarios = new ArrayList<>();
        
        listaUsuarios = usuarioPage.getContent();
        
        int totalPages = usuarioPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.addObject("keyword", keyword);
        modelAndView.addObject("listaUsuarios", listaUsuarios);
        
        return modelAndView;
    }

}
