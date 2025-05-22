package com.main;

import javax.swing.*;
import java.util.Random;

public class SistemaCombate {
    private int vidaJogador = 100;
    private int vidaInimigo = 1; // originalmente 110
    private int estaminaJogador = 6;
    private final int ESTAMINA_MAX = 10;

    private final Random random = new Random();
    private final JButton[] botoes;
    private final JTextArea areaTexto;
    private final Runnable aoVencer;
    private final Runnable aoPerder;
    private final Inimigo inimigo;

    private int danoInimigoAtual;
    private int tipoAtaqueAtual = -1;
    private boolean aguardandoReacao = false;
    private boolean reduzirDanoProximoAtaque = false;


    public SistemaCombate(JTextArea areaTexto, JButton[] botoes, Runnable aoVencer, Runnable aoPerder, Inimigo inimigo) {
        this.areaTexto = areaTexto;
        this.botoes = botoes;
        this.aoVencer = aoVencer;
        this.aoPerder = aoPerder;
        this.inimigo = inimigo;
        iniciar();
    }

    private void iniciar() {
        Principal.emCombate = true;
        Principal.exibirDialogo("Lute por sua vida!");
        configurarBotoesTurnoJogador();
    }

    private void atualizarTextoEstamina() {
        areaTexto.setText("Estamina: " + estaminaJogador + " | Vida: " + vidaJogador + " | Vida do " + inimigo.getNome() + ": " + vidaInimigo);
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
                turnoInimigo();
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
        if (chance < 85) {
            int dano = 15 + random.nextInt(10);
        if (reduzirDanoProximoAtaque) {
            dano /= 2;
            reduzirDanoProximoAtaque = false;
            Principal.exibirDialogo("O ataque foi parcialmente bloqueado pela muralha de gelo!\nVocê causa " + dano + " de dano!"); 
        } else { // A intenção acima era o jogdor dar menos dano no golem caso ele atacasse com muralha de gelo. Não
            Principal.exibirDialogo("Você acerta a criatura causando " + dano + " de dano!");
        }
        vidaInimigo -= dano;

        } else {
            Principal.exibirDialogo("Seu ataque errou!");
            estaminaJogador = Math.max(0, estaminaJogador - 1); // penalidade por erro
        }

        if (vidaInimigo <= 0) {
            Principal.exibirDialogo("Você derrotou o " + inimigo.getNome() + "!");
            Principal.botaoOpcoesCombate.setVisible(false);
            Principal.emCombate = false;
            areaTexto.setText("");
            for (JButton botao : botoes) {
                botao.setVisible(true);
                for (var l : botao.getActionListeners()) botao.removeActionListener(l);
                botao.setText("");
            }
            aoVencer.run();
        } else {
            turnoInimigo();
        }
    }

    private void turnoInimigo() {
        int tipoAtaque = random.nextInt(3);
        aguardandoReacao = true;
        danoInimigoAtual = inimigo.calcularDano(tipoAtaque);
        inimigo.aplicarEfeitoEspecial(tipoAtaque, this);
        Principal.exibirDialogo(inimigo.gerarDescricaoAtaque(tipoAtaque));
        // Efeitos especiais por tipo de inimigo e tipo de ataque
        if (inimigo.getNome().equals("criatura sombria") && tipoAtaque == 2) {
            estaminaJogador = Math.max(0, estaminaJogador - 1); // uivo
        }

        if (inimigo.getNome().equals("Golem de Gelo") && tipoAtaque == 2) {
            reduzirDanoProximoAtaque = true; // Muralha de Gelo ativa redução de dano
        }
        tipoAtaqueAtual = tipoAtaque;
        configurarBotoesReacao();
    }

    private void executarReacao(String reacao) {
        if (!aguardandoReacao) return;

        int chance = random.nextInt(100);
        boolean evitou = false;

        switch (reacao) {
            case "Defender" -> {
                if (!gastarEstamina(2)) return;
                if (chance < 72) {
                    danoInimigoAtual /= 2;
                    Principal.exibirDialogo("Você se defendeu e reduziu o dano!");
                    estaminaJogador = Math.min(ESTAMINA_MAX, estaminaJogador + 3);
                } else {
                    Principal.exibirDialogo("Defesa falhou!");
                    estaminaJogador = Math.max(0, estaminaJogador - 1);
                }
            }
            case "Esquivar" -> {
                if (!gastarEstamina(2)) return;
                if (chance < 55) {
                    danoInimigoAtual = 0;
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

        if (danoInimigoAtual > 0 && !evitou) {
            vidaJogador -= danoInimigoAtual;
            Principal.exibirDialogo("Você recebeu " + danoInimigoAtual + " de dano!");
            inimigo.aplicarEfeitoEspecial(tipoAtaqueAtual, this);
        }

        aguardandoReacao = false;
        atualizarTextoEstamina();

        if (vidaJogador <= 0) {
            Principal.exibirDialogo("Você foi derrotado pelo " + inimigo.getNome() + "...");
            Principal.emCombate = false;
            Principal.botaoOpcoesCombate.setVisible(false);
            areaTexto.setText("");
            for (JButton botao : botoes) {
                botao.setVisible(true);
                for (var l : botao.getActionListeners()) botao.removeActionListener(l);
                botao.setText("");
            }
            aoPerder.run();
        } else {
            configurarBotoesTurnoJogador();
        }
    }

    public void reexibirBotoes() {
        if (aguardandoReacao) {
            configurarBotoesReacao();
        } else {
            configurarBotoesTurnoJogador();
        }
    }

    public void reduzirEstaminaJogador(int qtd) {
    estaminaJogador = Math.max(0, estaminaJogador - qtd);
    }

    public void setReduzirDanoProximoAtaque(boolean valor) {
        this.reduzirDanoProximoAtaque = valor;
    }


}
