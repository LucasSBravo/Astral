package com.main;

import java.io.Serializable;

public class EstadoJogo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String personagem; // Ex: "Bárbaro", "Mago"
    private String etapa;      // Ex: "ataque", "aldeia", "fim"

    public EstadoJogo(String personagem, String etapa) {
        this.personagem = personagem;
        this.etapa = etapa;
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

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    @Override
    public String toString() {
        return "Personagem: " + personagem + ", Etapa: " + etapa;
    }
}
