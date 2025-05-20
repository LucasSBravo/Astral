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

    /*estrutura de uma escolha
     * case "montanha": 
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
     */

    private void apresentarInicio(JTextArea areaTexto) {
        limparOpcoes();
        Principal.exibirDialogo("Nas terras distantes de Feromah, uma antiga lenda sobre um poderoso meteoro místico rondava o cotidiano.");
        Principal.exibirDialogo("Até que em um momento...");
        Principal.exibirDialogo("Este poderoso meteoro foi avistado pelos reis de cada reino.");
        Principal.exibirDialogo("Que envoltos pela ganância designaram você para buscar este poderoso artefato.");
       //Cenário 

       Principal.exibirDialogo("Alvar: pelo quê fui convocado,vossa majestade ?");
       Principal.exibirDialogo("Rei Antros: o grande meteoro previsto pelo mago Belchior foi localizado no alto do monte Cassian, ordeno que o traga para mim de um jeito ou de outro. ");
       Principal.exibirDialogo("Vá depressa, Alvar, ou garanto que vai perder mais do que apenas sua filha!");

       //foto do alvar com raiva 
       Principal.exibirDialogo("*Você olha com ódio para o rei e segue caminho para a porta do castelo*");

       Principal.exibirDialogo("Você se encontra a porta do reino:");
        adicionarOpcao("Ir para a rota que passa pela floresta", area -> {
            GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "floresta"));
            continuarAventura("floresta", areaTexto, botoes);
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
                adicionarOpcao("Eu quero saber mais sobre a Meteoro, Adoniran", txt -> {
                    Principal.exibirDialogo("Arquimago: Oh meu caro, eu sinto muito,minha curiosidade se encontra no mesmo estado que a sua.");
                    Principal.exibirDialogo("Arquimago: Não possuo o saber necessário para decifrar essa poderosa entidade, mas posso afirmar que possuí um poder nunca antes visto pelos homens");
                    Principal.exibirDialogo("*Você se despede do mestre Adoniran e segue rota pela floresta*");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "floresta"));
                    continuarAventura("floresta", areaTexto, botoes);
                });
                adicionarOpcao("Eu quero saber mais sobre o Território onde está o meteoro", txt -> {
                    Principal.exibirDialogo("Arquimago: Minha sabedoria nunca chegou a essas terras meu caro, mas pelos estudos do mestre Jobim. ");
                    Principal.exibirDialogo("Sabemos que é um território com vasta magia negra, a noite é uma terrível aliada das forças umbras que circundam o território");
                    Principal.exibirDialogo("*Você se despede do mestre Adoniran e segue rota pela floresta*");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "floresta"));
                    continuarAventura("floresta", areaTexto, botoes);
                });
                super.iniciarAventura(areaTexto, botoes); //*o personagem se despede do mestre adoniran (Arquimago) e segue rota pela floresta
                break;

            //cenário floresta    
            case "floresta": 
            Principal.exibirDialogo("*Você se encontra à frente de uma vasta e densa mata");
                adicionarOpcao("Seguir pela trilha", txt -> {
                    Principal.exibirDialogo("*O personagem se depara com um caminho, seu olhar denso persegue cada detalhe, apenas o som do silêncio o faz companhia, até que um barulho surge de um dos arbustos e acaba com a paz presente no ambiente*");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "fada"));
                    continuarAventura("fada", areaTexto, botoes);
                });
                adicionarOpcao("Seguir em frente", txt -> {
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "frente"));
                    continuarAventura("frente", areaTexto, botoes);
                });
                super.iniciarAventura(areaTexto, botoes);
                break;
            case "fada":
                adicionarOpcao("Conferir arbusto", txt -> {
                    Principal.exibirDialogo("Você encontra uma pequena Fada presa entre os espinhos que estavam presentes no arbusto");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "libertar"));
                    continuarAventura("libertar", areaTexto, botoes);
                });
                adicionarOpcao("Seguir em frente", txt -> {
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "frente"));
                    continuarAventura("frente", areaTexto, botoes);
                });
                super.iniciarAventura(areaTexto, botoes);
                break;
            case "libertar": 
            Principal.exibirDialogo("A pequena fada se encontra debilitada e inconsciente, suas mãos se arranham nos espinhos para conseguir tirar ela dali");
                adicionarOpcao("Escondê-la em algum arbusto que não tenha espinhos", txt -> {
                    Principal.exibirDialogo("Uma voz misteriosa ressoa: Você fez uma boa ação nobre caçador");
                     EstadoJogo estado = new EstadoJogo("Bárbaro", "frente");
                    estado.setEscolha("salvouFada", true); // Registrando a decisão no mapa
                    GerenciadorProgresso.salvarProgresso(estado);
                    continuarAventura("frente", areaTexto, botoes);
                });
                adicionarOpcao("Deixar-la na grama mais próxima", txt -> {
                    Principal.exibirDialogo("Uma voz misteriosa ressoa: Você fez uma boa ação nobre caçador");
                    EstadoJogo estado = new EstadoJogo("Bárbaro", "frente");
                    estado.setEscolha("salvouFada", true); // Registrando a decisão no mapa
                    GerenciadorProgresso.salvarProgresso(estado);
                    continuarAventura("frente", areaTexto, botoes);
                });
                super.iniciarAventura(areaTexto, botoes);
                break;
            case "frente": 
            Principal.exibirDialogo("O tempo é precioso, não posso perdê-lo");
            Principal.exibirDialogo("*Você se depara com um grande vilarejo abandonado e exilado pelo tempo*");
            Principal.exibirDialogo("O vilarejo parece desabitado, quase nulo de vida");
            Principal.exibirDialogo("*Você escuta uma voz familiar*");
            Principal.exibirDialogo("Elena: Papai, Eu estou aqui, O senhor finalmente chegou");
            Principal.exibirDialogo("Alvar: E-... Elena!?");
            Principal.exibirDialogo("*Você corre até onde o som te guia*");
            Principal.exibirDialogo("*Você encontra sua filha próxima do poço da vila*");
                adicionarOpcao("Chegar mais perto (morte)", txt -> {
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "morte"));
                    continuarAventura("morte", areaTexto, botoes);
                });
                adicionarOpcao("Se questionar se é real", txt -> {
                    Principal.exibirDialogo("Alvar percebe que Elena não está usando o colar que ele lhe deu de presente");
                    Principal.exibirDialogo("Alvar: Você não é minha filha.");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "combate"));
                    continuarAventura("combate", areaTexto, botoes);
                });
                super.iniciarAventura(areaTexto, botoes);
                break;
            case "combate": 
            Principal.exibirDialogo("A criatura se revela");
            iniciarCombate(areaTexto);
                break;
            case "montanha": 
            areaTexto.setText("");
            Principal.exibirDialogo("*Após o combate você decide continuar a sua jornada*");
            Principal.exibirDialogo("Você chega na subida da montanha");
            Principal.exibirDialogo("*o personagem se depara com uma escalada voraz que se sustenta até o topo do monte");
            Principal.exibirDialogo("*o personagem chega ao topo e encontra pedaços do meteoro e no meio de uma cratera,uma estrela cadente");
            EstadoJogo estado = GerenciadorProgresso.carregarProgresso();
                if (estado != null && estado.getEscolha("salvouFada")) {
                    Principal.exibirDialogo("A fada que você salvou reaparece e concede a bênção dos ventos.");
                    continuarAventura("montaa", areaTexto, botoes);

                } else {
                    Principal.exibirDialogo("Você sente que alguma ajuda mágica poderia ter sido útil aqui...");
                    continuarAventura("dois", areaTexto, botoes);
                }
                super.iniciarAventura(areaTexto, botoes);
                break;
            case "dois": //*o personagem se encontra à frente de uma vasta e densa mata:
            Principal.exibirDialogo("combate contra golem de gelo");
                esperarDialogoEExecutar(() -> iniciarCombate2(areaTexto));
                break;

            case "montaa": 
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
            case "morte":
                Principal.exibirDialogo("Você foi moleque... Fim de jogo.");
                finalizarCiclo(areaTexto, botoes, new EstadoJogo("Bárbaro", "morte"));
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
            case "floresta", "arquimago", "fada","libertar","frente","combate", "morte", "fim" -> continuarAventura(etapa, areaTexto, botoes);
            default -> apresentarInicio(areaTexto);
        }
    }

    private void iniciarCombate(JTextArea areaTexto) {
        limparOpcoes();
    
        new SistemaCombate(areaTexto, botoes,
            () -> {
                GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "montanha"));
                continuarAventura("montanha", areaTexto, botoes);
            },
            () -> {
                GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "morte"));
                continuarAventura("morte", areaTexto, botoes);
            }
        );
    }
    private void iniciarCombate2(JTextArea areaTexto) {
        limparOpcoes();
    
        new SistemaCombate(areaTexto, botoes,
            () -> {
                GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "montaa"));
                continuarAventura("montaa", areaTexto, botoes);
            },
            () -> {
                GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "morte"));
                continuarAventura("morte", areaTexto, botoes);
            }
        );
    }

    public void esperarDialogoEExecutar(Runnable acao) {
    if (Principal.caixaDialogo != null) {
        Principal.caixaDialogo.executarAoTerminarFila(acao);
    } else {
        acao.run(); // fallback se não houver caixaDialogo
    }
}


    
}
