package com.dti.locadora.dao;
import java.sql.Connection;
import java.util.List;

import com.dti.locadora.model.Filme;


//Declaramos todas as operações necessárias nos cadastros.
public interface FilmeDAOInterface {
    public void inserirCadastro(Connection conn, String nome);
    
}
