package br.com.authservice.core.usecases;

import br.com.authservice.core.domain.interfaces.CriptografadorSenha;
import br.com.authservice.core.domain.interfaces.GerenciadorToken;
import br.com.authservice.core.domain.interfaces.RepositorioUsuario;
import br.com.authservice.core.domain.interfaces.ValidadorUsuario;
import br.com.authservice.core.domain.model.Token;
import br.com.authservice.core.domain.model.Usuario;
import br.com.authservice.core.exceptions.CredenciaisInvalidasException;

public class AutenticarUsuario {

    private final ValidadorUsuario validadorUsuario;
    private final CriptografadorSenha criptografadorSenha;
    private final RepositorioUsuario repositorioUsuario;
    private final GerenciadorToken gerenciadorToken;

    public AutenticarUsuario(ValidadorUsuario validadorUsuario,
                             CriptografadorSenha criptografadorSenha,
                             RepositorioUsuario repositorioUsuario,
                             GerenciadorToken gerenciadorToken) {
        this.validadorUsuario = validadorUsuario;
        this.criptografadorSenha = criptografadorSenha;
        this.repositorioUsuario = repositorioUsuario;
        this.gerenciadorToken = gerenciadorToken;
    }

    public Token execute(String email, String senha) {
        // Validação dos dados de entrada
        validadorUsuario.validarEmail(email);
        validadorUsuario.validarSenha(senha);

        // Busca do usuário no banco de dados
        Usuario usuario = repositorioUsuario.buscarUsuarioPorEmail(email);
        if (usuario == null) {
            throw new CredenciaisInvalidasException();
        }

        // Verificação da senha
        if (!criptografadorSenha.verificarSenha(senha, usuario.getSenha())) {
            throw new CredenciaisInvalidasException();
        }

        // Geração do token de autenticação
        Token token = gerenciadorToken.gerarToken();

        // Persistência do token no banco de dados
        gerenciadorToken.salvarToken(token);

        // Retorno do token de autenticação
        return token;
    }
}