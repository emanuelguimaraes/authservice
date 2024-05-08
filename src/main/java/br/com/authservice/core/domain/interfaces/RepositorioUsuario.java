package br.com.authservice.core.domain.interfaces;

import br.com.authservice.core.domain.model.Usuario;

public interface RepositorioUsuario {
    Usuario buscarUsuarioPorEmail(String email);
    Long salvarUsuario(Usuario usuario);
    Usuario buscarUsuarioPorToken(String token);
    void removerTodosUsuarios();
}