package br.com.authservice.infrastructure.gateways;

import br.com.authservice.core.domain.interfaces.ValidadorUsuario;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
public class ValidadorUsuarioImpl implements ValidadorUsuario {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern NUMBER_PATTERN = Pattern.compile(".*\\d.*");
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile(".*[A-Z].*");
    private static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern.compile(".*[!@#$%&*()_+=|<>?{}\\[\\]~-].*");

    private static final int MIN_PASSWORD_LENGTH = 8;

    private static final String EMPTY_NAME_MESSAGE = "Nome não pode ser vazio";
    private static final String INVALID_EMAIL_MESSAGE = "Email inválido";
    private static final String NULL_PASSWORD_MESSAGE = "Senha não pode ser nula";
    private static final String SHORT_PASSWORD_MESSAGE = "Senha deve ter pelo menos 8 caracteres";
    private static final String NO_NUMBER_PASSWORD_MESSAGE = "Senha deve conter pelo menos um caractere numérico";
    private static final String NO_UPPERCASE_PASSWORD_MESSAGE = "Senha deve conter pelo menos uma letra maiúscula";
    private static final String NO_SPECIAL_CHARACTER_PASSWORD_MESSAGE = "Senha deve conter pelo menos um caractere especial";

    @Override
    public void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException(EMPTY_NAME_MESSAGE);
        }
    }

    @Override
    public void validarEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException(INVALID_EMAIL_MESSAGE);
        }
    }

    @Override
    public void validarSenha(String senha) {
        if (senha == null) {
            throw new IllegalArgumentException(NULL_PASSWORD_MESSAGE);
        }

        if (senha.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException(SHORT_PASSWORD_MESSAGE);
        }

        if (!NUMBER_PATTERN.matcher(senha).matches()) {
            throw new IllegalArgumentException(NO_NUMBER_PASSWORD_MESSAGE);
        }

        if (!UPPERCASE_PATTERN.matcher(senha).matches()) {
            throw new IllegalArgumentException(NO_UPPERCASE_PASSWORD_MESSAGE);
        }

        if (!SPECIAL_CHARACTER_PATTERN.matcher(senha).matches()) {
            throw new IllegalArgumentException(NO_SPECIAL_CHARACTER_PASSWORD_MESSAGE);
        }
    }

    @Override
    public boolean validarCredenciais(String email, String senha) {
        // Aqui você pode adicionar a lógica para validar as credenciais do usuário
        // Por exemplo, você pode verificar se o email e a senha correspondem a um usuário existente no seu banco de dados
        return false;
    }
}