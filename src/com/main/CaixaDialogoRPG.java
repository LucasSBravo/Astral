package com.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.InputStream;
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
    private Image fundoPergaminho;
    // private Image manchaTinta;
    private Font fonteMedieval;

    private final JPanel focoDummy = new JPanel();

    public CaixaDialogoRPG() {
        setLayout(new BorderLayout());

        // Carrega imagens e fonte
        fundoPergaminho = new ImageIcon(getClass().getResource("/com/main/Resources/Imagens/pergaminho.jpg")).getImage();
        // manchaTinta = new ImageIcon(getClass().getResource("/images/mancha.png")).getImage();
        carregarFonteMedieval();

        // Painel invisível para foco
        focoDummy.setFocusable(true);
        focoDummy.setPreferredSize(new Dimension(0, 0));
        focoDummy.setOpaque(false);
        add(focoDummy, BorderLayout.NORTH);

        setOpaque(false);

        textoDialogo = new JTextArea();
        textoDialogo.setEditable(false);
        textoDialogo.setOpaque(false);
        textoDialogo.setFont(fonteMedieval.deriveFont(Font.PLAIN, 20f));
        textoDialogo.setForeground(new Color(50, 30, 10)); // Marrom escuro
        textoDialogo.setLineWrap(true);
        textoDialogo.setWrapStyleWord(true);
        add(textoDialogo, BorderLayout.CENTER);

        DefaultCaret caret = (DefaultCaret) textoDialogo.getCaret();
        caret.setBlinkRate(0);
        caret.setVisible(false);  


        botaoProximo = new JButton("→");
        botaoProximo.setFont(new Font("SansSerif", Font.BOLD, 24));
        botaoProximo.setFocusPainted(false);
        botaoProximo.setBackground(new Color(100, 60, 20));
        botaoProximo.setForeground(Color.WHITE);
        botaoProximo.setBorder(BorderFactory.createLineBorder(new Color(60, 40, 20), 2));
        botaoProximo.setPreferredSize(new Dimension(30, 30));
        botaoProximo.setMargin(new Insets(5, 5, 5, 5));
        botaoProximo.addActionListener(e -> {
            if (exibindoMensagem) {
                temporizador.stop();
                textoDialogo.setText(mensagemAtual);
                exibindoMensagem = false;
                if (filaMensagens.isEmpty() && dialogoListener != null) {
                    dialogoListener.aoTerminarAnimacao();
                }
            } else {
                exibirProximaMensagem();
            }
        });


        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.setOpaque(false);
        painelInferior.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        painelInferior.add(botaoProximo, BorderLayout.EAST);
        

        JLabel indicador = new JLabel("▼ Escolha uma opção ▼");
        indicador.setHorizontalAlignment(SwingConstants.CENTER);
        indicador.setFont(new Font("SansSerif", Font.PLAIN, 18));
        indicador.setForeground(new Color(80, 40, 10));
        painelInferior.add(indicador, BorderLayout.CENTER);

        add(painelInferior, BorderLayout.SOUTH);

        temporizador = new Timer(30, (ActionEvent e) -> {
            if (caracteresExibidos < mensagemAtual.length()) {
                textoDialogo.setText(mensagemAtual.substring(0, caracteresExibidos + 1));
                caracteresExibidos++;
            } else {
                temporizador.stop();
                exibindoMensagem = false;
                botaoProximo.setEnabled(true);
                if (filaMensagens.isEmpty() && dialogoListener != null) dialogoListener.aoTerminarAnimacao();
            }
        });

        setVisible(false);
    }

    private void carregarFonteMedieval() {
        try {
            InputStream is = getClass().getResourceAsStream("/com/main/Resources/Fontes/BLKCHCRY.TTF");
            fonteMedieval = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            fonteMedieval = new Font("Serif", Font.BOLD, 20);
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Fundo de pergaminho
        g2d.drawImage(fundoPergaminho, 0, 0, getWidth(), getHeight(), this);

        // Manchas de tinta (pode adicionar várias em posições diferentes)
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f)); // Transparência
        // g2d.drawImage(manchaTinta, 50, 50, 150, 100, this);

        g2d.dispose();
    }

    public void adicionarMensagem(String mensagem) {
        filaMensagens.add(mensagem);
        setVisible(true);
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
            textoDialogo.setFocusable(false);
            if (dialogoListener != null) dialogoListener.aoIniciarAnimacao();
            temporizador.start();
            focoDummy.requestFocusInWindow();
            setVisible(true);
        } else {
            exibindoMensagem = false;
        }
    }

    public void limpar() {
        filaMensagens.clear();
        textoDialogo.setText("");
        exibindoMensagem = false;
        botaoProximo.setEnabled(false);
        setVisible(false);
    }
}
