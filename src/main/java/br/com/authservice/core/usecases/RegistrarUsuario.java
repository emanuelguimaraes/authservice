package br.com.authservice.core.usecases;

import br.com.authservice.core.domain.interfaces.CriptografadorSenha;
import br.com.authservice.core.domain.interfaces.RepositorioUsuario;
import br.com.authservice.core.domain.interfaces.ValidadorUsuario;
import br.com.authservice.core.domain.model.Permissao;
import br.com.authservice.core.domain.model.Usuario;
import br.com.authservice.core.exceptions.EmailJaCadastradoException;

import java.util.List;

public class RegistrarUsuario {

    private final ValidadorUsuario validadorUsuario;
    private final CriptografadorSenha criptografadorSenha;
    private final RepositorioUsuario repositorioUsuario;

    public RegistrarUsuario(ValidadorUsuario validadorUsuario,
        CriptografadorSenha criptografadorSenha,
        RepositorioUsuario repositorioUsuario) {
        this.validadorUsuario = validadorUsuario;
        this.criptografadorSenha = criptografadorSenha;
        this.repositorioUsuario = repositorioUsuario;
    }

    public Long execute(String nome, String email, String senha, List<Permissao> permissoes) {
        // Validação dos dados de entrada
        validadorUsuario.validarNome(nome);
        validadorUsuario.validarEmail(email);
        validadorUsuario.validarSenha(senha);

        // Verificação de email existente
        Usuario usuarioExistente = repositorioUsuario.buscarUsuarioPorEmail(email);
        if (usuarioExistente != null) {
            throw new EmailJaCadastradoException();
        }

        // Criptografia da senha
        String senhaCriptografada = criptografadorSenha.criptografarSenha(senha);

        // Criação do objeto Usuário
        Usuario usuario = new Usuario(nome, email, senhaCriptografada, permissoes);

        // Persistência do usuário no banco de dados
        // Retorno do ID do usuário criado
        return repositorioUsuario.salvarUsuario(usuario);
    }
}