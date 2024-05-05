package br.com.authservice.core.domain.model;

public enum Permissao {
    READ("READ"),
    WRITE("WRITE"),
    DELETE("DELETE");

    private final String descricao;

    Permissao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}