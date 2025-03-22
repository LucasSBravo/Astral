package com.main;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class Personagem {
    protected String nome;
    protected String origem;
    protected String historia;
    protected List<Opcao> opcoes;

    public Personagem(String nome, String origem, String historia) {
        this.nome = nome;
        this.origem = origem;
        this.historia = historia;
        this.opcoes = new ArrayList<>();
    }

    public void apresentarHistoria(JTextArea areaTexto) {
        areaTexto.append("\nVocê escolheu: " + nome + "\n");
        areaTexto.append("Origem: " + origem + "\n");
        areaTexto.append("História: " + historia + "\n");
        areaTexto.append("-------------------------------------------\n");
    }

    public void iniciarAventura(JTextArea areaTexto, JButton[] botoes) {
        areaTexto.append("\nO que você decide fazer?\n");
        int numOpcoes = Math.min(opcoes.size(), botoes.length);

        resetarEventos(botoes);

        for (int i = 0; i < numOpcoes; i++) {
            areaTexto.append((i + 1) + ") " + opcoes.get(i).getTexto() + "\n");
            botoes[i].setText(opcoes.get(i).getTexto());
            botoes[i].setEnabled(true);
        }

        if (botoes.length > numOpcoes) {
            botoes[botoes.length - 1].setText("Sair");
            botoes[botoes.length - 1].setEnabled(true);
            botoes[botoes.length - 1].addActionListener(e -> System.exit(0));
        }

        for (int i = 0; i < numOpcoes; i++) {
            final int index = i;
            botoes[i].addActionListener(e -> {
                opcoes.get(index).executar(areaTexto);
            });
        }
    }

    protected void finalizarCiclo(JTextArea areaTexto, JButton[] botoes) {
        areaTexto.append("\nDeseja voltar ao menu inicial?\n");

        resetarEventos(botoes);

        botoes[0].setText("Voltar ao Menu");
        botoes[0].setEnabled(true);
        botoes[0].addActionListener(e -> {
            limparOpcoes();
            com.main.Principal.menu(areaTexto, botoes);
        });

        botoes[botoes.length - 1].setText("Sair");
        botoes[botoes.length - 1].setEnabled(true);
        botoes[botoes.length - 1].addActionListener(e -> System.exit(0));

        for (int i = 1; i < botoes.length - 1; i++) {
            botoes[i].setEnabled(false);
            botoes[i].setText("");
        }
    }

    protected void resetarEventos(JButton[] botoes) {
        for (JButton botao : botoes) {
            for (ActionListener al : botao.getActionListeners()) {
                botao.removeActionListener(al);
            }
        }
    }
    
    
    protected void adicionarOpcao(String texto, Consumer<JTextArea> acao) {
        opcoes.add(new Opcao(texto, acao));
    }

    protected void limparOpcoes(){
        opcoes.clear();
    }
}