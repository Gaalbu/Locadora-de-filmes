package com.dti.locadora.dao;

import com.dti.locadora.model.Filme;
import com.dti.locadora.util.ConexaoDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime; //data atual
import java.time.format.DateTimeFormatter; //formatando em string

public class FilmeDAO implements FilmeDAOInterface{
    //Método auxiliar para obter a conexão.

    private Connection conectar() throws SQLException{
        return ConexaoDatabase.conectar();
    }

    private String getDataAtualFormatada(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public void inserir(Filme filme){
        String sql = "INSERT INTO filmes (titulo, duracaoMinutos, genero, anoLancamento) VALUES (?,?,?,?)";

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            
            pstmt.setString(1, filme.getTitulo());
            pstmt.setInt(2, filme.getDuracaoMinutos());
            pstmt.setString(3, filme.getGenero());

            //Lógica reservada para formatar a data (string->datetime)
            String dataParaGravar = filme.getAnoLancamento();

            //Se for nulo ou vazio, usamos o "agora" (now())
            if (dataParaGravar == null || dataParaGravar.isEmpty()){
                dataParaGravar = getDataAtualFormatada();
            }

            pstmt.setString(4, dataParaGravar);

            pstmt.executeUpdate();
            System.out.println("Filme inserido com sucesso. Data: " + dataParaGravar);

                

        } catch (SQLException e) {
            System.err.println("Erro ao inserir filme: " + e.getMessage());
        }
    }

    @Override
    public List<Filme> buscarTodos(){
        List<Filme> filmes = new ArrayList<>();
        String sql = "SELECT * FROM filmes";

        try (Connection conn = conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Filme filme = new Filme(rs.getInt("id"), rs.getString("titulo"), rs.getInt("duracaoMinutos"), rs.getString("genero"), rs.getString("anoLancamento"));
                filmes.add(filme);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar filmes: " + e.getMessage());
        }
        return filmes;
    }

    @Override
    public Filme buscarPorId(int id){
        String sql = "SELECT * FROM filmes WHERE id = ?";
        Filme filme = null;

        try(Connection conn = conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                filme = new Filme(rs.getInt("id"),rs.getString("titulo"),rs.getInt("duracaoMinutos"),rs.getString("genero"),rs.getString("anoLancamento"));
            }
        }
        catch (SQLException e){
            System.err.println("Erro ao buscar filme: " + e.getMessage());
        }
        return filme;
    }

    @Override
    public boolean atualizar (Filme filme){
        String sql = "UPDATE filmes SET titulo = ?, duracaoMinutos = ?, genero = ?,anoLancamento = ? WHERE id = ?";
        
        
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, filme.getTitulo());
            pstmt.setInt(2, filme.getDuracaoMinutos());
            pstmt.setString(3, filme.getGenero());

            String dataParaGravar = filme.getAnoLancamento();
            if(dataParaGravar == null || dataParaGravar.isEmpty()){
                dataParaGravar = getDataAtualFormatada();
            }
            pstmt.setString(4, dataParaGravar);
            pstmt.setInt(5, filme.getId()); // parametro id ficou na 5 posição...

            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0; //se for diferente de 0, mostra quantas linhas mudaram (TEM QUE SAIR 1 ou 0);

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar filme: " + e.getMessage());
            return false;
        }
    }
    @Override
    public boolean excluir(int id){
        String sql = "DELETE FROM filmes WHERE id = ?";
        
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir filme: " + e.getMessage());
            return false;
        }
    }
}
