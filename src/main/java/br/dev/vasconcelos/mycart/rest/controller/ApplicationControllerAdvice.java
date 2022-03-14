package br.dev.vasconcelos.mycart.rest.controller;

import br.dev.vasconcelos.mycart.exception.BusinessRulesException;
import br.dev.vasconcelos.mycart.rest.ApiErrors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(BusinessRulesException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(BusinessRulesException ex){
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException ex){
        return new ApiErrors(
                ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map( erro -> erro.getDefaultMessage() )
                .collect(Collectors.toList())
        );
    }
}
