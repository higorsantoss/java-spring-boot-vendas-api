package br.com.higor.vendas.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.higor.vendas.controller.error.ApiErrors;
import br.com.higor.vendas.exception.RegraNegocioException;

@RestControllerAdvice
public class ApplicationControllerAdvice {

	@ExceptionHandler(RegraNegocioException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleRegraNegocioException(RegraNegocioException ex) {
		String mensagemErro = ex.getMessage();
		return new ApiErrors(mensagemErro);
	}
	
	
	// Bean Validator responsavel por validar mensagem de erro que defini na entidade com a anotation @NotEmpty
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult().getAllErrors().stream().map( 
															erro -> erro.getDefaultMessage()).collect(Collectors.toList());
		return new ApiErrors(errors);
	}
}
