package br.com.authservice.infrastructure.repositories.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String senha;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_permissao",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "permissao_id")
    )
    private Set<PermissaoEntity> permissoes;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Set<TokenEntity> tokens;

    public TokenEntity getActiveToken() {
        return tokens.stream()
            .filter(token -> !token.isExpirado())
            .findFirst()
            .orElse(null);
    }
}