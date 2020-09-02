package com.llac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.llac.entidades.Cupom;
import com.llac.repositorio.CupomRepositorio;

@Service
public class CupomService {
	
	@Autowired
	private CupomRepositorio repositorio;

	public Cupom inserir(Cupom cupom) {
		return repositorio.save(cupom);
	}
	
	public List<Cupom> listarCupons(){
		return repositorio.findAll();
	}
}
