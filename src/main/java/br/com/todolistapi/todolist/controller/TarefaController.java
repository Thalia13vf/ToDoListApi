package br.com.todolistapi.todolist.controller;

import java.util.List;

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
import br.com.todolistapi.todolist.repository.TarefaRepository;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	@GetMapping
	public List<Tarefa> listar() {
		List<Tarefa> tarefas = tarefaRepository.findAll();
		return tarefas;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Tarefa adicionar(@RequestBody Tarefa tarefa) {
		return tarefaRepository.save(tarefa);
	}
	
	@PutMapping("/{tarefaId}")
	public ResponseEntity<Tarefa> atualizar(@PathVariable Long tarefaId, @RequestBody Tarefa tarefa){
		if(!tarefaRepository.existsById(tarefaId)) {
			return ResponseEntity.notFound().build();
		}
		
		tarefa.setId(tarefaId);
		tarefa = tarefaRepository.save(tarefa);
		return ResponseEntity.ok(tarefa);
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
