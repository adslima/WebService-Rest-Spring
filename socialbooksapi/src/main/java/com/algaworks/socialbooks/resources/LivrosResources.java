package com.algaworks.socialbooks.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.socialbooks.domain.Livros;
import com.algaworks.socialbooks.service.LivrosService;
import com.algaworks.socialbooks.service.exceptions.LivroNaoEncontradoException;

@RestController
@RequestMapping(value = "/livros")
public class LivrosResources {

	@Autowired
	private LivrosService livrosService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Livros>> Listar() {
		return ResponseEntity.status(HttpStatus.OK).body(this.livrosService.listar());
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> salvar(@RequestBody Livros livro) {
		livro = livrosService.salvar(livro);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(livro.getId()).toUri();

		return ResponseEntity.created(uri).build();

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
		Livros livros = null;
		try {
			livros = livrosService.buscar(id);
		} catch (LivroNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(livros);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
		try {
			this.livrosService.deletar(id);
		} catch (LivroNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizar(@RequestBody Livros livros, @PathVariable("id") Long id) {
		livros.setId(id);
		try {
			this.livrosService.atualizar(livros);
		} catch (LivroNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}

}
