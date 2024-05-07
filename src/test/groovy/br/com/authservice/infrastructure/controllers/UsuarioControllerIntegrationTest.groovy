package br.com.authservice.infrastructure.controllers

import br.com.authservice.core.domain.model.Permissao
import br.com.authservice.infrastructure.controllers.dto.UsuarioDTO
import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
class UsuarioControllerIntegrationTest extends Specification {

    @Autowired
    WebApplicationContext webApplicationContext

    MockMvc mockMvc

    def setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    def "test registrarUsuario endpoint"() {
        given: "a user DTO"
        UsuarioDTO usuarioDTO = new UsuarioDTO("Teste", "teste@teste.com", "senha", Arrays.asList(Permissao.WRITE))

        when: "the registrarUsuario endpoint is called"
        def result = mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonOutput.toJson(usuarioDTO)))

        then: "the response status is 200 OK"
        result.andExpect(status().isOk())
    }
}