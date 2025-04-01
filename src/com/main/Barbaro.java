package com.main;

import javax.swing.JButton;
import javax.swing.JTextArea;

public class Barbaro extends Personagem {
	private JButton[] botoes;

    public Barbaro(JButton[] botoes) {
        super("Bárbaro", "Tribo das Montanhas Uivantes",
                "Você é um guerreiro das montanhas, conhecido por sua força e fúria. Sua tribo foi atacada e você busca vingança.");
        
        this.botoes = botoes;

        adicionarOpcao("Seguir o rastro dos invasores", area -> {
            Principal.exibirDialogo("Você encontra um acampamento inimigo e se prepara para o ataque...");
            adicionarOpcoesAtaque(area, botoes);
        });

        adicionarOpcao("Buscar aliados nas aldeias próximas", area -> {
            Principal.exibirDialogo("Você viaja para a aldeia vizinha em busca de ajuda...");
            adicionarOpcoesAldeia(area, botoes);
        });

    }
 

	private void adicionarOpcoesAtaque(JTextArea area, JButton[] botoes) {
        limparOpcoes();
        
        adicionarOpcao("Atacar o acampamento diretamente", areaTexto -> {
            Principal.exibirDialogo( "Você entra em fúria e ataca com toda a sua força!");
            finalizarCiclo(areaTexto, botoes);
        });

        adicionarOpcao("Infiltrar-se sorrateiramente", areaTexto -> {
            Principal.exibirDialogo("Você se move silenciosamente, eliminando os guardas um a um.");
            finalizarCiclo(areaTexto, botoes);
        });
        
        iniciarAventura(area, botoes);
    }


	private void adicionarOpcoesAldeia(JTextArea area, JButton[] botoes) {
        limparOpcoes();
        
        adicionarOpcao("Pedir ajuda ao líder da aldeia", areaTexto -> {
            Principal.exibirDialogo("O líder concorda em ajudar, mas exige uma prova de sua força.");
            finalizarCiclo(areaTexto, botoes);
        });
        
        adicionarOpcao("Buscar uma rede de informações para descobrir o motivo do ataque", areaTexto -> {
            Principal.exibirDialogo( "Você entra em uma casa aparentemente abandonada no fim da Aldeia");
            finalizarCiclo(areaTexto, botoes);
        });
        
        iniciarAventura(area, botoes);
	}
}
