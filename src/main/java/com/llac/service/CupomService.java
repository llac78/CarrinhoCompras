package com.llac.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	public Page<Cupom> listar(Pageable pageable, String keyword) {
		   if (keyword != null) {
		       return repositorio.listarPorKeyword(keyword, pageable);
		   }
		   return repositorio.findAll(pageable);
		}
		
		public Optional<Cupom> buscarPorId(Long id) {
			return repositorio.findById(id);
		}

		public void deletar(Cupom cupom) {
			repositorio.delete(cupom);
		}

		public Cupom criarOuAtualizarCupom(Cupom c) {
			if (c.getId() == null) {
				c = repositorio.save(c);

				return c;
				
			} else {
				Optional<Cupom> cupom = repositorio.findById(c.getId());

				if (cupom.isPresent()) {
					Cupom novoCupom = cupom.get();
					novoCupom.setCodigo(c.getCodigo());
					novoCupom.setValorDesconto(c.getValorDesconto());

					novoCupom = repositorio.save(novoCupom);

					return novoCupom;
				} else {
					c = repositorio.save(c);

					return c;
				}
			}
		}
}
