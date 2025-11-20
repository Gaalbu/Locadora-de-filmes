package com.dti.locadora.dao;

import com.dti.locadora.model.Filme;
import com.dti.locadora.util.ConexaoDatabase;

import java.io.Serial;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmeDAO implements FilmeDAOInterface{
    //Método auxiliar para obter a conexão.

    private Connection conectar() throws SQLException{
        return ConexaoDatabase.conectar();
    }

    @Override
    public void inserir(Filme filme){
        String sql = "INSERT INTO filmes (titulo, duracaoMinutos, genero) VALUES (?, ?, ?)";

        try (Connection conn = conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                
                pstmt.setString(1, filme.getTitulo());
                pstmt.setInt(2, filme.getDuracaoMinutos());
                pstmt.setString(3, filme.getGenero());

                pstmt.executeUpdate();
                System.out.println("Filme inserido com sucesso!");
        }catch (SQLException e) {
            System.err.println("Erro ao inserir filme: " + e.getMessage());
        }
    }
    
    @Override
    public List<Filme> buscarTodos() {
        List<Filme> filmes = new ArrayList<>();
        String sql = "SELECT * FROM filmes";

        try(Connection conn = conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            
            while (rs.next()) {
                Filme filme = new Filme(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getInt("duracaoMinutos"),
                    rs.getString("genero")
                );
                filmes.add(filme);
            }
        } catch (SQLException e){
            System.err.println("Erro ao buscar filmes: " + e.getMessage());
        }
        return filmes;
    }

    public Filme buscarPorId(int id){
        String sql = "SELECT * FROM filmes WHERE id = ?";
        Filme filme = null;

        try (Connection conn = conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()){
                filme = new Filme(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getInt("duracaoMinutos"),
                    rs.getString("genero")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar filme por ID: " + e.getMessage());
        }
        
        return filme;
    }

    @Override
    public boolean atualizar (Filme filme){
        String sql = "UPDATE filmes SET titulo = ?, duracaoMinutos = ?, genero = ?, WHERE id = ?";

        try (Connection conn = conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setString(1, filme.getTitulo());
                pstmt.setInt(2, filme.getDuracaoMinutos());
                pstmt.setString(3,filme.getGenero());
                pstmt.setInt(4, filme.getId());

                int linhasAfetadas = pstmt.executeUpdate();
                return linhasAfetadas > 0;
            } catch(SQLException e){
                System.err.println("Erro ao atualizar filme: " + e.getMessage());
                return false;
            }
    }

    @Override
    public boolean excluir (int id){
        String sql = "DELETE FROM filmes WHERE id = ?";

        try(Connection conn = conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
                pstmt.setInt(1, id);            
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;
            }catch (SQLException e){
                System.err.println("Erro ao excluir filme: " + e.getMessage());
                return false;
            }
    }
}
