package com.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Principal {
    public static void main(String[] args) {
        JFrame janela = new JFrame("Jogo de Aventura");
        janela.setSize(600, 400);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLayout(new BorderLayout());

        JTextArea areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        janela.add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(2, 2));
        JButton[] botoes = new JButton[3];

        for (int i = 0; i < 3; i++) {
            botoes[i] = new JButton();
            painelBotoes.add(botoes[i]);
        }

        janela.add(painelBotoes, BorderLayout.SOUTH);
        janela.setVisible(true);

        menu(areaTexto, botoes);
    }

    public static void menu(JTextArea areaTexto, JButton[] botoes) {
        areaTexto.setText("\n--------------------------------------------------------\n");
        areaTexto.append(" Escolha seu personagem:\n");
        areaTexto.append(" 1) Mago\n");
        areaTexto.append(" 2) Bárbaro\n");
        areaTexto.append(" 0) Sair\n");
        areaTexto.append("--------------------------------------------------------\n");

        String[] opcoes = {"Mago", "Bárbaro", "Sair"};

        for (JButton botao : botoes) {
            for (ActionListener al : botao.getActionListeners()) {
                botao.removeActionListener(al);
            }
        }

        for (int i = 0; i < 3; i++) {
            botoes[i].setText(opcoes[i]);
            botoes[i].setEnabled(true);
        }

        botoes[2].addActionListener(e -> System.exit(0));

        for (int i = 0; i < 2; i++) {
            final int escolhaFinal = i;
            botoes[i].addActionListener(e -> {
                Personagem personagem = switch (escolhaFinal) {
                    case 0 -> new Mago(botoes);
                    case 1 -> new Barbaro(botoes);
                    default -> null;
                };
                if (personagem != null) {
                    personagem.apresentarHistoria(areaTexto);
                    personagem.iniciarAventura(areaTexto, botoes);
                }
            });
        }
    }
}