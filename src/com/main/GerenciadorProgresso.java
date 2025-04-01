package com.main;

import java.io.*;

public class GerenciadorProgresso {
    private static final String ARQUIVO_SALVAMENTO = "progresso_jogo.dat";

    // Salvar o progresso do jogo
    public static void salvarProgresso(String classeEscolhida) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_SALVAMENTO))) {
            oos.writeObject(classeEscolhida);
            System.out.println("Progresso salvo com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o progresso: " + e.getMessage());
        }
    }

    // Carregar o progresso do jogo
    public static String carregarProgresso() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO_SALVAMENTO))) {
            return (String) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Nenhum progresso salvo encontrado.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar o progresso: " + e.getMessage());
        }
        return null; // Retorna null se não houver progresso salvo
    }
}