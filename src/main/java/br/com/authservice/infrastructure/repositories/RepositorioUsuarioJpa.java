package br.com.authservice.infrastructure.repositories;

import br.com.authservice.core.domain.interfaces.RepositorioUsuario;
import br.com.authservice.core.domain.model.Usuario;
import br.com.authservice.core.exceptions.SalvarUsuarioException;
import br.com.authservice.infrastructure.repositories.entities.UsuarioEntity;
import br.com.authservice.infrastructure.repositories.mappers.UsuarioMapper;
import jakarta.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioUsuarioJpa extends JpaRepository<UsuarioEntity, Long>, RepositorioUsuario {

    Logger logger = LoggerFactory.getLogger(RepositorioUsuarioJpa.class);

    @Override
    default Usuario buscarUsuarioPorEmail(String email) {
        return this.findByEmail(email)
                .map(UsuarioMapper.INSTANCE::toDomain)
                .orElse(null);
    }

    @Override
    default Long salvarUsuario(Usuario usuario) {
        try {
            UsuarioEntity savedEntity = this.save(UsuarioMapper.INSTANCE.fromDomain(usuario));
            return savedEntity.getId();
        } catch (PersistenceException e) {
            logger.error("Erro ao salvar o usuário: ", e);
            throw new SalvarUsuarioException("Erro ao salvar o usuário", e);
        }
    }

    @Override
    default Usuario buscarUsuarioPorToken(String token) {
        return this.findByTokensValor(token)
                .map(UsuarioMapper.INSTANCE::toDomain)
                .orElse(null);
    }

    @Override
    default void removerTodosUsuarios() {
        this.deleteAll();
    }

    Optional<UsuarioEntity> findByEmail(String email);

    Optional<UsuarioEntity> findByTokensValor(String token);
}