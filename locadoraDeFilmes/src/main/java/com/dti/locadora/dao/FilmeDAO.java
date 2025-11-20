package com.dti.locadora.dao;

import com.dti.locadora.model.Filme;
import com.dti.locadora.util.ConexaoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime; //data atual
import java.time.format.DateTimeFormatter; //formatando em string

public class FilmeDAO implements FilmeDAOInterface{
    
    private static final Logger logger = LoggerFactory.getLogger(FilmeDAO.class);
    
    //Método auxiliar para obter a conexão.
    private Connection conectar() throws SQLException{
        return ConexaoDatabase.conectar();
    }

    private String getDataAtualFormatada(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public int inserir(Filme filme){
        String sql = "INSERT INTO filmes (titulo, duracaoMinutos, genero, anoLancamento) VALUES (?,?,?,?)";
        logger.debug("Tentando inserir filme com título: {}", filme.getTitulo());

        int filmeId = -1; //ID padrão para debug de falhas.

        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            
            
            pstmt.setString(1, filme.getTitulo());
            pstmt.setInt(2, filme.getDuracaoMinutos());
            pstmt.setString(3, filme.getGenero());

            //Se for nulo ou vazio, usamos o "agora" (now())
            String dataParaGravar = filme.getAnoLancamento();
            if (dataParaGravar == null || dataParaGravar.isEmpty()){
                dataParaGravar = getDataAtualFormatada();
                logger.debug("Data de lançamento vazia, usando data atual: {}", dataParaGravar);
            }

            pstmt.setString(4, dataParaGravar);

            int linhasAfetadas = pstmt.executeUpdate();
            
            if(linhasAfetadas > 0){
                //Pega aqui o ID
                try(ResultSet generatedKeys = pstmt.getGeneratedKeys()){
                    filmeId = generatedKeys.getInt(1);
                    logger.info("Filme inserido com sucesso! ID: {}, Título: {}", filmeId, filme.getTitulo());
                }
            }

            return filmeId;        

        } catch (SQLException e) {
            logger.error("Erro ao inserir filme {}", e.getMessage(), e);
            return -1; //Retorna erro
        }
        
    }

    @Override
    public List<Filme> buscarTodos(){
        List<Filme> filmes = new ArrayList<>();
        String sql = "SELECT * FROM filmes";
        logger.debug("Buscando todos os filmes...");

        try (Connection conn = conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Filme filme = new Filme(rs.getInt("id"), rs.getString("titulo"), rs.getInt("duracaoMinutos"), rs.getString("genero"), rs.getString("anoLancamento"));
                filmes.add(filme);
            }
            logger.info("Busca de filmes finalizada. Total encontrado: {}", filmes.size());
            
            return filmes; //Retorno se tudo deu certo

        } catch (SQLException e) {
            logger.error("Erro ao buscar filmes: {}", e.getMessage(), e);
            return filmes; //Retorna a lista vazia ou parcialmente preenchida
        }        
    }

    @Override
    public Filme buscarPorId(int id){
        String sql = "SELECT * FROM filmes WHERE id = ?";
        Filme filme = null;
        logger.debug("Buscando filme por ID: {}", id);

        try(Connection conn = conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                filme = new Filme(rs.getInt("id"),rs.getString("titulo"),rs.getInt("duracaoMinutos"),rs.getString("genero"),rs.getString("anoLancamento"));
                logger.info("Filme encontrado: {}", filme.getTitulo());
            }else{
                logger.warn("Filme com ID {} não encontrado no banco.", id);
            }
            
            return filme;
        }
        catch (SQLException e){
            logger.error("Erro ao buscar filme por ID: {}: {}",id, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean atualizar (Filme filme){
        String sql = "UPDATE filmes SET titulo = ?, duracaoMinutos = ?, genero = ?,anoLancamento = ? WHERE id = ?";
        logger.debug("Tentando atualizar filme ID: {}", filme.getId());
        
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
            if (linhasAfetadas > 0){
                logger.info("Filme ID {} atualizado com sucesso.", filme.getId());
                return true;
            }else{
                logger.warn("Tentativa de atualizar filme ID {} falhou. Linhas afetadas: 0.", filme.getId());
                return false;
            }

        } catch (SQLException e) {
            logger.error("Erro ao atualizar filme ID {} : {}", filme.getId(), e.getMessage(), e);
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
            
            if (linhasAfetadas > 0){
                logger.info("Filme ID {} excluído com sucesso.", id);
                return true;
            }else{
                logger.warn("Tentativa de excluir filme ID {} falhou. Linhas afetadas: 0.", id);
                return false;
            }

        } catch (SQLException e) {
            logger.error("Erro ao excluir filme ID {}: {}", id, e.getMessage(), e);
            return false;
        }
    }
}
