package br.com.authservice.core.usecases

import br.com.authservice.core.domain.interfaces.CriptografadorSenha
import br.com.authservice.core.domain.interfaces.RepositorioUsuario
import br.com.authservice.core.domain.interfaces.ValidadorUsuario
import br.com.authservice.core.domain.model.Permissao
import br.com.authservice.core.domain.model.Usuario
import br.com.authservice.core.exceptions.EmailJaCadastradoException
import spock.lang.Specification

import static java.util.Collections.emptyList

class RegistrarUsuarioTest extends Specification {

    ValidadorUsuario validadorUsuario
    CriptografadorSenha criptografadorSenha
    RepositorioUsuario repositorioUsuario
    RegistrarUsuario registrarUsuario

    def setup() {
        validadorUsuario = Mock(ValidadorUsuario)
        criptografadorSenha = Mock(CriptografadorSenha)
        repositorioUsuario = Mock(RepositorioUsuario)
        registrarUsuario = new RegistrarUsuario(validadorUsuario, criptografadorSenha, repositorioUsuario)
    }

    def "test execute"() {
        given: "a user"
        String nome = "Teste"
        String email = "teste@teste.com"
        String senha = "senha"
        List<Permissao> permissoes = emptyList()
        Long idEsperado = 1L

        when: "execute is called"
        Long idRetornado = registrarUsuario.execute(nome, email, senha, permissoes)

        then: "the returned id should be as expected"
        idRetornado == idEsperado
        1 * validadorUsuario.validarNome(nome)
        1 * validadorUsuario.validarEmail(email)
        1 * validadorUsuario.validarSenha(senha)
        1 * repositorioUsuario.buscarUsuarioPorEmail(email) >> null
        1 * repositorioUsuario.salvarUsuario(_) >> 1L
    }

    def "test execute with already registered email"() {
        given: "a user with an already registered email"
        String nome = "Teste"
        String email = "teste@teste.com"
        String senha = "senha"
        List<Permissao> permissoes = emptyList()
        Usuario usuarioExistente = new Usuario(nome, email, senha, permissoes)

        when: "execute is called"
        repositorioUsuario.buscarUsuarioPorEmail(email) >> usuarioExistente
        registrarUsuario.execute(nome, email, senha, permissoes)

        then: "an EmailJaCadastradoException is thrown"
        EmailJaCadastradoException e = thrown()
    }
}