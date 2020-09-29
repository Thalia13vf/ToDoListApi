package br.com.todolistapi.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.todolistapi.todolist.model.Usuario;
import br.com.todolistapi.todolist.repository.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public void delete(Long UsuarioId) {
		usuarioRepository.deleteById(UsuarioId);
	}
}
