package com.main;

import javax.swing.JButton;
import javax.swing.JTextArea;

public class Mago extends Personagem {
    @SuppressWarnings("unused")
	private JButton[] botoes;

    public Mago(JButton[] botoes) {
        super("Mago", "Torre Arcana de Eldoria",
                "Os magos da Torre Arcana passam décadas estudando os segredos do universo. Você foi escolhido para recuperar um artefato antigo.");

        this.botoes = botoes;

        adicionarOpcao("Partir imediatamente para a missão", area -> {
            area.append("\nVocê enfrenta perigos e chega a uma encruzilhada...\n");
            adicionarOpcoesEncruzilhada(area, botoes);
        });
        
        
        adicionarOpcao("Consultar o Arquimago", area -> {
            area.append("\nO Arquimago lhe conta sobre um perigo oculto...\n");
            adicionarOpcoesArquimago(area, botoes);
        });
    }
    

    private void adicionarOpcoesEncruzilhada(JTextArea area, JButton[] botoes) {
        limparOpcoes(); 

        adicionarOpcao("Explorar a floresta sombria", areaTexto -> {
            areaTexto.append("\nVocê encontra criaturas sombrias e precisa lutar...\n");
            finalizarCiclo(areaTexto, botoes);
        });

        adicionarOpcao("Seguir pelo caminho das montanhas", areaTexto -> {
            areaTexto.append("\nO caminho é perigoso, mas você avista o artefato ao longe...\n");
            finalizarCiclo(areaTexto, botoes);
        });

        iniciarAventura(area, botoes);
    }
    
    
    private void adicionarOpcoesArquimago(JTextArea area, JButton[] botoes) {
        limparOpcoes();
        
        adicionarOpcao("Pedir ajuda ao Arquimago", areaTexto -> {
            areaTexto.append("\nO Arquimago concorda em ajudá-lo, mas exige algo em troca...\n");
            finalizarCiclo(areaTexto, botoes);
        });

        adicionarOpcao("Investigar o perigo sozinho", areaTexto -> {
            areaTexto.append("\nVocê parte em uma missão solitária e perigosa...\n");
            finalizarCiclo(areaTexto, botoes);
        });
        
        iniciarAventura(area, botoes);
    }

}