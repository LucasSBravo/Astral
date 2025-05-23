package com.main;

public interface Inimigo {
    String getNome();
    int calcularDano(int tipoAtaque);
    String gerarDescricaoAtaque(int tipoAtaque);

    default void aplicarEfeitoEspecial(int tipoAtaque, SistemaCombate combate) {
    // Nenhum efeito especial por padrão
}

}
