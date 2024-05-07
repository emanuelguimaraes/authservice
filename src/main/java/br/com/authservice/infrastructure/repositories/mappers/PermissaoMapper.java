package br.com.authservice.infrastructure.repositories.mappers;

import br.com.authservice.core.domain.model.Permissao;
import br.com.authservice.infrastructure.repositories.entities.PermissaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PermissaoMapper {

    PermissaoMapper INSTANCE = Mappers.getMapper(PermissaoMapper.class);

    default Permissao toDomain(PermissaoEntity permissaoEntity) {
        return Permissao.valueOf(permissaoEntity.getNome());
    }

    default PermissaoEntity fromDomain(Permissao permissao) {
        PermissaoEntity permissaoEntity = new PermissaoEntity();
        permissaoEntity.setNome(permissao.getDescricao());
        return permissaoEntity;
    }
}