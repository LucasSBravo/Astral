package com.main;

import javax.swing.*;
import java.util.Random;

public class SistemaCombate {
    private int vidaJogador = 100;
    private int vidaCriatura = 10; // originalmente 80, mas esse bicho é broken demais kkkkk
    private final Random random = new Random();

    private final JButton[] botoes;
    private final JTextArea areaTexto;
    private final Runnable aoVencer;
    private final Runnable aoPerder;

    public SistemaCombate(JTextArea areaTexto, JButton[] botoes, Runnable aoVencer, Runnable aoPerder) {
        this.areaTexto = areaTexto;
        this.botoes = botoes;
        this.aoVencer = aoVencer;
        this.aoPerder = aoPerder;
        iniciar();
    }

    private void iniciar() {
        Principal.exibirDialogo("Uma criatura sombria surge das sombras! Prepare-se para o combate!");
        configurarBotoes();
        Principal.botaoOpcoesCombate.setVisible(true);
    }

    private void configurarBotoes() {
        String[] acoes = {"Atacar", "Defender", "Esquivar"};

        for (int i = 0; i < botoes.length && i < acoes.length; i++) {
            JButton botao = botoes[i];
            String acao = acoes[i];
            botao.setVisible(true);
            botao.setText(acao);
            for (var l : botao.getActionListeners()) botao.removeActionListener(l);
            botao.addActionListener(e -> executarAcao(acao));
        }

        for (int i = acoes.length; i < botoes.length; i++) {
            botoes[i].setVisible(false);
        }
    }

    private void executarAcao(String acao) {
        switch (acao) {
            case "Atacar" -> atacar();
            case "Defender" -> defender();
            case "Esquivar" -> esquivar();
        }
    }

    private void atacar() {
        int chance = random.nextInt(100);
        System.out.println("[Debug] Chance de acerto do ataque: " + chance);
        areaTexto.append("Vida do Jogador: " + vidaJogador + " | Vida da Criatura: " + vidaCriatura + "\n");
        if (chance < 70) {
            int dano = 15 + random.nextInt(10);
            vidaCriatura -= dano;
            Principal.exibirDialogo("Você acerta a criatura causando " + dano + " de dano!");
        } else {
            Principal.exibirDialogo("Seu ataque errou!");
        }
        turnoCriatura();
    }

    private void defender() {
        int chance = random.nextInt(100);
        System.out.println("[Debug] Chance de defesa: " + chance);
        if (chance < 60) {
            Principal.exibirDialogo("Você se defendeu com sucesso e reduziu o dano!");
        } else {
            Principal.exibirDialogo("Sua defesa falhou!");
        }
        turnoCriatura();
    }

    private void esquivar() {
        int chance = random.nextInt(100);
        System.out.println("[Debug] Chance de esquiva: " + chance);
        if (chance < 40) {
            Principal.exibirDialogo("Você se esquivou com sucesso!");
        } else {
            Principal.exibirDialogo("Você falhou em esquivar!");
        }
        turnoCriatura();
    }

    private void turnoCriatura() {
        if (vidaCriatura <= 0) {
            Principal.exibirDialogo("Você derrotou a criatura!");
            Principal.botaoOpcoesCombate.setVisible(false);
            aoVencer.run();
            return;
        }

        int acao = random.nextInt(3);
        int dano = 10 + random.nextInt(10);
        boolean evitarDano = false;

        switch (acao) {
            case 0 -> Principal.exibirDialogo("A criatura ataca com garras sombrias!");
            case 1 -> {
                Principal.exibirDialogo("A criatura lança uma sombra venenosa!");
                dano += 5;
            }
            case 2 -> {
                Principal.exibirDialogo("A criatura uiva, desorientando você.");
                dano = 0;
                evitarDano = true;
            }
        }

        if (!evitarDano) {
            vidaJogador -= dano;
            Principal.exibirDialogo("Você recebeu " + dano + " de dano!");
        }

        System.out.println("[Debug] Vida do Jogador: " + vidaJogador + " | Vida da Criatura: " + vidaCriatura);

        if (vidaJogador <= 0) {
            Principal.exibirDialogo("Você foi derrotado pela criatura...");
            Principal.botaoOpcoesCombate.setVisible(false);
            aoPerder.run();
        } else {
            configurarBotoes();
        }
    }
}
