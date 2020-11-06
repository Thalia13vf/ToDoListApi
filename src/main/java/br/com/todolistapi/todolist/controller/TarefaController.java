package br.com.todolistapi.todolist.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import br.com.todolistapi.todolist.model.StatusTarefa;
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
	@GetMapping("/{usuario_id}/tarefasAbertas")
	public List<TarefaResponse> listarTarefasAbertasDoUsuario(@PathVariable Long usuario_id){
		List<Tarefa> tarefasAbertas = tarefaRepository.findAllByUsuario_id(usuario_id);
		tarefasAbertas = tarefasAbertas.stream().filter(tarefa -> tarefa.getStatus().equals(StatusTarefa.ABERTA)).collect(Collectors.toList());
		return Tarefa.paraListDto(tarefasAbertas);
	}
	
	@GetMapping("/{usuario_id}/tarefasFinalizadas")
	public List<TarefaResponse> listarTarefasFinalizadasDoUsuario(@PathVariable Long usuario_id){
		List<Tarefa> tarefasFinalizadas = tarefaRepository.findAllByUsuario_id(usuario_id);
		tarefasFinalizadas = tarefasFinalizadas.stream().filter(tarefa -> tarefa.getStatus().equals(StatusTarefa.FINALIZADA)).collect(Collectors.toList());
		return Tarefa.paraListDto(tarefasFinalizadas);
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
		TarefaResponse tarefaResponse = tarefa.paraDto(tarefa.getId(), tarefa.getTitulo(), tarefa.getDescricao(), tarefa.getData(), tarefa.getStatus());
		return tarefaResponse;
	}
	
	@PutMapping("/{tarefaId}")
	public ResponseEntity<TarefaResponse> atualizar(@PathVariable Long tarefaId, @RequestBody @Valid Tarefa tarefa){
		if(!tarefaRepository.existsById(tarefaId)) {
			return ResponseEntity.notFound().build();
		}
		
		tarefa.setId(tarefaId);
		tarefa = tarefaRepository.save(tarefa);
		TarefaResponse tarefaResponse = tarefa.paraDto(tarefa.getId(), tarefa.getTitulo(), tarefa.getDescricao(), tarefa.getData(), tarefa.getStatus());
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
	
	@PutMapping("/{tarefaId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long tarefaId) {
		Optional<Tarefa> tarefa = tarefaRepository.findById(tarefaId);
		Tarefa tarefaFinalizar = tarefa.get();
		tarefaFinalizar.finalizarTarefa(tarefaFinalizar);
		tarefaRepository.save(tarefaFinalizar);
	}
}
