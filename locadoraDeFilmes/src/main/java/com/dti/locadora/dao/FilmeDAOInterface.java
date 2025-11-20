package com.dti.locadora.dao;


import java.util.List;
import com.dti.locadora.model.Filme;


//Declaramos todas as operações necessárias nos cadastros.
public interface FilmeDAOInterface {
    void inserir(Filme filme);

    List<Filme> buscarTodos();

    Filme buscarPorId(int id);

    boolean atualizar(Filme filme);

    boolean excluir(int id);
    
}
