package com.main;

import java.util.Random;

public class CriaturaSombria implements Inimigo {
    private final Random random = new Random();

    @Override
    public String getNome() {
        return "criatura sombria";
    }

    @Override
    public int calcularDano(int tipo) {
        int dano = 10 + random.nextInt(10);
        if (tipo == 1) dano += 5;     // sombra venenosa
        if (tipo == 2) dano = 0;      // uivo
        return dano;
    }

    @Override
    public String gerarDescricaoAtaque(int tipo) {
        return switch (tipo) {
            case 0 -> "A criatura ataca com garras sombrias!";
            case 1 -> "A criatura lança uma sombra venenosa!";
            case 2 -> "A criatura uiva, desorientando você.";
            default -> "A criatura ataca!";
        };
    }

    @Override
    public void aplicarEfeitoEspecial(int tipoAtaque, SistemaCombate combate) {
        if (tipoAtaque == 2) { // Uivo
            combate.reduzirEstaminaJogador(1);
        }
    }

}
