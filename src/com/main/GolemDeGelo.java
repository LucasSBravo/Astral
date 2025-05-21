package com.main;

import java.util.Random;

public class GolemDeGelo implements Inimigo {
    private final Random random = new Random();

    @Override
    public String getNome() {
        return "Criatura de gelo";
    }

    @Override
    public int calcularDano(int tipo) {
        int dano = 15 + random.nextInt(6);
        if (tipo == 2) dano = 0; // rugido atordoa
        return dano;
    }

    @Override
    public String gerarDescricaoAtaque(int tipo) {
        return switch (tipo) {
            case 0 -> "A Criatura de gelo golpeia com um braço de gelo!";
            case 1 -> "A Criatura de gelo dispara espinhos congelantes!";
            case 2 -> "A Criatura de gelo ergue uma muralha de gelo!";
            default -> "A Criatura de gelo ataca!";
        };
    }

    @Override
    public void aplicarEfeitoEspecial(int tipoAtaque, SistemaCombate combate) {
        if (tipoAtaque == 2) { // Muralha de gelo
            combate.setReduzirDanoProximoAtaque(true);
        }
    }

}
