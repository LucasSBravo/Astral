package com.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Queue;

public class CaixaDialogoRPG extends JPanel {
    private JTextArea textoDialogo;
    private Timer temporizador;
    private int caracteresExibidos;
    private String mensagemAtual;
    private Queue<String> filaMensagens = new LinkedList<>();
    private boolean exibindoMensagem = false;
    private JButton botaoProximo;

    public CaixaDialogoRPG() {
        setLayout(new BorderLayout());
        setOpaque(true); // importante!
        setBackground(new Color(255, 255, 200));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 3),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Área de texto
        textoDialogo = new JTextArea();
        textoDialogo.setEditable(false);
        textoDialogo.setOpaque(false); // o texto ainda pode ser transparente se quiser
        textoDialogo.setFont(new Font("Arial", Font.BOLD, 16));
        textoDialogo.setLineWrap(true);
        textoDialogo.setWrapStyleWord(true);
        add(textoDialogo, BorderLayout.CENTER);

        // Botão de próxima mensagem
        botaoProximo = new JButton("→");
        botaoProximo.setFont(new Font("Arial", Font.BOLD, 16));
        botaoProximo.setFocusPainted(false);
        botaoProximo.setBackground(Color.DARK_GRAY);
        botaoProximo.setForeground(Color.WHITE);
        botaoProximo.setEnabled(true); // Sempre habilitado, a lógica será no clique
        botaoProximo.addActionListener(e -> {
            if (exibindoMensagem) {
                // Interrompe o efeito de digitação e mostra o texto inteiro
                temporizador.stop();
                textoDialogo.setText(mensagemAtual);
                exibindoMensagem = false;
               // botaoProximo.setEnabled(true);
            } else {
                exibirProximaMensagem();
            }
        });

        // Painel inferior com o botão
        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.setOpaque(false);
        painelInferior.add(botaoProximo, BorderLayout.EAST);

        JLabel indicador = new JLabel("▼ Clique em uma opção para continuar ▼");
        indicador.setHorizontalAlignment(SwingConstants.CENTER);
        painelInferior.add(indicador, BorderLayout.CENTER);

        add(painelInferior, BorderLayout.SOUTH);

        // Temporizador para exibir texto gradualmente
        temporizador = new Timer(30, (ActionEvent e) -> {
            if (caracteresExibidos < mensagemAtual.length()) {
                textoDialogo.setText(mensagemAtual.substring(0, caracteresExibidos + 1));
                caracteresExibidos++;
            } else {
                temporizador.stop();
                exibindoMensagem = false;
                botaoProximo.setEnabled(true); // Habilita o botão após exibir a mensagem
            }
        });

        setVisible(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // desenha o fundo amarelado com cantos arredondados
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        g2.dispose();
        super.paintComponent(g);
    }

    public void adicionarMensagem(String mensagem) {
        filaMensagens.add(mensagem);
        setVisible(true); // Garante que a caixa de diálogo seja exibida
        if (!exibindoMensagem) {
            exibirProximaMensagem();
        }
    }

    private void exibirProximaMensagem() {
        if (!filaMensagens.isEmpty()) {
            exibindoMensagem = true;
            mensagemAtual = filaMensagens.poll();
            caracteresExibidos = 0;
            textoDialogo.setText("");
           // botaoProximo.setEnabled(false); // Desabilita o botão enquanto a mensagem está sendo exibida
            temporizador.start();
            setVisible(true);
        } else {
            // Corrige o estado ao clicar sem mensagens
            exibindoMensagem = false;
        }
    }

    public void limpar() {
        filaMensagens.clear();
        textoDialogo.setText("");
        exibindoMensagem = false; // Garante que novas mensagens possam ser exibidas
        botaoProximo.setEnabled(false);
        setVisible(false);
    }
}