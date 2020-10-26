package br.com.todolistapi.todolist.controller;

import java.util.List;

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

import br.com.todolistapi.todolist.model.Tarefa;
import br.com.todolistapi.todolist.model.dto.TarefaResponse;
import br.com.todolistapi.todolist.repository.TarefaRepository;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	@GetMapping
	public List<TarefaResponse> listar() {
		List<Tarefa> tarefas = tarefaRepository.findAll();
		return Tarefa.paraListDto(tarefas);
	}
	@GetMapping("/{usuario_id}")
	public List<TarefaResponse> todasTarefasPorUsuario(@PathVariable Long usuario_id){
		List<Tarefa> tarefas = tarefaRepository.findAllByUsuario_id(usuario_id);
		return Tarefa.paraListDto(tarefas);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TarefaResponse adicionar(@RequestBody @Valid Tarefa tarefa) {
		tarefaRepository.save(tarefa);
		TarefaResponse tarefaResponse = tarefa.paraDto(tarefa.getId(), tarefa.getTitulo(), tarefa.getDescricao(), tarefa.getData());
		return tarefaResponse;
	}
	
	@PutMapping("/{tarefaId}")
	public ResponseEntity<TarefaResponse> atualizar(@PathVariable Long tarefaId, @RequestBody @Valid Tarefa tarefa){
		if(!tarefaRepository.existsById(tarefaId)) {
			return ResponseEntity.notFound().build();
		}
		
		tarefa.setId(tarefaId);
		tarefa = tarefaRepository.save(tarefa);
		TarefaResponse tarefaResponse = tarefa.paraDto(tarefa.getId(), tarefa.getTitulo(), tarefa.getDescricao(), tarefa.getData());
		return ResponseEntity.ok(tarefaResponse);
	}
	
	@DeleteMapping("/{tarefaId}")
	public ResponseEntity<Void> remover(@PathVariable Long tarefaId){
		if(!tarefaRepository.existsById(tarefaId)) {
			return ResponseEntity.notFound().build();
		}
		
		tarefaRepository.deleteById(tarefaId);
		return ResponseEntity.noContent().build();
	}
}
