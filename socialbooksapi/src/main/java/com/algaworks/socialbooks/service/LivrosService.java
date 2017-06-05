package com.algaworks.socialbooks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.socialbooks.domain.Livros;
import com.algaworks.socialbooks.respository.LivrosRepository;
import com.algaworks.socialbooks.service.exceptions.LivroNaoEncontradoException;

public class LivrosService {

	@Autowired
	private LivrosRepository livrosRepository;

	public List<Livros> listar() {
		return this.livrosRepository.findAll();
	}

	public Livros buscar(Long id) {
		Livros livro = livrosRepository.findOne(id);

		if (livro != null) {
			return livro;
		}
		throw new LivroNaoEncontradoException("O livro não foi encontrado nessa pesquisa");
	}

	public Livros salvar(Livros livro) {
		livro.setId(null);
		return livrosRepository.save(livro);
	}

	public void deletar(Long id) {
		try {
			livrosRepository.delete(id);
		} catch (LivroNaoEncontradoException e) {
			throw new LivroNaoEncontradoException("Não possivel encontrar o livro para deletar");
		}
	}

	public void atualizar(Livros livro) {
		verificarExistencia(livro);
		this.livrosRepository.save(livro);
	}

	private void verificarExistencia(Livros livro) {
		buscar(livro.getId());
	}

}
