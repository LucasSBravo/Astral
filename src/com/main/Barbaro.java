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
                    EstadoJogo estado = GerenciadorProgresso.carregarProgresso();
                    //para salvar uma escolha precisa dessa estrutura
                    if (estado == null) {
                        estado = new EstadoJogo("Bárbaro", "combate");
                    } else {
                        estado.setProximaEtapa("combate");
                    }
                    GerenciadorProgresso.salvarProgresso(estado);
                    //
                    continuarAventura("combate", areaTexto, botoes);
                });
                super.iniciarAventura(areaTexto, botoes);
                break;
            case "combate": 
            for (JButton botao : botoes) {
             botao.setVisible(false);
                 }
            Principal.exibirDialogo("A criatura se revela");
            esperarDialogoEExecutar(() -> iniciarCombate(areaTexto));
                break;
            case "montanha": 
            areaTexto.setText("");
            Principal.exibirDialogo("*Após o combate você decide continuar a sua jornada*");
            Principal.exibirDialogo("Você chega na subida da montanha");
            Principal.exibirDialogo("*Você se depara com uma escalada voraz que se sustenta até o topo do monte");
            //talvez uma cinemática aqui
            Principal.exibirDialogo("*O personagem sobe todo o percurso da montanha");
            Principal.exibirDialogo("*uma criatura feita de neve e gelo surge e bloqueia o seu caminho*");
            EstadoJogo estado = GerenciadorProgresso.carregarProgresso();
                if (estado != null && estado.getEscolha("salvouFada")) {
                    Principal.exibirDialogo("*A fada que você salvou reaparece*");
                    Principal.exibirDialogo("Fada: Os bons corações merecem justiça!\n\n Que nada seja capaz de o impedir");
                    Principal.exibirDialogo("*A cratura é derretida e evapora diante de seus olhos*");
                    continuarAventura("montaa", areaTexto, botoes);

                } else {
                    Principal.exibirDialogo("Alvar: Eu não o temo criatura nefasta!");
                    continuarAventura("gelo", areaTexto, botoes);
                }
                super.iniciarAventura(areaTexto, botoes);
                break;
            case "gelo": //*o personagem se encontra à frente de uma vasta e densa mata:
            // Oculta os botões antes de exibir a mensagem de combate
            for (JButton botao : botoes) {
                botao.setVisible(false);
                 }
                esperarDialogoEExecutar(() -> iniciarCombate2(areaTexto));
                break;

            case "montaa": 
            Principal.exibirDialogo("*Você chega ao topo e encontra pedaços do meteoro e no meio de uma cratera,uma estrela cadente*");
            Principal.exibirDialogo("*Dela uma poderosa voz ecoa*");
            Principal.exibirDialogo("Voz:aquele que ousar se aproximar realize seu maior desejo,o preço por isso será equivalente ao que seu coração anseia. ");
            Principal.exibirDialogo("Tudo que é dado à alguém será tirado de outros");
                adicionarOpcao("Fazer um desejo", txt -> {
                    Principal.exibirDialogo("Alvar: Eu quero minha filha de volta!");
                    Principal.exibirDialogo("Voz: Um desejo sincero vindo do coração não será negado à ninguém");
                    Principal.exibirDialogo("Voz: Vosso coração foi ferido por esse mundo,um pequeno anjo foi morto pela crueldade da floresta");
                    Principal.exibirDialogo("Voz: Eu enxergo Paternidade no seu coração, eu enxergo uma verdade tão pontual quanto o sol que todos os dias nasce e se põe");
                    Principal.exibirDialogo("Voz: Você terá sua filha novamente lenhador de Albour,ao custo de algo de mesmo valor");
                    Principal.exibirDialogo("*sua filha reaparece na sua frente*");
                    Principal.exibirDialogo("Elena: Papai!?");
                    Principal.exibirDialogo("Alvar: Elena!!");
                    Principal.exibirDialogo("*Você retorna a sua casa,mesmo sabendo das consequências,sua alegria sobrepõe a culpa,sua filha está de volta*");
                    Principal.exibirDialogo("*Você e elena retornam ao seu lar,uma antiga lápide é partida ao meio com um golpe do machado*");
                    Principal.exibirDialogo("Aproveite os seu dias,Nobre lenhador, dê valor a cada segundo");
                    Principal.exibirDialogo("Sua pequenina retornou... \n\n *Fim de jogo");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "fim"));
                    continuarAventura("fim", areaTexto, botoes);
                });
                adicionarOpcao("Levar para o Rei", txt -> {
                    Principal.exibirDialogo("*a poderosa estrela é levada em suas mãos,o caminho é torturante,Algo segue tentado seu coração para que use o desejo para seu próprio objetivo.*");
                    Principal.exibirDialogo("*porém você segue com seus olhos voltados para  o castelo,sua mente está limpa como as planícies.*");
                    Principal.exibirDialogo("Rei: Você se provou digno,Ilvar.");
                    Principal.exibirDialogo("Retorne a sua casa,meus Guardas levarão sua recompensa.");
                    Principal.exibirDialogo("Rei: AGORA SUMA!!");
                    Principal.exibirDialogo("*Ao sair do castelo,tudo que seu coração pede é um lar,talvez uma pequena cabana já cansada do luto que a envolve*");
                    Principal.exibirDialogo("*Uma casa velha com uma lápide ao lado é tudo que te restou*");
                    Principal.exibirDialogo("*Você se ajoelha ao túmulo*");
                    Principal.exibirDialogo("Alvar: Ninguém deve sofrer a dor que eu sofri no dia que eu te perdi");
                    Principal.exibirDialogo("Fim de jogo");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "fim"));
                    continuarAventura("fim", areaTexto, botoes);
                });
                super.iniciarAventura(areaTexto, botoes);
                break;

            case "fim":
                Principal.exibirDialogo("Obrigado por jogar!!!");
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
            case "floresta", "arquimago", "fada","libertar","frente","combate", "gelo", "montanha", "montaa", "morte", "fim" -> continuarAventura(etapa, areaTexto, botoes);
            default -> apresentarInicio(areaTexto);
        }
    }

    private void iniciarCombate(JTextArea areaTexto) {
        limparOpcoes();
    
        new SistemaCombate(areaTexto, botoes,
            () -> {
                EstadoJogo estado = GerenciadorProgresso.carregarProgresso();
                if (estado == null) {
                    estado = new EstadoJogo("Bárbaro", "montanha");
                } else {
                    estado.setEtapa("montanha"); // ou estado.setProximaEtapa("montanha") se preferir manter o método novo
                }
                GerenciadorProgresso.salvarProgresso(estado);
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
