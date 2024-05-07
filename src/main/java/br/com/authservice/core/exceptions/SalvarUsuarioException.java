package br.com.authservice.core.exceptions;

public class SalvarUsuarioException extends RuntimeException {
    public SalvarUsuarioException(String message, Throwable cause) {
        super(message, cause);
    }
}