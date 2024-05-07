package br.com.authservice.infrastructure.gateways;

import br.com.authservice.core.domain.interfaces.CriptografadorSenha;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CriptografadorSenhaImpl implements CriptografadorSenha {

    private final BCryptPasswordEncoder encoder;

    public CriptografadorSenhaImpl() {
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public String criptografarSenha(String senha) {
        return encoder.encode(senha);
    }

    @Override
    public boolean verificarSenha(String senha, String senhaCriptografada) {
        return encoder.matches(senha, senhaCriptografada);
    }
}