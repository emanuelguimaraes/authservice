package br.com.authservice.core.domain.interfaces;

public interface ValidadorUsuario {
    void validarNome(String nome);
    void validarEmail(String email);
    void validarSenha(String senha);
    boolean validarCredenciais(String email, String senha);
}