package com.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Principal {
    private static CaixaDialogoRPG dialogoAtual; 
    private static boolean telaCheia = false; // Variável para controlar o modo de execução
    private static JFrame janela; // Janela agora é um atributo global 
    private static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    public static void main(String[] args) {
        // Carregar progresso salvo
        String progressoSalvo = GerenciadorProgresso.carregarProgresso();
        if (progressoSalvo != null) {
            System.out.println("Progresso carregado: " + progressoSalvo);
        }
        janela = new JFrame("Jogo de Aventura");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setUndecorated(true); // Remove bordas e barra de título
        janela.setLayout(new BorderLayout()); // Garante que os componentes sejam organizados corretamente
        janela.setSize(1280, 720); // Define um tamanho inicial correto
        janela.setLocationRelativeTo(null); // Centraliza na tela
    
        configurarModoExecucao(); // Aplica as configurações de tela corretamente
    
        // Painel com imagem de fundo
        FundoPanel painelFundo = new FundoPanel("src/com/main/Resources/Imagens/darkaether.png");
        painelFundo.setLayout(new BorderLayout());
    
        // Criando e configurando a área de texto
        JTextArea areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setOpaque(false);
        areaTexto.setForeground(Color.WHITE);
        areaTexto.setFont(new Font("Arial", Font.BOLD, 18)); // Ajusta o tamanho da fonte para melhor visibilidade
        areaTexto.setWrapStyleWord(true);
        areaTexto.setLineWrap(true);
        
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        painelFundo.add(scrollPane, BorderLayout.CENTER);
    
        // Criando painel para os botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(1, 3));
        painelBotoes.setOpaque(false);
    
        JButton[] botoes = new JButton[3];
        for (int i = 0; i < 3; i++) {
            botoes[i] = new JButton();
            painelBotoes.add(botoes[i]);
        }

        // Instância da CaixaDialogoRPG
        dialogoAtual = new CaixaDialogoRPG(janela);
    
        painelFundo.add(painelBotoes, BorderLayout.SOUTH);

        // Adiciona botão de configurações
        JButton botaoConfig = new JButton("⚙");
        botaoConfig.setFocusable(false);
        botaoConfig.setPreferredSize(new Dimension(50, 50));
        botaoConfig.setFont(new Font("Arial", Font.BOLD, 20));
        botaoConfig.setOpaque(false);
        botaoConfig.addActionListener(e -> exibirOpcoes(janela));
        
        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelTopo.setOpaque(false);
        painelTopo.add(botaoConfig);
        painelFundo.add(painelTopo, BorderLayout.NORTH);

        janela.setContentPane(painelFundo);
        janela.setVisible(true);

        // Exibe o lobby ao iniciar
         exibirLobby(areaTexto, botoes, janela);

         janela.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_F11) {
                    alternarModoExecucao();
                } else if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    exibirOpcoes(janela);
                }
            }
        });

        janela.setVisible(true);
    }
    
    public static void configurarModoExecucao() {
        if (janela == null) return;

        janela.dispose();
        
        if (telaCheia) {
            janela.setUndecorated(true);
            janela.setResizable(false);
            gd.setFullScreenWindow(janela);
        } else {
            janela.setUndecorated(false);
            janela.setResizable(true);
            gd.setFullScreenWindow(null); // Sai do modo tela cheia
    
            janela.setSize(1280, 720); // Define a resolução personalizada corretamente
            janela.setLocationRelativeTo(null); // Centraliza a janela
        }
        janela.setVisible(true);
        janela.revalidate(); // Garante que a interface gráfica seja atualizada
        janela.repaint(); // Força a repintura da tela
    }

    public static void alternarModoExecucao() {
        telaCheia = !telaCheia; // Alterna entre os modos
        configurarModoExecucao();
    }

    public static void aplicarResolucaoPersonalizada() {
        telaCheia = false;
        configurarModoExecucao();
    }

    public static void exibirOpcoes(JFrame janela) {
        String[] modos = {"Resolução Personalizada", "Tela Cheia"};
        int escolha = JOptionPane.showOptionDialog(
                janela,
                "Escolha o modo de execução:",
                "Opções",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                modos,
                modos[0]
        );
        
        if (escolha == 0) {
            telaCheia = false;
        } else if (escolha == 1) {
            telaCheia = true;
        }
        
        configurarModoExecucao();
    }

    public static void exibirLobby(JTextArea areaTexto, JButton[] botoes, JFrame janela) {
        areaTexto.setText("\n--------------------------------------------------------\n");
        areaTexto.append(" Bem-vindo ao Lobby!\n");
        areaTexto.append(" Escolha uma das opções abaixo:\n");
        areaTexto.append(" 1. Jogar\n");
        areaTexto.append(" 2. Opções\n");
        areaTexto.append(" 3. Sair\n");
        areaTexto.append("--------------------------------------------------------\n");

        String[] opcoes = {"Jogar", "Opções", "Sair"};

        // Limpar ActionListeners antigos
        for (JButton botao : botoes) {
            for (ActionListener al : botao.getActionListeners()) {
                botao.removeActionListener(al);
            }
        }

        // Configurar botões
        for (int i = 0; i < 3; i++) {
            botoes[i].setText(opcoes[i]);
            botoes[i].setEnabled(true);
            botoes[i].setBackground(UIManager.getColor("Button.background")); // Restaurando cor padrão de fundo
            botoes[i].setForeground(UIManager.getColor("Button.foreground")); // Restaurando cor do texto
        }

        // Ação do botão "Jogar"
        botoes[0].addActionListener(e -> {
            areaTexto.setText(""); // Limpa o texto do lobby
            String progressoSalvo = GerenciadorProgresso.carregarProgresso();
            menu(areaTexto, botoes, progressoSalvo); // Executa o restante do código
        });

        // Ação do botão "Opções"
        botoes[1].addActionListener(e -> {
            exibirOpcoes(janela);
        });

        // Ação do botão "Sair"
        botoes[2].addActionListener(e -> {
            exibirDialogo("Você escolheu sair do jogo.");
            System.exit(0);
        });
    }

   /* public static void exibirOpcoes(JFrame janela) {
        // Exibe um diálogo para escolher o modo de execução
        String[] modos = {"Resolução Personalizada", "Tela Cheia"};
        int escolha = JOptionPane.showOptionDialog(
                janela,
                "Escolha o modo de execução:",
                "Opções",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                modos,
                modos[0]
        );

        if (escolha == 0) {
            telaCheia = true;
        } else if (escolha == 1) {
            telaCheia = false;
        }

        // Reconfigura o modo de execução
        configurarModoExecucao();
    }*/

    public static void menu(JTextArea areaTexto, JButton[] botoes, String progressoSalvo) {
        areaTexto.setText("\n--------------------------------------------------------\n");
        areaTexto.append(" Escolha seu personagem:\n");
        areaTexto.append(" Mago\n");
        areaTexto.append(" Bárbaro\n");
        areaTexto.append(" Sair\n");
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
                // Salvar progresso
                GerenciadorProgresso.salvarProgresso(opcoes[escolhaFinal]);

                // Exibir mensagem na janela de diálogo
                exibirDialogo("Você escolheu: " + opcoes[escolhaFinal] + ". Progresso salvo!");
            });
        }
            // Configurar botão "Sair"
        botoes[2].addActionListener(e -> {
            exibirDialogo("Você escolheu sair do jogo.");
            System.exit(0);
        });

        // Exibir progresso salvo, se houver
        if (progressoSalvo != null) {
            exibirDialogo("Progresso carregado: " + progressoSalvo);
        }
    }
    
   
    public static void exibirDialogo(String mensagem) {
        if (dialogoAtual == null) {
            throw new IllegalStateException("Caixa de diálogo não foi inicializada.");
        }
        dialogoAtual.adicionarMensagem(mensagem);
    }
    
    public static CaixaDialogoRPG getDialogoAtual() {
        return dialogoAtual;
    }
   
}

	
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
