package com.main;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class Personagem {
    protected String nome;
    protected String origem;
    protected List<Opcao> opcoes;

    public Personagem(String nome, String origem) {
        this.nome = nome;
        this.origem = origem;
        this.opcoes = new ArrayList<>();
    }

    public void apresentarHistoria(JTextArea areaTexto) {
        areaTexto.setText("");
        Principal.exibirDialogo("Você escolheu: " + nome + "\nOrigem: " + origem );
    }

    public void iniciarAventura(JTextArea areaTexto, JButton[] botoes) {
        //areaTexto.append("\nASTRAL\n");// texto permanente em todas as instancias do roteiro
        int numOpcoes = Math.min(opcoes.size(), botoes.length);

        resetarEventos(botoes);

        // Impede que os botões fiquem com foco visual
        for (JButton botao : botoes) {
            botao.setFocusable(false);
        }

        for (int i = 0; i < numOpcoes; i++) {
           // areaTexto.append(opcoes.get(i).getTexto() + "\n"); //Mostra a escolha na tela
            botoes[i].setText(opcoes.get(i).getTexto());// Mostra a escolha no botão
            botoes[i].setEnabled(true);
        }

        if (botoes.length > numOpcoes) {
            botoes[botoes.length - 1].setText("Opções");
            botoes[botoes.length - 1].setEnabled(true);
            botoes[botoes.length - 1].addActionListener(e -> {
                if (Principal.caixaDialogo != null) {
                    Principal.caixaDialogo.limpar(); // Limpa e oculta a caixa de diálogo
                }
                Principal.exibirLobby(GerenciadorProgresso.carregarProgresso()); // Volta para o lobby
            });
        }

        for (int i = 0; i < numOpcoes; i++) {
            final int index = i;
            botoes[i].addActionListener(e -> {
                opcoes.get(index).executar(areaTexto);
            });
        }
    }

    public void atualizarLocalizacao(JTextArea areaTexto, String local) {
        String textoAtual = areaTexto.getText();
        String[] linhas = textoAtual.split("\n", 2); // Separa a primeira linha do resto
        String resto = (linhas.length > 1) ? linhas[1] : "";
        areaTexto.setText("Local: " + local + "\n" + resto);
    }

    public abstract void retomarProgresso(JTextArea areaTexto, EstadoJogo estado, JButton[] botoes);


    protected void finalizarCiclo(JTextArea areaTexto, JButton[] botoes) {
        finalizarCiclo(areaTexto, botoes, null);
    }

    protected void finalizarCiclo(JTextArea areaTexto, JButton[] botoes, EstadoJogo progressoSalvo) {
        Principal.exibirDialogo("\nDeseja voltar ao menu inicial?\n");

        resetarEventos(botoes);
    
        // Configura botão "Voltar ao Menu"
        botoes[0].setText("Reiniciar Jornada");
        botoes[0].setEnabled(true);
        botoes[0].addActionListener(e -> {
            limparOpcoes();
            Principal.exibirMenuInicial(areaTexto, botoes, progressoSalvo);

        });
    
        // Configura botão "Sair"
         botoes[botoes.length - 1].setText("Voltar ao Menu");
         botoes[botoes.length - 1].setEnabled(true);
         botoes[botoes.length - 1].addActionListener(e -> Principal.exibirLobby(GerenciadorProgresso.carregarProgresso())); 
    
        // Desativa botões intermediários se existirem
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

    // Adicione ao final da classe Personagem
public abstract void continuarAventura(String etapaSalva, JTextArea areaTexto, JButton[] botoes);

}