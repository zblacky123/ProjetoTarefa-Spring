package br.com.projeto.projetospring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.projetospring.domain.Tarefa;
import br.com.projeto.projetospring.repository.TarefaRepository;

@RestController
public class TarefaController {
	
	@Autowired
	private TarefaRepository ur;
	
	@GetMapping("/tarefa/listar")
	public List<Tarefa> listar() {
		return ur.findAll();
	}
	
	@PostMapping("/tarefa/cadastrar")
	public String cadastrar(@RequestBody Tarefa us) {
		String msg = "";
		ur.save(us);
		msg = "Cadastrou!";
		return msg;
	}

}
