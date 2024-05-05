package br.com.authservice.core.usecases;

import br.com.authservice.core.domain.interfaces.GerenciadorToken;
import br.com.authservice.core.domain.interfaces.RepositorioUsuario;
import br.com.authservice.core.domain.model.Usuario;
import br.com.authservice.core.exceptions.TokenInvalidoException;

public class ValidarToken {

    private final GerenciadorToken gerenciadorToken;
    private final RepositorioUsuario repositorioUsuario;

    public ValidarToken(GerenciadorToken gerenciadorToken, RepositorioUsuario repositorioUsuario) {
        this.gerenciadorToken = gerenciadorToken;
        this.repositorioUsuario = repositorioUsuario;
    }

    public Usuario execute(String token) {
        // Validação do token de autenticação
        if (!gerenciadorToken.validarToken(token)) {
            throw new TokenInvalidoException();
        }

        // Busca do token no banco de dados
        // Obtenção das informações do usuário associado ao token
        // Retorno das informações do usuário
        return repositorioUsuario.buscarUsuarioPorToken(token);
    }
}