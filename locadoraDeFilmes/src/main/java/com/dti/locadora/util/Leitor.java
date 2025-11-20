package com.dti.locadora.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;



public class Leitor {
    //Declaração de leitor para reutilização futura no código
    private final BufferedReader reader;

    
    public Leitor(){
        //Inicializa o leitor e conecta-o ao IO do console
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }
    
    /*
    Lê uma string do console diretamente.
    Captura até que o usuário aperte a tecla ENTER.
    
    */
    
    public String lerString (String entrada){
        System.out.println(entrada);
        try {
            String linha = reader.readLine();
            // Se for nula, retorna string vazia para evitar erros (NullPointerException);
            return linha != null ? linha : "";
        } catch (IOException e) {
            System.err.println("Erro ao ler entrada.");
            e.printStackTrace();
            return "";
        }
    }

    /*
    Lê um número inteiro com validação.
    Tem um loop que continua até que o usuário digite um inteiro válido.
    */
    public int lerInt(String entrada){
        while (true) {
            String input = lerString(entrada);
            
            try{
                return Integer.parseInt(input.trim());
            }catch (NumberFormatException e){
                System.err.println("Erro: O valor digitado não é um número inteiro válido. Tente novamente.");
            }
        }
    }
}
