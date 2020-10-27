package com.llac.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.llac.entidades.Usuario;
import com.llac.repositorio.UsuarioRepositorio;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepositorio repositorio;

	public Usuario inserir(Usuario usuario) {
		return repositorio.save(usuario);
	}

	public Page<Usuario> listar(Pageable pageable, String keyword) {
		if (keyword != null) {
			return repositorio.listarPorKeyword(keyword, pageable);
		}
		return repositorio.findAll(pageable);
	}

	public Optional<Usuario> buscarPorId(Long id) {
		return repositorio.findById(id);
	}

	public void deletar(Usuario usuario) {
		repositorio.delete(usuario);
	}

	public Usuario criarOuAtualizarUsuario(Usuario u) {
		if (u.getId() == null) {
			u = repositorio.save(u);

			return u;

		} else {
			Optional<Usuario> usuario = repositorio.findById(u.getId());

			if (usuario.isPresent()) {
				Usuario novoUsuario = usuario.get();
				novoUsuario.setLogin(u.getLogin());
				novoUsuario.setSenha(u.getSenha());

				novoUsuario = repositorio.save(novoUsuario);

				return novoUsuario;
			} else {
				u = repositorio.save(u);

				return u;
			}
		}
	}
}
