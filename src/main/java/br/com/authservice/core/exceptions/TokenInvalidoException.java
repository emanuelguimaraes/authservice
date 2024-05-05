package br.com.authservice.core.exceptions;

public class TokenInvalidoException extends RuntimeException {

    public TokenInvalidoException() {
        super("O token fornecido é inválido.");
    }
}