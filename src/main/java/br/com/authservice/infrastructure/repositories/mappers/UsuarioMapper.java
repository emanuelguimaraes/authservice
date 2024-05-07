package br.com.authservice.infrastructure.repositories.mappers;

import br.com.authservice.core.domain.model.Usuario;
import br.com.authservice.infrastructure.repositories.entities.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    default Usuario toDomain(UsuarioEntity usuarioEntity) {
        if (usuarioEntity == null) {
            return null;
        }

        return new Usuario(
            usuarioEntity.getNome(),
            usuarioEntity.getEmail(),
            usuarioEntity.getSenha(),
            usuarioEntity.getPermissoes().stream().map(PermissaoMapper.INSTANCE::toDomain).collect(Collectors.toList())
        );
    }

    default UsuarioEntity fromDomain(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNome(usuario.getNome());
        usuarioEntity.setEmail(usuario.getEmail());
        usuarioEntity.setSenha(usuario.getSenha());
        usuarioEntity.setPermissoes(usuario.getPermissoes().stream().map(PermissaoMapper.INSTANCE::fromDomain).collect(Collectors.toSet()));

        return usuarioEntity;
    }
}