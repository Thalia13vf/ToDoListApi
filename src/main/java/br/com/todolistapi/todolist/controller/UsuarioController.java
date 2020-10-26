package br.com.todolistapi.todolist.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.todolistapi.todolist.model.Usuario;
import br.com.todolistapi.todolist.model.dto.UsuarioResponse;
import br.com.todolistapi.todolist.repository.UsuarioRepository;
import br.com.todolistapi.todolist.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/{usuarioId}")
	public ResponseEntity<UsuarioResponse> buscar(@PathVariable Long usuarioId){
		Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
		if(usuario.isPresent()) {
			UsuarioResponse usuarioResponse = usuario.get().paraDto(usuario.get().getNome(), usuario.get().getEmail());
			return ResponseEntity.ok(usuarioResponse);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioResponse adicionar(@RequestBody @Valid Usuario usuario) {
		usuarioService.salvar(usuario);
		return usuario.paraDto(usuario.getNome(), usuario.getEmail());
	}
	
	@PutMapping("/{usuarioId}")
	public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Long usuarioId, @RequestBody @Valid Usuario usuario){
		if(!usuarioRepository.existsById(usuarioId)) {
			return ResponseEntity.notFound().build();
		}
		
		usuario.setId(usuarioId);
		usuario = usuarioRepository.save(usuario);
		UsuarioResponse usuarioResponse = usuario.paraDto(usuario.getNome(), usuario.getEmail());
		return ResponseEntity.ok(usuarioResponse);
	}
	
	@DeleteMapping("/{usuarioId}")
	public ResponseEntity<Void> excluir(@PathVariable Long usuarioId){
		if(!usuarioRepository.existsById(usuarioId)) {
			return ResponseEntity.notFound().build();
		}
		usuarioService.delete(usuarioId);
		return ResponseEntity.noContent().build();
	}
	
}
