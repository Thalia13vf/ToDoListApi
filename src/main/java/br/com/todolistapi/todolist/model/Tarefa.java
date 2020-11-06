package br.com.todolistapi.todolist.model;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import br.com.todolistapi.todolist.model.dto.TarefaResponse;

@Entity
public class Tarefa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "{titulo.not.blank}")
	private String titulo;
	
	@NotBlank(message = "{descricao.not.blank}")
	private String descricao;
	
	private OffsetDateTime data;
	
	private StatusTarefa status;
	
	public Tarefa() {
		this.status = StatusTarefa.ABERTA;
	}
	
	@ManyToOne
	@JoinColumn(name="usuario_id")
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

	public StatusTarefa getStatus() {
		return status;
	}
	public void setStatus(StatusTarefa status) {
		this.status = status;
	}
	
	private boolean validarFinalizacao(Tarefa tarefa) {
		if(tarefa.getStatus().equals(StatusTarefa.ABERTA)) {
			System.out.println("TAREFA ABERTA");
			return true;
			
		}
		return false;
	}
	
	public void finalizarTarefa(Tarefa tarefa) {
		boolean finalizada = validarFinalizacao(tarefa);
		if(finalizada == true) {
			tarefa.setStatus(StatusTarefa.FINALIZADA);
		}
	}
	
	public static List<TarefaResponse> paraListDto(List<Tarefa> lista){
		
		return lista.stream()
				.map(tarefa -> tarefa.paraDto(tarefa.id, tarefa.titulo, tarefa.descricao, tarefa.data, tarefa.status))
				.collect(Collectors.toList());
	}
	
	public TarefaResponse paraDto(Long id, String titulo, String descricao, OffsetDateTime data, StatusTarefa status) {
		return new TarefaResponse(id, titulo, descricao, data, status);
	}
	
}
