package com.algaworks.socialbooks.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.socialbooks.domain.Livros;

public interface LivrosRepository extends JpaRepository<Livros, Long> {

}
