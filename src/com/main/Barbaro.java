package com.main;

import javax.swing.*;

public class Barbaro extends Personagem {
    private final JButton[] botoes;

    public Barbaro(JButton[] botoes) {
        super("Bárbaro", "Tribo das Montanhas Uivantes", "Você busca vingança após um ataque à sua tribo.");
        this.botoes = botoes;
    }

    @Override
    public void iniciarAventura(JTextArea areaTexto, JButton[] botoes) {
        apresentarInicio(areaTexto);
    }

    private void apresentarInicio(JTextArea areaTexto) {
        limparOpcoes();

        adicionarOpcao("Seguir os invasores", area -> {
            Principal.exibirDialogo("Você encontra o acampamento inimigo.");
            GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "ataque"));
            continuarAventura("ataque", areaTexto, botoes);
        });

        adicionarOpcao("Buscar aliados", area -> {
            Principal.exibirDialogo("Você chega à aldeia mais próxima.");
            GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "aldeia"));
            continuarAventura("aldeia", areaTexto, botoes);
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

            case "aldeia":
                areaTexto.setText("Você chega a uma aldeia em chamas, atacada por saqueadores.\n");
                adicionarOpcao("Proteger os aldeões", txt -> {
                    areaTexto.append("Você derrota os saqueadores e se torna herói da aldeia!\n");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "montanha"));
                    continuarAventura("montanha", areaTexto, botoes);
                });
                adicionarOpcao("Ignorar e seguir viagem", txt -> {
                    areaTexto.append("Você ignora o pedido de ajuda... os aldeões te olham com desprezo.\n");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "montanha"));
                    continuarAventura("montanha", areaTexto, botoes);
                });
                super.iniciarAventura(areaTexto, botoes);
                break;

            case "ataque":
                areaTexto.setText("Você encontra o acampamento inimigo à noite.\n");
                adicionarOpcao("Ataque frontal", txt -> {
                    areaTexto.append("Você ataca com fúria!\n");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "fim"));
                    finalizarCiclo(areaTexto, botoes, new EstadoJogo("Bárbaro", "fim"));
                });
                adicionarOpcao("Infiltração", txt -> {
                    areaTexto.append("Você elimina os guardas silenciosamente.\n");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "fim"));
                    finalizarCiclo(areaTexto, botoes, new EstadoJogo("Bárbaro", "fim"));
                });
                super.iniciarAventura(areaTexto, botoes);
                break;

            case "montanha":
                areaTexto.setText("Você escala a montanha proibida em busca da Lâmina dos Deuses.\n");
                adicionarOpcao("Desafiar o guardião da montanha", txt -> {
                    areaTexto.append("Após uma batalha brutal, você conquista a espada lendária!\n");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "fim"));
                    finalizarCiclo(areaTexto, botoes, new EstadoJogo("Bárbaro", "fim"));
                });
                adicionarOpcao("Buscar um caminho alternativo", txt -> {
                    areaTexto.append("Você evita o combate, mas não encontra a espada. Seu destino é incerto...\n");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "fim"));
                    finalizarCiclo(areaTexto, botoes, new EstadoJogo("Bárbaro", "fim"));
                });
                super.iniciarAventura(areaTexto, botoes);
                break;

            case "fim":
                areaTexto.setText("Sua jornada como Bárbaro termina aqui. A lenda viverá para sempre...\n");
                finalizarCiclo(areaTexto, botoes, new EstadoJogo("Bárbaro", "fim"));
                break;

            default:
                areaTexto.setText("Progresso inválido ou corrompido. Reiniciando a história...\n");
                apresentarInicio(areaTexto);
                break;
        }
    }

    @Override
    public void retomarProgresso(JTextArea areaTexto, EstadoJogo estado, JButton[] botoes) {
        String etapa = estado.getEtapa();

        switch (etapa) {
            case "inicio" -> apresentarInicio(areaTexto);
            case "ataque", "aldeia", "montanha", "fim" -> continuarAventura(etapa, areaTexto, botoes);
            default -> apresentarInicio(areaTexto);
        }
    }
}
