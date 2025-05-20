package com.main;

import java.io.*;

public class GerenciadorProgresso {
    private static final String ARQUIVO_PROGRESSO = "progresso_jogo.dat";

    // Salvar progresso
    public static void salvarProgresso(EstadoJogo estadoJogo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_PROGRESSO))) {
            oos.writeObject(estadoJogo);
            System.out.println("Progresso salvo com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar progresso: " + e.getMessage());
        }
    }

    // Carregar progresso
    public static EstadoJogo carregarProgresso() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO_PROGRESSO))) {
            return (EstadoJogo) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Nenhum progresso salvo encontrado.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar progresso: " + e.getMessage());
        }
        return null;
    }
}