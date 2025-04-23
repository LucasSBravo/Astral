package com.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.text.DefaultCaret;

public class CaixaDialogoRPG extends JPanel {
    private JTextArea textoDialogo;
    private Timer temporizador;
    private int caracteresExibidos;
    private String mensagemAtual;
    private Queue<String> filaMensagens = new LinkedList<>();
    private boolean exibindoMensagem = false;
    private JButton botaoProximo;
    private final JPanel focoDummy = new JPanel(); // Painel invisível para capturar foco


    public CaixaDialogoRPG() {
        setLayout(new BorderLayout());
        // Painel invisível para capturar o foco e evitar a barra de digitação no JTextArea
        focoDummy.setFocusable(true);
        focoDummy.setPreferredSize(new Dimension(0, 0));
        focoDummy.setOpaque(false);
        add(focoDummy, BorderLayout.NORTH);
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

        DefaultCaret caret = (DefaultCaret) textoDialogo.getCaret();
        caret.setBlinkRate(0);      // desliga o piscar do caret
        caret.setVisible(false);    // esconde o caret completamente

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
            
                // Se essa for a última mensagem da fila, avisa que a animação terminou
                if (filaMensagens.isEmpty() && dialogoListener != null) {
                    dialogoListener.aoTerminarAnimacao();
                }
            
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
                botaoProximo.setEnabled(true);
                
                // 🔽 Só chama o listener se não houver mais mensagens
                if (filaMensagens.isEmpty()) {
                    if (dialogoListener != null) dialogoListener.aoTerminarAnimacao();
                }
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
    
    public interface DialogoListener {
        void aoIniciarAnimacao();
        void aoTerminarAnimacao();

    }    

    private DialogoListener dialogoListener;

    public void setDialogoListener(DialogoListener listener) {
    this.dialogoListener = listener;
    }

    private void exibirProximaMensagem() {
        if (!filaMensagens.isEmpty()) {
            exibindoMensagem = true;
            mensagemAtual = filaMensagens.poll();
            caracteresExibidos = 0;
            textoDialogo.setText("");
            textoDialogo.setCaretPosition(0);
            textoDialogo.getCaret().setVisible(false);
            textoDialogo.setFocusable(false);  // Pode receber foco se necessário
            textoDialogo.setCaretPosition(0); // Reseta a posição do cursor
            textoDialogo.getCaret().setVisible(false); // Esconde o cursor

           // botaoProximo.setEnabled(false); // Desabilita o botão enquanto a mensagem está sendo exibida
           if (dialogoListener != null) dialogoListener.aoIniciarAnimacao();
            temporizador.start();
            // Força o foco para o dummy, para evitar barra de digitação no JTextArea
            focoDummy.requestFocusInWindow();
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