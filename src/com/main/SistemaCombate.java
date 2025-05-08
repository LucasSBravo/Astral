package com.main;

import javax.swing.*;
import java.util.Random;

public class SistemaCombate {
    private int vidaJogador = 100;
    private int vidaCriatura = 80;
    private int estaminaJogador = 6;
    private final int ESTAMINA_MAX = 10;

    private final Random random = new Random();
    private final JButton[] botoes;
    private final JTextArea areaTexto;
    private final Runnable aoVencer;
    private final Runnable aoPerder;

    private int danoCriaturaAtual;
    private boolean aguardandoReacao = false;

    public SistemaCombate(JTextArea areaTexto, JButton[] botoes, Runnable aoVencer, Runnable aoPerder) {
        this.areaTexto = areaTexto;
        this.botoes = botoes;
        this.aoVencer = aoVencer;
        this.aoPerder = aoPerder;
        iniciar();
    }

    private void iniciar() {
        Principal.emCombate = true;
        Principal.exibirDialogo("Prepare-se para o combate!");
        configurarBotoesTurnoJogador();
    }

    private void atualizarTextoEstamina() {
        areaTexto.setText("Estamina: " + estaminaJogador + " | Vida: " + vidaJogador + " | Vida da Criatura: " + vidaCriatura);
    }

    private void configurarBotoesTurnoJogador() {
        String[] acoes = {"Atacar", "Recuperar"};
        for (int i = 0; i < botoes.length && i < acoes.length; i++) {
            JButton botao = botoes[i];
            String acao = acoes[i];
            botao.setVisible(true);
            botao.setText(acao);
            for (var l : botao.getActionListeners()) botao.removeActionListener(l);
            botao.addActionListener(e -> executarAcaoJogador(acao));
        }

        for (int i = acoes.length; i < botoes.length; i++) {
            botoes[i].setVisible(false);
        }

        atualizarTextoEstamina();
    }

    private void configurarBotoesReacao() {
        String[] reacoes = {"Defender", "Esquivar", "Recuperar"};
        for (int i = 0; i < botoes.length && i < reacoes.length; i++) {
            JButton botao = botoes[i];
            String reacao = reacoes[i];
            botao.setVisible(true);
            botao.setText(reacao);
            for (var l : botao.getActionListeners()) botao.removeActionListener(l);
            botao.addActionListener(e -> executarReacao(reacao));
        }

        for (int i = reacoes.length; i < botoes.length; i++) {
            botoes[i].setVisible(false);
        }

        atualizarTextoEstamina();
    }

    private void executarAcaoJogador(String acao) {
        switch (acao) {
            case "Atacar" -> {
                if (gastarEstamina(3)) atacar();
            }
            case "Recuperar" -> {
                estaminaJogador = Math.min(ESTAMINA_MAX, estaminaJogador + 4);
                Principal.exibirDialogo("Você recuperou estamina!");
                turnoCriatura();
            }
        }
    }

    private boolean gastarEstamina(int custo) {
        if (estaminaJogador >= custo) {
            estaminaJogador -= custo;
            return true;
        } else {
            Principal.exibirDialogo("Você está sem estamina suficiente!");
            return false;
        }
    }

    private void atacar() {
        int chance = random.nextInt(100);
        if (chance < 70) {
            int dano = 15 + random.nextInt(10);
            vidaCriatura -= dano;
            Principal.exibirDialogo("Você acerta a criatura causando " + dano + " de dano!");
        } else {
            Principal.exibirDialogo("Seu ataque errou!");
            estaminaJogador = Math.max(0, estaminaJogador - 1); // penalidade por erro
        }

        if (vidaCriatura <= 0) {
            Principal.exibirDialogo("Você derrotou a criatura!");
            Principal.botaoOpcoesCombate.setVisible(false);
            Principal.emCombate = false;
            aoVencer.run();
        } else {
            turnoCriatura();
        }
    }

    private void turnoCriatura() {
        int acao = random.nextInt(3);
        aguardandoReacao = true;
        danoCriaturaAtual = 10 + random.nextInt(10);

        switch (acao) {
            case 0 -> Principal.exibirDialogo("A criatura ataca com garras sombrias!");
            case 1 -> {
                Principal.exibirDialogo("A criatura lança uma sombra venenosa!");
                danoCriaturaAtual += 5;
            }
            case 2 -> {
                Principal.exibirDialogo("A criatura uiva, desorientando você.");
                danoCriaturaAtual = 0;
            }
        }

        configurarBotoesReacao();
    }

    private void executarReacao(String reacao) {
        if (!aguardandoReacao) return;

        int chance = random.nextInt(100);
        boolean evitou = false;

        switch (reacao) {
            case "Defender" -> {
                if (!gastarEstamina(2)) return;
                if (chance < 60) {
                    danoCriaturaAtual /= 2;
                    Principal.exibirDialogo("Você se defendeu e reduziu o dano!");
                    estaminaJogador = Math.min(ESTAMINA_MAX, estaminaJogador + 3);
                } else {
                    Principal.exibirDialogo("Defesa falhou!");
                    estaminaJogador = Math.max(0, estaminaJogador - 1);
                }
            }
            case "Esquivar" -> {
                if (!gastarEstamina(2)) return;
                if (chance < 40) {
                    danoCriaturaAtual = 0;
                    Principal.exibirDialogo("Você se esquivou com sucesso!");
                    estaminaJogador = Math.min(ESTAMINA_MAX, estaminaJogador + 2);
                    evitou = true;
                } else {
                    Principal.exibirDialogo("Esquiva falhou!");
                    estaminaJogador = Math.max(0, estaminaJogador - 1);
                }
            }
            case "Recuperar" -> {
                estaminaJogador = Math.min(ESTAMINA_MAX, estaminaJogador + 4);
                Principal.exibirDialogo("Você recuperou estamina!");
            }
        }

        if (danoCriaturaAtual > 0 && !evitou) {
            vidaJogador -= danoCriaturaAtual;
            Principal.exibirDialogo("Você recebeu " + danoCriaturaAtual + " de dano!");
        }

        aguardandoReacao = false;
        atualizarTextoEstamina();

        if (vidaJogador <= 0) {
            Principal.exibirDialogo("Você foi derrotado pela criatura...");
            Principal.emCombate = false;
            Principal.botaoOpcoesCombate.setVisible(false);
            aoPerder.run();
        } else {
            configurarBotoesTurnoJogador(); // volta turno do jogador
        }
    }

    public void reexibirBotoes() {
        if (aguardandoReacao) {
            configurarBotoesReacao();
        } else {
            configurarBotoesTurnoJogador();
        }
    }
}
