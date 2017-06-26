package com.algaworks.socialbooks.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.socialbooks.domain.Comentarios;
import com.algaworks.socialbooks.domain.Livros;
import com.algaworks.socialbooks.respository.ComentariosRepository;
import com.algaworks.socialbooks.service.LivrosService;
import com.algaworks.socialbooks.service.exceptions.LivroNaoEncontradoException;

@RestController
@RequestMapping(value = "/livros")
public class LivrosResource {

	@Autowired
	private LivrosService livrosService;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Livros>> Listar() {
		return ResponseEntity.status(HttpStatus.OK).body(this.livrosService.listar());
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> salvar(@RequestBody Livros livro) {
		livro = livrosService.salvar(livro);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(livro.getId()).toUri();

		return ResponseEntity.created(uri).build();

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) {
		Livros livros = livrosService.buscar(id);
		return ResponseEntity.status(HttpStatus.OK).body(livros);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deletar(@PathVariable("id") Long id) {
		this.livrosService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> atualizar(@RequestBody Livros livros, @PathVariable("id") Long id) {
		livros.setId(id);
		this.livrosService.atualizar(livros);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}/comentarios", method = RequestMethod.POST)
	public ResponseEntity<Void> adicionarComentario(@PathVariable("id") Long livrosId,
			@RequestBody Comentarios comentarios) {
		livrosService.salvarComentario(livrosId, comentarios);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

		return ResponseEntity.created(uri).build();
	}
	@RequestMapping(value = "/{id}/comentarios", method = RequestMethod.GET)
	public ResponseEntity<List<Comentarios>> listaComentarios(@PathVariable("id") Long livrosId){
		List<Comentarios>  comentarios = livrosService.listarComentarios(livrosId);
		return ResponseEntity.status(HttpStatus.OK).body(comentarios);}

}
