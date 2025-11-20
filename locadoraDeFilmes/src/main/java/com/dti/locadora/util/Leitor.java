package com.dti.locadora.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
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
    
    public String lerString (String prompt){
        System.out.println(prompt);
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
    public int lerInt(String prompt){
        while (true) {
            String input = lerString(prompt);
            
            try{
                return Integer.parseInt(input.trim());
            }catch (NumberFormatException e){
                System.err.println("Erro: O valor digitado não é um número inteiro válido. Tente novamente.");
            }
        }
    }

    /*
    Lê uma string que será formatada para o padrão DATETIME
    
    */

    public String lerDataValida (String prompt) {
        String dataInput = "";
        while(true){
            dataInput = lerString(prompt + "(YYYY-MM-DD HH:MM:SS, ou VAZIO): ");
            
            if (dataInput.isEmpty()){
                return dataInput; //Retorna string vazia para o DAO usar NOW()
            }
        
            try {
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").parse(dataInput);
                return dataInput;
            } catch (java.time.format.DateTimeParseException e) {
                System.err.println("Formato de data inválido. Use o padrão YYYY-MM-DD HH:MM:SS.");
            }
        }  
    }
}
