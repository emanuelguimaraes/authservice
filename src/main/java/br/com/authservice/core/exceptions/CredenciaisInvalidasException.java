package br.com.authservice.core.exceptions;

public class CredenciaisInvalidasException extends RuntimeException {

    public CredenciaisInvalidasException() {
        super("As credenciais fornecidas são inválidas.");
    }
}