package com.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Principal {
    private static boolean telaCheia = true;

    public static void main(String[] args) {
        EstadoJogo estadoSalvo = GerenciadorProgresso.carregarProgresso();

        JFrame janela = new JFrame("Jogo de Aventura");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setUndecorated(true);
        configurarModoExecucao(janela);

        JTextArea areaTexto = criarAreaTexto();
        JButton[] botoes = criarBotoes();
        FundoPanel painelFundo = criarPainelFundo(areaTexto, botoes);

        janela.setContentPane(painelFundo);
        janela.setVisible(true);

        if (estadoSalvo != null) {
            int opcao = JOptionPane.showOptionDialog(null,
                    "Deseja continuar de onde parou?",
                    "Progresso encontrado",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Sim", "Novo Jogo", "Sair"},
                    "Sim");

            if (opcao == 0) {
                continuarJogo(areaTexto, botoes, estadoSalvo);
            } else if (opcao == 1) {
                exibirMenuInicial(areaTexto, botoes, null);
            } else {
                System.exit(0);
            }
        } else {
            exibirMenuInicial(areaTexto, botoes, null);
        }
    }

    private static void continuarJogo(JTextArea areaTexto, JButton[] botoes, EstadoJogo estado) {
        String classe = estado.getPersonagem();
        Personagem personagem = null;

        if ("Mago".equals(classe)) {
            personagem = new Mago(botoes);
        } else if ("Bárbaro".equals(classe)) {
            personagem = new Barbaro(botoes);
        }

        if (personagem != null) {
            personagem.retomarProgresso(areaTexto, estado, botoes);
        } else {
            exibirMenuInicial(areaTexto, botoes, null);
        }
    }

    private static JTextArea criarAreaTexto() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setOpaque(false);
        area.setForeground(Color.WHITE);
        area.setFont(new Font("Arial", Font.BOLD, 18));
        area.setWrapStyleWord(true);
        area.setLineWrap(true);
        return area;
    }

    private static JButton[] criarBotoes() {
        JButton[] botoes = new JButton[3];
        for (int i = 0; i < botoes.length; i++) {
            botoes[i] = new JButton();
        }
        return botoes;
    }

    private static FundoPanel criarPainelFundo(JTextArea areaTexto, JButton[] botoes) {
        Image imagemFundo = new ImageIcon("src/com/main/Resources/Imagens/darkaether.png").getImage();
        FundoPanel painelFundo = new FundoPanel(imagemFundo);
        painelFundo.setLayout(new BorderLayout());

        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        painelFundo.add(scroll, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new GridLayout(1, botoes.length));
        painelBotoes.setOpaque(false);
        for (JButton botao : botoes) {
            painelBotoes.add(botao);
        }

        painelFundo.add(painelBotoes, BorderLayout.SOUTH);
        return painelFundo;
    }

    public static void exibirMenuInicial(JTextArea areaTexto, JButton[] botoes, EstadoJogo progressoSalvo) {
        String[] opcoes = {"Mago", "Bárbaro", "Sair"};

        areaTexto.setText("Escolha seu personagem:\n");

        ImageIcon iconeMago = new ImageIcon("src/com/main/Resources/Imagens/mago.png");
        ImageIcon iconeBarbaro = new ImageIcon("src/com/main/Resources/Imagens/cav.png");
        iconeMago = new ImageIcon(iconeMago.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        iconeBarbaro = new ImageIcon(iconeBarbaro.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));

        limparActionListeners(botoes);

        for (int i = 0; i < botoes.length; i++) {
            botoes[i].setText(opcoes[i]);
            botoes[i].setEnabled(true);
            botoes[i].setIcon(null);
        }

        botoes[0].setIcon(iconeMago);
        botoes[1].setIcon(iconeBarbaro);

        botoes[0].addActionListener(e -> iniciarComPersonagem(new Mago(botoes), "Mago", areaTexto, botoes));
        botoes[1].addActionListener(e -> iniciarComPersonagem(new Barbaro(botoes), "Bárbaro", areaTexto, botoes));
        botoes[2].addActionListener(e -> System.exit(0));
    }

    private static void iniciarComPersonagem(Personagem personagem, String classe, JTextArea areaTexto, JButton[] botoes) {
        for (JButton botao : botoes) botao.setIcon(null);

        personagem.apresentarHistoria(areaTexto);
        personagem.iniciarAventura(areaTexto, botoes);

        EstadoJogo estado = new EstadoJogo(classe + ":Inicio", classe);
        GerenciadorProgresso.salvarProgresso(estado);
    }

    private static void limparActionListeners(JButton[] botoes) {
        for (JButton botao : botoes) {
            for (ActionListener listener : botao.getActionListeners()) {
                botao.removeActionListener(listener);
            }
        }
    }

    public static void configurarModoExecucao(JFrame janela) {
        if (telaCheia) {
            janela.setSize(1280, 720);
            janela.setLocationRelativeTo(null);
        } else {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            janela.setSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
            gd.setFullScreenWindow(janela);
        }
    }

    public static void exibirDialogo(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem);
    }

    public static class FundoPanel extends JPanel {
        private Image imagem;
    
        public FundoPanel(Image imagem) {
            this.imagem = imagem;
        }
    
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(imagem, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
}
