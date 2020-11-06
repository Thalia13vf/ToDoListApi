package br.com.todolistapi.todolist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.todolistapi.todolist.model.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long>{
	List<Tarefa> findAllByUsuario_id(Long usuario_id);
	
}
