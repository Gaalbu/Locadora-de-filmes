package com.dti.locadora.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;



public class Leitor {
    //Declaração de leitor para reutilização futura no código
    private BufferedReader leitorIO;

    //Declarando um leitor para evitar problemas de buffer.
    public Leitor(){
        this.leitorIO = new BufferedReader(new InputStreamReader(System.in));
    }
    
    //Método de leitura IO.
    public String IOinput() throws IOException{
        return leitorIO.readLine();
    }
}
