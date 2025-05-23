package com.main;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EstadoJogo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String personagem; // Ex: "Bárbaro", "Mago"
    private String etapa;      // Ex: "ataque", "aldeia", "fim"
    private Map<String, Boolean> escolhas; // Mapa de decisões do jogador

    public EstadoJogo(String personagem, String etapa) {
        this.personagem = personagem;
        this.etapa = etapa;
        this.escolhas = new HashMap<>();
    }

    public String getPersonagem() {
        return personagem;
    }

    public void setPersonagem(String personagem) {
        this.personagem = personagem;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setProximaEtapa(String etapa) {
    this.etapa = etapa;
}


    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    // Métodos para manipular escolhas
    public void setEscolha(String chave, boolean valor) {
        escolhas.put(chave, valor);
    }

    public boolean getEscolha(String chave) {
        return escolhas.getOrDefault(chave, false);
    }

    public boolean temEscolha(String chave) {
        return escolhas.containsKey(chave);
    }

    @Override
    public String toString() {
        return "Personagem: " + personagem + ", Etapa: " + etapa + ", Escolhas: " + escolhas;
    }
}
