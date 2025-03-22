package com.main;

import java.util.function.Consumer;

import javax.swing.JTextArea;

public class Opcao {
    private String texto;
    private Consumer<JTextArea> acao;

    public Opcao(String texto, Consumer<JTextArea> acao) {
        this.texto = texto;
        this.acao = acao;
    }

    public String getTexto() {
        return texto;
    }

    public void executar(JTextArea areaTexto) {
        acao.accept(areaTexto);
    }
}