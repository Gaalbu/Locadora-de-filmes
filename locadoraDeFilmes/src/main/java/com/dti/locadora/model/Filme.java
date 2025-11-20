package com.dti.locadora.model;

/*
    Classe modelo filme que será criada para cada cadastro.

*/

public class Filme {
    private int id; //Sendo gerado pelo SQLite
    private String titulo;
    private int duracaoMinutos;
    private String genero;

    public Filme(String titulo, int duracaoMinutos, String genero){
        this.titulo = titulo;
        this.duracaoMinutos = duracaoMinutos;
        this.genero = genero;
    }

    //Getters

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public String getGenero() {
        return genero;
    }
    

}
