package br.com.authservice.infrastructure.config;

import br.com.authservice.core.domain.interfaces.CriptografadorSenha;
import br.com.authservice.core.domain.interfaces.RepositorioUsuario;
import br.com.authservice.core.domain.interfaces.ValidadorUsuario;
import br.com.authservice.core.usecases.RegistrarUsuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsecaseConfig {

    @Bean
    public RegistrarUsuario registrarUsuario(ValidadorUsuario validadorUsuario,
                                             CriptografadorSenha criptografadorSenha,
                                             RepositorioUsuario repositorioUsuario) {
        return new RegistrarUsuario(validadorUsuario, criptografadorSenha, repositorioUsuario);
    }
}