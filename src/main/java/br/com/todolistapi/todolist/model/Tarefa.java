package br.com.todolistapi.todolist.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.todolistapi.todolist.model.dto.TarefaResponse;

@Entity
public class Tarefa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String descricao;
	private OffsetDateTime data;
	
	@ManyToOne
	private Usuario usuario;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public OffsetDateTime getData() {
		return data;
	}
	public void setData(OffsetDateTime data) {
		this.data = data;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public static List<TarefaResponse> paraListDto(List<Tarefa> lista){
		
		return lista.stream()
				.map(tarefa -> tarefa.paraDto(tarefa.id, tarefa.titulo, tarefa.descricao, tarefa.data))
				.collect(Collectors.toList());
	}
	
	public TarefaResponse paraDto(Long id, String titulo, String descricao, OffsetDateTime data) {
		return new TarefaResponse(id, titulo, descricao, data);
	}
	
}
