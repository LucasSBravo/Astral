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

    public CaixaDialogoRPG() {
        setLayout(new BorderLayout());
        setOpaque(true); // importante!
        setBackground(new Color(255, 255, 200));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 3),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        textoDialogo = new JTextArea();
        textoDialogo.setEditable(false);
        textoDialogo.setOpaque(false); // o texto ainda pode ser transparente se quiser
        textoDialogo.setFont(new Font("Arial", Font.BOLD, 16));
        textoDialogo.setLineWrap(true);
        textoDialogo.setWrapStyleWord(true);
        add(textoDialogo, BorderLayout.CENTER);

        JLabel indicador = new JLabel("▼ Clique em uma opção para continuar ▼");
        indicador.setHorizontalAlignment(SwingConstants.CENTER);
        add(indicador, BorderLayout.SOUTH);

        temporizador = new Timer(30, (ActionEvent e) -> {
            if (caracteresExibidos < mensagemAtual.length()) {
                textoDialogo.setText(mensagemAtual.substring(0, caracteresExibidos + 1));
                caracteresExibidos++;
            } else {
                temporizador.stop();
                exibindoMensagem = false;
                exibirProximaMensagem();
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
            temporizador.start();
            setVisible(true);
        } 
    }

    public void limpar() {
        filaMensagens.clear();
        textoDialogo.setText("");
        setVisible(false);
    }
}
