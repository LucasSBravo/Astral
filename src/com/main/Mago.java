package com.main;

import javax.swing.*;

public class Mago extends Personagem {
    private final JButton[] botoes;

    public Mago(JButton[] botoes) {
        super("Mago", "Torre Arcana de Eldoria", "Você foi escolhido para recuperar um artefato mágico perdido.");
        this.botoes = botoes;
    }

    @Override
    public void iniciarAventura(JTextArea areaTexto, JButton[] botoes) {
        apresentarInicio(areaTexto);
    }

    private void apresentarInicio(JTextArea areaTexto) {
        limparOpcoes();

        adicionarOpcao("Partir em missão", area -> {
            Principal.exibirDialogo("Você parte para a jornada...");
            GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "floresta"));
            continuarAventura("floresta", areaTexto, botoes);
        });

        adicionarOpcao("Consultar o Arquimago", area -> {
            Principal.exibirDialogo("O Arquimago revela segredos perigosos...");
            GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "arquimago"));
            continuarAventura("arquimago", areaTexto, botoes);
        });

        super.iniciarAventura(areaTexto, botoes);
    }

    @Override
    public void continuarAventura(String etapa, JTextArea areaTexto, JButton[] botoes) {
        limparOpcoes(); // Remove opções anteriores para evitar duplicações

        switch (etapa) {
            case "inicio":
                apresentarInicio(areaTexto);
                break;

            case "floresta":
                Principal.exibirDialogo("Você entra na floresta encantada, onde energias mágicas fluem pelo ar.\n");
                adicionarOpcao("Consultar o grimório antigo", txt -> {
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "arquimago"));
                    continuarAventura("arquimago", areaTexto, botoes);
                    Principal.exibirDialogo("Você aprende um novo feitiço poderoso!\n");//criar botao de proxima escolha
                });
                adicionarOpcao("Explorar ruínas mágicas", txt -> {
                    areaTexto.append("Você encontra uma relíquia ancestral.\n");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "arquimago"));
                    continuarAventura("arquimago", areaTexto, botoes);
                });
                super.iniciarAventura(areaTexto, botoes);
                break;

            case "arquimago":
                Principal.exibirDialogo("Você chega à torre do Arquimago. Ele te observa com curiosidade.\n");
                adicionarOpcao("Pedir ensinamentos", txt -> {
                    areaTexto.append("O Arquimago decide te treinar. Você se torna mais poderoso!\n");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "fim"));
                    finalizarCiclo(areaTexto, botoes, new EstadoJogo("Mago", "fim"));
                });
                adicionarOpcao("Desafiá-lo para um duelo", txt -> {
                    areaTexto.append("Após um duelo épico, você vence! Agora você é o novo Arquimago.\n");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "fim"));
                    finalizarCiclo(areaTexto, botoes, new EstadoJogo("Mago", "fim"));
                });
                super.iniciarAventura(areaTexto, botoes);
                break;

            case "fim":
                Principal.exibirDialogo("Sua jornada como Mago chegou ao fim... mas muitas aventuras ainda o aguardam.\n");
                finalizarCiclo(areaTexto, botoes, new EstadoJogo("Mago", "fim"));
                break;

            default:
                Principal.exibirDialogo("Progresso desconhecido ou corrompido. Iniciando do começo...\n");
                apresentarInicio(areaTexto);
                break;
        }
    }

    @Override
    public void retomarProgresso(JTextArea areaTexto, EstadoJogo estado, JButton[] botoes) {
        String etapa = estado.getEtapa();

        switch (etapa) {
            case "inicio" -> apresentarInicio(areaTexto);
            case "floresta", "arquimago", "fim" -> continuarAventura(etapa, areaTexto, botoes);
            default -> apresentarInicio(areaTexto);
        }
    }
}
