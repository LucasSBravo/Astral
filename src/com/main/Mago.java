package com.main;

import javax.swing.*;

public class Mago extends Personagem {
    private final JButton[] botoes;

    public Mago(JButton[] botoes) {
        super("Aielo", "Biscon", "Mago");
        this.botoes = botoes;
    }

    @Override
    public void iniciarAventura(JTextArea areaTexto, JButton[] botoes) {
        apresentarInicio(areaTexto);
    }

    private void apresentarInicio(JTextArea areaTexto) {
        limparOpcoes();
        Principal.mudarImagemDeFundo("src/com/main/Resources/Imagens/Cenario/CasteloAstral.jpg");
        Principal.exibirDialogo("Nas terras distantes de Feromah, uma antiga lenda sobre um poderoso meteoro místico rondava o cotidiano.");
        Principal.exibirDialogo("Até que em um momento...");
        Principal.exibirDialogo("Este poderoso meteoro foi avistado pelos reis de cada reino.");
        Principal.exibirDialogo("Que envoltos pela ganância designaram você para buscar este poderoso artefato.");
       //Cenário - você inicia a história no reino de Feromah,sendo convocado pelo rei antros

       Principal.exibirDialogo("Aielo: Pelo quê fui convocado, Vossa Majestade?");
       Principal.exibirDialogo("Rei Antros: O grande meteoro previsto pelo mago Belchior foi localizado no alto do Monte Cassian, ordeno que traga-o para mim de um jeito ou de outro.");
       Principal.exibirDialogo("Vá depressa Aielo, ou garanto que Adoniran vai expurgá-lo sob minhas ordens!");
       Principal.exibirDialogo("*Você olha com medo para o rei e segue caminho para a porta do castelo*");
       Principal.exibirDialogo("Você caminha até o portão principal e se encontra a porta do reino:");
        adicionarOpcao("Ir para a rota que passa pela floresta", area -> {
            //atualizarLocalizacao(areaTexto, "Floresta Encantada");
            GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "floresta"));
            continuarAventura("floresta", areaTexto, botoes);
        });

        adicionarOpcao("Falar com o arquimago", area -> {
            //atualizarLocalizacao(areaTexto, "Floresta Encantada");
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

            case "arquimago":  
            Principal.mudarImagemDeFundo("src/com/main/Resources/Imagens/Cenario/BibliotecaAstral.jpg");
                Principal.exibirDialogo("Arquimago: O que o rei te disse, Aielo?");
                Principal.exibirDialogo("Aielo: Mestre Adoniran, ele me informou sobre um misterioso artefato que caiu no alto do Monte Cassian...");
                adicionarOpcao("Algum feiticeiro conseguiu interceptá-lo?", txt -> {
                    Principal.exibirDialogo("Aielo: Eu quero saber mais sobre a Meteoro mestre, algum feiticeiro conseguiu interceptá-lo?");
                    Principal.exibirDialogo("Arquimago: Meu querido, infelizmente os feiticeiros de Cassian não foram capazes de atingi-lo, e por consequência também não imagino que saíbam mais que nós.");
                    Principal.exibirDialogo("*Você se despede do mestre adoniran com um olhar de respeito e segue rota pela floresta");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "floresta"));
                    continuarAventura("floresta", areaTexto, botoes);
                });
                adicionarOpcao("Onde está o meteoro?", txt -> {
                    Principal.exibirDialogo("Aielo: Eu quero saber mais sobre o Território onde está o meteoro, Mestre.");
                    Principal.exibirDialogo("Arquimago: minha sabedoria nunca chegou a essas terras meu caro, mas pelos estudos do Mestre Jobim, a Magia Umbra habita a região, Não conheço nada além que eu possa lhe informar, meu caro pupilo.");
                    Principal.exibirDialogo("*Você se despede do Mestre Adoniran com um olhar de respeito e segue rota pela floresta*");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "floresta"));
                    continuarAventura("floresta", areaTexto, botoes);
                });
                super.iniciarAventura(areaTexto, botoes);
                break;    

            case "floresta": 
            Principal.mudarImagemDeFundo("src/com/main/Resources/Imagens/Cenario/FlorestaAstral.jpg");
                Principal.exibirDialogo("Você se encontra à frente de uma vasta e densa mata:");
                adicionarOpcao("Seguir pela trilha", txt -> {
                    Principal.exibirDialogo("Você se depara com um caminho, seu olhar denso persegue cada detalhe do ambiente. ");
                    Principal.exibirDialogo("Apenas o som do silêncio o faz companhia, até que um barulho surge de um dos arbustos e acaba com a paz presente no ambiente.");
                    Principal.exibirDialogo("*Seus ossos balançam dentro do seu corpo, o medo toma conta de sua consciência, mas a coragem também passa por seu raciocínio*");
                    Principal.exibirDialogo("Aielo: E-eu Consigo!");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "fada"));
                    continuarAventura("fada", areaTexto, botoes);
                });
                adicionarOpcao("Seguir em frente", txt -> {
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "frente"));
                    continuarAventura("frente", areaTexto, botoes);
                });
                super.iniciarAventura(areaTexto, botoes);
                break;

            case "fada":
            Principal.mudarImagemDeFundo("src/com/main/Resources/Imagens/Cenario/FlorestaAstral.jpg");
                adicionarOpcao("Conferir o arbusto", txt ->{
                    Principal.exibirDialogo("*Você encontra uma pequena fada presa entre os espinhos que estavam presentes no arbusto*");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "libertar"));
                    continuarAventura("libertar", areaTexto, botoes);
                });
                adicionarOpcao("Seguir em frente", txt -> {
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "frente"));
                    continuarAventura("frente", areaTexto, botoes);
                });
                super.iniciarAventura(areaTexto, botoes);
                break;

            case "libertar": 
            Principal.mudarImagemDeFundo("src/com/main/Resources/Imagens/Cenario/FlorestaAstral.jpg");
            Principal.exibirDialogo("A pequena fada se encontra debilitada e inconsciente. Suas mãos se arranham nos espinhos para conseguir tirar ela dali.");
                adicionarOpcao("Escondê-la em algum arbusto que não tenha espinhos", txt -> {
                    Principal.exibirDialogo("Uma voz misteriosa ressoa: Você fez uma boa ação pequeno feiticeiro.");
                     EstadoJogo estado = new EstadoJogo("Mago", "frente");
                    estado.setEscolha("salvouFada", true); // Registrando a decisão no mapa
                    GerenciadorProgresso.salvarProgresso(estado);
                    continuarAventura("frente", areaTexto, botoes);
                });
                adicionarOpcao("Deixar-la na grama mais próxima", txt -> {
                    Principal.exibirDialogo("Uma voz misteriosa ressoa: Você fez uma boa ação pequeno feiticeiro.");
                    EstadoJogo estado = new EstadoJogo("Mago", "frente");
                    estado.setEscolha("salvouFada", true); // Registrando a decisão no mapa
                    GerenciadorProgresso.salvarProgresso(estado);
                    continuarAventura("frente", areaTexto, botoes);
                });
                super.iniciarAventura(areaTexto, botoes);
                break;

            case "frente": 
            Principal.exibirDialogo("\"O tempo é precioso, não posso perdê-lo\" Diz Aielo para si.");
            Principal.mudarImagemDeFundo("src/com/main/Resources/Imagens/Cenario/CombateAstral.jpg");
            Principal.exibirDialogo("*Você segue seu caminho avante rumo ao próximo ponto*");
            Principal.exibirDialogo("*Você se depara com um grande vilarejo abandonado e exilado pelo tempo*");
            Principal.exibirDialogo("O vilarejo parece desabitado, quase nulo de vida");
            Principal.exibirDialogo("*Você escuta uma voz familiar*");
            Principal.exibirDialogo("Adoniran: Meu pupilo, porque demorou tanto? Senti sua falta meu querido!");
            Principal.exibirDialogo("Aielo: Mestre?");
            Principal.exibirDialogo("*Você corre até onde o som te guia, seu coração bate eufórico*");
            Principal.exibirDialogo("Você encontra seu mestre próximo do poço da vila.");
            Principal.exibirDialogo("Adoniran: meu querido, eu deveria ter reconhecido o seu valor a muito tempo. Você já pode se tornar um mestre feiticeiro!");
            Principal.exibirDialogo("Aielo: Mas mestre, o senhor me disse que eu não estava pronto.");
                adicionarOpcao("Chegar mais perto", txt -> {
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "morte"));
                    continuarAventura("morte", areaTexto, botoes);
                });
                adicionarOpcao("Se questionar se é real", txt -> {
                    Principal.exibirDialogo("Aielo: Onde estão os seus óculos Mestre? Mestre Jobim disse que você não poderia tirar-los nunca.");
                    Principal.exibirDialogo("Adoniran: Eh M-Meu pupilo... ");
                    Principal.exibirDialogo("Adoniran/Criatura: VOCÊ TINHA QUE ESTRAGAR TUDO!");
                    EstadoJogo estado = GerenciadorProgresso.carregarProgresso();
                    //para salvar uma escolha precisa dessa estrutura
                    if (estado == null) {
                        estado = new EstadoJogo("Mago", "combate");
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
            Principal.mudarImagemDeFundo("src/com/main/Resources/Imagens/Cenario/CombateAstral.jpg");
            for (JButton botao : botoes) {
             botao.setVisible(false);
                 }
            Principal.exibirDialogo("A criatura se revela");
            Principal.exibirDialogo("Criatura: VOU MANDAR VOCÊ PRO INFERNO!");
            esperarDialogoEExecutar(() -> iniciarCombate(areaTexto));
            break;   
            
            case "montanha": 
            areaTexto.setText("");
            Principal.mudarImagemDeFundo("src/com/main/Resources/Imagens/Cenario/GeloAstral.jpg");
            Principal.exibirDialogo("*Após o combate você decide continuar a sua jornada*");
            Principal.exibirDialogo("*Você chega na subida da montanha*");
            Principal.exibirDialogo("*Você se depara com uma escalada voraz que se sustenta até o topo do monte*");
            //talvez uma cinemática aqui
            Principal.exibirDialogo("Você sobe todo o percurso da montanha utilizando os feitiços que aprendeu. Você se torna tão leve quanto o ar.");
            Principal.exibirDialogo("Uma criatura feita de neve e gelo surge e bloqueia o seu caminho!");
            Principal.exibirDialogo("Criatura de gelo: VOCÊ NÃO PASSARÁ!");
            EstadoJogo estado = GerenciadorProgresso.carregarProgresso();
                if (estado != null && estado.getEscolha("salvouFada")) {
                    Principal.exibirDialogo("*A fada que você salvou reaparece*");
                    Principal.exibirDialogo("Fada Katarina: Os inocentes e corajosos merecem respeito e proteção,\n\n Que nada seja capaz de impedir-lo Nobre Feiticeiro.");
                    Principal.exibirDialogo("*A cratura é derretida e evapora diante de seus olhos*");
                    continuarAventura("montaa", areaTexto, botoes);

                } else {
                    Principal.exibirDialogo("Aielo: Eu não o temo besta de gelo!");
                    continuarAventura("gelo", areaTexto, botoes);
                }
                super.iniciarAventura(areaTexto, botoes);
                break;
            case "gelo": //*o personagem se encontra à frente de uma vasta e densa mata:
            // Oculta os botões antes de exibir a mensagem de combate
            Principal.mudarImagemDeFundo("src/com/main/Resources/Imagens/Cenario/GeloAstral.jpg");
            for (JButton botao : botoes) {
                botao.setVisible(false);
                 }
                esperarDialogoEExecutar(() -> iniciarCombate2(areaTexto));
                break;

            case "montaa": 
            Principal.mudarImagemDeFundo("src/com/main/Resources/Imagens/Cenario/GeloAstral.jpg");
            Principal.exibirDialogo("Você chega ao topo e encontra pedaços do meteoro e no meio de uma cratera, uma estrela cadente, Tão brilhante quanto o sol");
            Principal.exibirDialogo("*Dela uma poderosa voz ecoa*");
            Principal.exibirDialogo("Voz: Aquele que ousar se aproximar, realize seu maior desejo. O preço por isso será equivalente ao que seu coração anseia. ");
            Principal.exibirDialogo("Voz: Tudo que é dado à alguém será tirado de outros");
                adicionarOpcao("Fazer um desejo", txt -> {
                    Principal.exibirDialogo("Aielo: Eu quero ser um mestre, igual o Mestre Adoniran.");
                    Principal.exibirDialogo("Voz: Um desejo sincero vindo do coração não será negado à ninguém.");
                    Principal.exibirDialogo("Voz: Seu coração foi julgado como incapaz, vejo tanta insegurança em sua alma, mas uma certa lembrança move seu palpitar.");
                    Principal.exibirDialogo("Voz: Eu enxergo um sonho que te moveu até seu mestre, o desejo de mudar todo esse mundo. Você possui uma doce inocência.");
                    Principal.exibirDialogo("Voz: Você terá o título que tanto anseia, feiticeiro de Biscon!");
                    Principal.exibirDialogo("*Um enorme brilho envolve seu ser, suas roupas se modificam e um magnífico poder grita do seu ser*");
                    Principal.exibirDialogo("Voz: Seu poder é algo que já existia, tudo que eu fiz foi levantá-lo do chão.");
                    Principal.exibirDialogo("Aielo: Eu consegui... M-Muito Obrigado!");
                    Principal.exibirDialogo("Aielo: O que eu devo fazer agora?");
                    Principal.exibirDialogo("Voz: Mude o mundo...");
                    Principal.exibirDialogo("Você retorna ao seu mestre e o revela o que ocorreu");
                    Principal.exibirDialogo("*Mestre Adoniran reverencia você, em sinal de respeito*");
                    Principal.exibirDialogo("*Vocês se abraçam em um sinal ainda maior de admiração*");
                    Principal.exibirDialogo("*Aproveite os seu dias, Nobre Feiticeiro. Seu sonho está em suas mãos, o resto do caminho, apenas você poderá escrever!");
                    Principal.exibirDialogo("*Fim de jogo");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "fim"));
                    continuarAventura("fim", areaTexto, botoes);
                });
                adicionarOpcao("Levar para o Rei", txt -> {
                    Principal.exibirDialogo("*A poderosa estrela é levada em suas mãos. O caminho é torturante, algo segue tentando seu coração para que use o desejo para sua própria vontade*");
                    Principal.exibirDialogo("*Porém você segue com seus olhos voltados para o castelo, sua mente está limpa como as planícies*");
                    Principal.exibirDialogo("*Você chega ao castelo e entrega a estrela para o Rei");
                    Principal.exibirDialogo("Rei: Você se provou digno, Aielo...");
                    Principal.exibirDialogo("Rei: Retorne ao Arquimago, meus Guardas levarão sua recompensa.");
                    Principal.exibirDialogo("Rei: AGORA SUMA!!");
                    Principal.exibirDialogo("*Ao sair do castelo, tudo que seu coração pede é um lar, talvez uma pequena biblioteca seja tudo que o resta.*");
                    Principal.exibirDialogo("*Os livros envolvem você e um ar de sabedoria o cobre*");
                    Principal.exibirDialogo("*O olhar que seu rosto produz é frio como a noite, um sonho à um palmo de distância, perdido para um rei*");
                    Principal.exibirDialogo("Você se encontra com seu mestre e ele o chama com as mãos para mais uma xícara de chá.");
                    Principal.exibirDialogo("Aielo: Obrigado meu Mestre.");
                    Principal.exibirDialogo("Mestre Adoniran: Você é um homem de honra, Nobre feiticeiro.");
                    Principal.exibirDialogo("*Fim de jogo");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "fim"));
                    continuarAventura("fim", areaTexto, botoes);
                });
                super.iniciarAventura(areaTexto, botoes);
                break;
                
            case "fim":
                Principal.exibirDialogo("Obrigado por jogar!!!");
                finalizarCiclo(areaTexto, botoes, new EstadoJogo("Mago", "fim"));
                break;

            case "morte":
                Principal.exibirDialogo("Você foi despedaçado pelo seu inimigo.\n\n *Fim de jogo.");
                finalizarCiclo(areaTexto, botoes, new EstadoJogo("Mago", "morte"));
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
            case "floresta", "arquimago","duelo", "fada","libertar","frente","combate", "gelo", "montanha", "montaa", "morte", "fim" -> continuarAventura(etapa, areaTexto, botoes); // para salvar adicione o nome do case aqui
            default -> apresentarInicio(areaTexto);
        }
    }

     private void iniciarCombate(JTextArea areaTexto) {
    limparOpcoes();

    Inimigo criaturaSombria = new CriaturaSombria();

    new SistemaCombate(areaTexto, botoes,
        () -> {
            EstadoJogo estado = GerenciadorProgresso.carregarProgresso();
            if (estado == null) {
                estado = new EstadoJogo("Mago", "montanha");
            } else {
                estado.setEtapa("montanha");
            }
            GerenciadorProgresso.salvarProgresso(estado);
            continuarAventura("montanha", areaTexto, botoes);
        },
        () -> {
            GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "morte"));
            continuarAventura("morte", areaTexto, botoes);
        },
        criaturaSombria
    );
    }

    private void iniciarCombate2(JTextArea areaTexto) {
    limparOpcoes();

    Inimigo golemDeGelo = new GolemDeGelo();

    new SistemaCombate(areaTexto, botoes,
        () -> {
            GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "montaa"));
            continuarAventura("montaa", areaTexto, botoes);
        },
        () -> {
            GerenciadorProgresso.salvarProgresso(new EstadoJogo("Bárbaro", "morte"));
            continuarAventura("morte", areaTexto, botoes);
        },
        golemDeGelo
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
