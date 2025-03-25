package com.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Principal {
    public static void main(String[] args) {
        JFrame janela = new JFrame("Jogo de Aventura");
        janela.setSize(1580, 780);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Painel com imagem de fundo
        FundoPanel painelFundo = new FundoPanel("src/com/main/Resources/Imagens/darkaether.png");
        painelFundo.setLayout(new BorderLayout());  // Usando BorderLayout para organizar os componentes

        // Criando e configurando a área de texto
        JTextArea areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setOpaque(false);  // Torna o fundo da área de texto transparente
        areaTexto.setForeground(Color.WHITE);  // Ajusta a cor do texto para branco (para contraste com o fundo)
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setOpaque(false);  // Torna o fundo da JScrollPane transparente
        scrollPane.getViewport().setOpaque(false);  // Torna o fundo do viewport transparente
        painelFundo.add(scrollPane, BorderLayout.CENTER);

        // Criando painel para os botões e configurando-o
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(1, 3));  // Organiza os botões em linha
        painelBotoes.setOpaque(false);  // Torna o fundo do painel de botões transparente (não será afetado pela imagem)

        JButton[] botoes = new JButton[3];

        for (int i = 0; i < 3; i++) {
            botoes[i] = new JButton();
            painelBotoes.add(botoes[i]);
        }

        painelFundo.add(painelBotoes, BorderLayout.SOUTH);  // Adiciona os botões no painel de fundo
        janela.setContentPane(painelFundo);  // Adiciona o painel de fundo à janela
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
    
        // Limpar ActionListeners antigos
        for (JButton botao : botoes) {
            for (ActionListener al : botao.getActionListeners()) {
                botao.removeActionListener(al);
            }
        }
    
        // Carregar imagem para o botão Mago
        ImageIcon iconeMago = new ImageIcon("src/com/main/Resources/Imagens/mago.png"); // Caminho da imagem
        Image imagemAjustadaMago = iconeMago.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        iconeMago = new ImageIcon(imagemAjustadaMago);
    
        // Carregar imagem para o botão Bárbaro
        ImageIcon iconeBarbaro = new ImageIcon("src/com/main/Resources/Imagens/cav.png"); // Caminho da imagem
        Image imagemAjustadaBarbaro = iconeBarbaro.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        iconeBarbaro = new ImageIcon(imagemAjustadaBarbaro);
    
        // Configurar botões
        for (int i = 0; i < 3; i++) {
            botoes[i].setText(opcoes[i]);
            botoes[i].setEnabled(true);
            botoes[i].setBackground(UIManager.getColor("Button.background")); // Restaurando cor padrão de fundo
            botoes[i].setForeground(UIManager.getColor("Button.foreground")); // Restaurando cor do texto
        }
    
        // Adicionar imagens aos botões
        botoes[0].setIcon(iconeMago);    // Ícone para o botão Mago
        botoes[1].setIcon(iconeBarbaro); // Ícone para o botão Bárbaro
    
        // Ação do botão "Sair"
        botoes[2].addActionListener(e -> System.exit(0));
    
        // Ação dos botões de personagens
        for (int i = 0; i < 2; i++) {
            final int escolhaFinal = i;
            botoes[i].addActionListener(e -> {
                // Remover ícone de todos os botões antes de prosseguir
                for (JButton botao : botoes) {
                    botao.setIcon(null);
                }
    
                // Criar personagem correspondente
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

// Classe para exibir imagem de fundo
class FundoPanel extends JPanel {
    private Image imagemFundo;

    public FundoPanel(String caminhoImagem) {
        try {
            imagemFundo = new ImageIcon(caminhoImagem).getImage();
        } catch (Exception e) {
            System.out.println("Erro ao carregar imagem: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);  // Chama o método pai para garantir que o painel seja pintado corretamente
        if (imagemFundo != null) {
            // Redimensionando a imagem para o tamanho do painel
            g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
