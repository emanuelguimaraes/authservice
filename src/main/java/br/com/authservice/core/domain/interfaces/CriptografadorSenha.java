package br.com.authservice.core.domain.interfaces;

public interface CriptografadorSenha {
    String criptografarSenha(String senha);
    boolean verificarSenha(String senha, String senhaCriptografada);
}