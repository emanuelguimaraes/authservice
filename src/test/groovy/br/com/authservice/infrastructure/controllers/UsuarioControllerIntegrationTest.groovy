package br.com.authservice.infrastructure.controllers

import br.com.authservice.core.domain.interfaces.RepositorioUsuario
import br.com.authservice.core.domain.model.Permissao
import br.com.authservice.core.domain.model.Usuario
import br.com.authservice.infrastructure.controllers.dto.UsuarioDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UsuarioControllerIntegrationTest extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    RepositorioUsuario repositorioUsuario

    ObjectMapper objectMapper

    def setup() {
        objectMapper = new ObjectMapper()
    }

    def cleanup() {
        repositorioUsuario.removerTodosUsuarios()
    }

    def "registrarUsuario endpoint saves user correctly"() {
        setup:
        UsuarioDTO usuarioDTO = new UsuarioDTO("Usuário Teste", "teste@teste.com", "SenhaTeste#001", Arrays.asList(Permissao.WRITE))

        when: "the registrarUsuario endpoint is called"
        def result = mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO)))

        then: "the response status is 200 OK"
        result.andExpect(status().isOk())

        and: "the user is saved in the database"
        Usuario savedUser = repositorioUsuario.buscarUsuarioPorEmail("teste@teste.com")
        savedUser != null
        savedUser.getNome() == "Usuário Teste"
        savedUser.getEmail() == "teste@teste.com"
        savedUser.getPermissoes() == Arrays.asList(Permissao.WRITE)
    }

    def "registrarUsuario endpoint throws EmailJaCadastradoException for existing email"() {
        setup:
        UsuarioDTO usuarioDTO = new UsuarioDTO("Usuário Teste", "teste@teste.com", "SenhaTeste#001", Arrays.asList(Permissao.WRITE))
        Usuario existingUser = new Usuario("Usuário Existente", "teste@teste.com", "SenhaExistente#001", Arrays.asList(Permissao.WRITE))
        repositorioUsuario.salvarUsuario(existingUser)

        when: "the registrarUsuario endpoint is called with an existing email"
        def result = mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO)))

        then: "the response status is 409 Conflict"
        result.andExpect(status().isConflict())
    }

    def "registrarUsuario endpoint throws MethodArgumentNotValidException for invalid user data"() {
        setup:
        UsuarioDTO usuarioDTO = new UsuarioDTO("", "invalid email", "", Arrays.asList(Permissao.WRITE))

        when: "the registrarUsuario endpoint is called with invalid user data"
        def result = mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO)))

        then: "the response status is 400 Bad Request"
        result.andExpect(status().isBadRequest())
    }
}