package br.com.projeto.projetospring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projeto.projetospring.domain.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Integer>{

}
