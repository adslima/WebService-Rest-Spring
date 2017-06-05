package com.algaworks.socialbooks.service.exceptions;

public class LivroNaoEncontradoException  extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4122655043683301118L;

	
	public LivroNaoEncontradoException (String mensagem){
		super(mensagem);
	}
	
	public LivroNaoEncontradoException (String mensagem, Throwable causa){
		super(mensagem, causa);
	}
}
