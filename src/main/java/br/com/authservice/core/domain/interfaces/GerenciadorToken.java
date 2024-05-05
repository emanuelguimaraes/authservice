package br.com.authservice.core.domain.interfaces;

import br.com.authservice.core.domain.model.Token;

public interface GerenciadorToken {
    Token gerarToken();
    void salvarToken(Token token);
    Token buscarToken(String token);
    boolean validarToken(String token);
}