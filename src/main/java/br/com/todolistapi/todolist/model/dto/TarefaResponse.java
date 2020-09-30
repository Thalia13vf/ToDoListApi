package br.com.todolistapi.todolist.model.dto;

import java.time.OffsetDateTime;

public class TarefaResponse {
	private Long id;
	private String titulo;
	private String descricao;
	private OffsetDateTime dataFinalizacao;
	
	public TarefaResponse(Long id, String titulo, String descricao, OffsetDateTime data) {
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.dataFinalizacao = data;
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
	public OffsetDateTime getDataFinalizacao() {
		return dataFinalizacao;
	}
	public void setDataFinalizacao(OffsetDateTime dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
