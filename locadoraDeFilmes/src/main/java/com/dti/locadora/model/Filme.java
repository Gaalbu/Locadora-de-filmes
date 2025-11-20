package com.dti.locadora.model;

/*
    Classe modelo filme que será criada para cada cadastro.

*/

public class Filme {
    private int id; //Sendo gerado pelo SQLite
    private String titulo;
    private int duracaoMinutos;
    private String genero;
    private String anoLancamento;

    

    public Filme(String titulo, int duracaoMinutos, String genero, String anoLancamento) {
        this.titulo = titulo;
        this.duracaoMinutos = duracaoMinutos;
        this.genero = genero;
        this.anoLancamento = anoLancamento;
    }

    //Segundo construtor gerado para usarmos quando buscarmos um filme.
    public Filme(int id, String titulo, int duracaoMinutos, String genero, String anoLancamento) {
        this.id = id;
        this.titulo = titulo;
        this.duracaoMinutos = duracaoMinutos;
        this.genero = genero;
        this.anoLancamento = anoLancamento;
    }

    //Getters

    public int getId() {
        return id;
    }

    public String getAnoLancamento() {
        return anoLancamento;
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
    
    //Setters, talvez serão usados para Update.

    public void setTitulo(String titulo) { 
        this.titulo = titulo; 
    }
    public void setDuracaoMinutos(int duracaoMinutos) { 
        this.duracaoMinutos = duracaoMinutos; 
    }
    public void setGenero(String genero) { 
        this.genero = genero; 
    }

    public void setAnoLancamento(String anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Título: %s | Gênero: %s | Data: %s", id, titulo, genero, anoLancamento);
    }

    

    


}
