package br.com.authservice.application;

import br.com.authservice.core.domain.model.Permissao;
import br.com.authservice.core.usecases.RegistrarUsuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrarUsuarioService {

    private final RegistrarUsuario registrarUsuario;
    private final Logger logger = LoggerFactory.getLogger(RegistrarUsuarioService.class);

    public RegistrarUsuarioService(RegistrarUsuario registrarUsuario) {
        this.registrarUsuario = registrarUsuario;
    }

    public Long registrarUsuario(String nome, String email, String senha, List<Permissao> permissoes) {
        logger.info("Iniciando o registro do usuário {}", email);
        try {
            Long usuarioId = registrarUsuario.execute(nome, email, senha, permissoes);
            logger.info("Usuário {} registrado com sucesso", email);
            return usuarioId;
        } catch (Exception e) {
            logger.error("Erro ao registrar o usuário {}", email, e);
            throw e;
        }
    }
}
