package com.main;

import javax.swing.*;

public class Barbaro extends Personagem {
    private final JButton[] botoes;

    public Barbaro(JButton[] botoes) {
        super("Alvar", "Feromah");
        this.botoes = botoes;
    }

    @Override
    public void iniciarAventura(JTextArea areaTexto, JButton[] botoes) {
        apresentarInicio(areaTexto);
    }

    private void apresentarInicio(JTextArea areaTexto) {
        limparOpcoes();
        Principal.exibirDialogo("Nas terras distantes de Feromah,uma antiga lenda sobre um poderoso meteoro místico rondava o cotidiano.");
        Principal.exibirDialogo("Até que em um momento...");
        Principal.exibirDialogo("Este poderoso meteoro foi avistado pelos reis de cada reino.");
        Principal.exibirDialogo("Que envoltos pela ganância designaram você para buscar este poderoso artefato.");
       //Cenário 

       Principal.exibirDialogo("Alvar: pelo quê fui convocado,vossa majestade ?");
       Principal.exibirDialogo("Rei Antros: o grande meteoro previsto pelo mago Belchior foi localizado no alto do monte  Cassian,ordeno que o traga para mim de um jeito ou de outro,Alvar,vá depressa,ou garanto que vai perder mais do que apenas sua filha!");
       Principal.exibirDialogo("Você se encontra a porta do reino:");
        adicionarOpcao("Ir para a rota que passa pela floresta", area -> {
            Principal.exibirDialogo("Você encontra o acampamento inimigo.");
            GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "ataque"));
            continuarAventura("ataque", areaTexto, botoes);
        });

        adicionarOpcao("Falar com o arquimago", area -> {
            Principal.exibirDialogo("Arquimago: no que posso ajudar meu caro senhor?");
            GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "arquimago"));
            continuarAventura("arquimago", areaTexto, botoes);
        });

        super.iniciarAventura(areaTexto, botoes);
    }

    @Override
    public void continuarAventura(String etapa, JTextArea areaTexto, JButton[] botoes) {
        limparOpcoes(); // Limpa opções anteriores

        switch (etapa) {
            case "inicio":
                apresentarInicio(areaTexto);
                break;

            case "arquimago":
                adicionarOpcao("Eu quero saber mais sobre a Meteoro ,Adoniran", txt -> {
                    Principal.exibirDialogo("oh meu caro,eu sinto muito,minha curiosidade se encontra no mesmo estado que a sua.");
                    Principal.exibirDialogo("Não possuo o saber necessário para decifrar essa poderosa entidade,mas posso afirmar que possuí um poder nunca antes visto pelos homens");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "montanha"));
                    continuarAventura("montanha", areaTexto, botoes);
                });
                adicionarOpcao("Ignorar e seguir viagem", txt -> {
                    Principal.exibirDialogo("Você ignora o pedido de ajuda... os aldeões te olham com desprezo.\n");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "montanha"));
                    continuarAventura("montanha", areaTexto, botoes);
                });
                super.iniciarAventura(areaTexto, botoes);
                break;

            case "ataque":
                Principal.exibirDialogo("Você encontra o acampamento inimigo à noite.\n");
                adicionarOpcao("Ataque frontal", txt -> {
                    Principal.exibirDialogo("Você ataca com fúria!\n");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "fim"));
                    finalizarCiclo(areaTexto, botoes, new EstadoJogo("Bárbaro", "fim"));
                });
                adicionarOpcao("Infiltração", txt -> {
                    Principal.exibirDialogo("Você elimina os guardas silenciosamente.\n");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "fim"));
                    finalizarCiclo(areaTexto, botoes, new EstadoJogo("Bárbaro", "fim"));
                });
                super.iniciarAventura(areaTexto, botoes);
                break;

            case "montanha":
                Principal.exibirDialogo("Você escala a montanha proibida em busca da Lâmina dos Deuses.\n");
                adicionarOpcao("Desafiar o guardião da montanha", txt -> {
                    Principal.exibirDialogo("Após uma batalha brutal, você conquista a espada lendária!\n");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "fim"));
                    finalizarCiclo(areaTexto, botoes, new EstadoJogo("Bárbaro", "fim"));
                });
                adicionarOpcao("Buscar um caminho alternativo", txt -> {
                    Principal.exibirDialogo("Você evita o combate, mas não encontra a espada. Seu destino é incerto...\n");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "fim"));
                    finalizarCiclo(areaTexto, botoes, new EstadoJogo("Bárbaro", "fim"));
                });
                super.iniciarAventura(areaTexto, botoes);
                break;

            case "fim":
                Principal.exibirDialogo("Sua jornada como Bárbaro termina aqui. A lenda viverá para sempre...\n");
                finalizarCiclo(areaTexto, botoes, new EstadoJogo("Bárbaro", "fim"));
                break;

            default:
                Principal.exibirDialogo("Progresso inválido ou corrompido. Reiniciando a história...\n");
                apresentarInicio(areaTexto);
                break;
        }
    }

    @Override
    public void retomarProgresso(JTextArea areaTexto, EstadoJogo estado, JButton[] botoes) {
        String etapa = estado.getEtapa();

        switch (etapa) {
            case "inicio" -> apresentarInicio(areaTexto);
            case "ataque", "arquimago", "montanha", "fim" -> continuarAventura(etapa, areaTexto, botoes);
            default -> apresentarInicio(areaTexto);
        }
    }
}
