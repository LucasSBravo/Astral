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
            atualizarLocalizacao(areaTexto, "Floresta Encantada");
            Principal.exibirDialogo("Você parte para a jornada...");
            //areaTexto.append("Você parte para a jornada..."); escreve texto fora da caixa de diálogo
            GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "floresta"));
            continuarAventura("floresta", areaTexto, botoes);
        });

        adicionarOpcao("Consultar o Arquimago", area -> {
            atualizarLocalizacao(areaTexto, "Floresta Encantada");
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

            case "floresta": atualizarLocalizacao(areaTexto, "Floresta Encantada");
                Principal.exibirDialogo("Você entra na floresta encantada, onde energias mágicas fluem pelo ar.\n");
                adicionarOpcao("Consultar o grimório antigo", txt -> {
                    Principal.exibirDialogo("Você aprende um novo feitiço poderoso!\n");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "arquimago"));
                    continuarAventura("arquimago", areaTexto, botoes);
                });
                adicionarOpcao("Explorar ruínas mágicas", txt -> {
                    Principal.exibirDialogo("Você encontra uma relíquia ancestral.\n");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "arquimago"));
                    continuarAventura("arquimago", areaTexto, botoes);
                });
                super.iniciarAventura(areaTexto, botoes);
                break;

            case "arquimago":  atualizarLocalizacao(areaTexto, "Torre");
                Principal.exibirDialogo("Você chega à torre do Arquimago. Ele te observa com curiosidade.\n");
                adicionarOpcao("Pedir ensinamentos", txt -> {
                    Principal.exibirDialogo("O Arquimago decide te treinar. Você se torna mais poderoso!\n");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "fim"));
                    //finalizarCiclo(areaTexto, botoes, new EstadoJogo("Mago", "fim")); usado somente nas escolhas finais
                });
                adicionarOpcao("Desafiá-lo para um duelo", txt -> {
                    Principal.exibirDialogo("Após um duelo épico, você vence! Agora você é o novo Arquimago.\n");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "duelo"));
                   // finalizarCiclo(areaTexto, botoes, new EstadoJogo("Mago", "fim"));
                    continuarAventura("duelo", areaTexto, botoes);
                });
                super.iniciarAventura(areaTexto, botoes);
                break;

            case "duelo": atualizarLocalizacao(areaTexto, "Em fuga");
                Principal.exibirDialogo("Agora você deve lidar com o cadáver.\n");
                adicionarOpcao("Enterrar", txt ->{
                    Principal.exibirDialogo("Você enterra o corpo e saí correndo\n");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "duelo"));
                    finalizarCiclo(areaTexto, botoes, new EstadoJogo("Mago", "fim"));
                });
                adicionarOpcao("Correr", txt ->{
                    Principal.exibirDialogo("Você foge, e os cães farejadores te encontram\n");
                    GerenciadorProgresso.salvarProgresso(new EstadoJogo("Mago", "duelo"));
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
            case "floresta", "arquimago","duelo", "fim" -> continuarAventura(etapa, areaTexto, botoes); // para salvar adicione o nome do case aqui
            default -> apresentarInicio(areaTexto);
        }
    }
}
