package com.dti.locadora.util;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TabelaSQLite {
    private static final Logger logger = LoggerFactory.getLogger(TabelaSQLite.class);
    
    //Caminho relativo para 'resources', acessando sem erro.
    private static final String ARQUIVO_SCRIPT = "sql/criar_tabelas.sql";

    public static void criarTabelas(Connection conn){
        //Log de debug para querys de verificação e criação.
        logger.debug("Verificando a integridade das tabelas...");
        
        //Vai armazenar todo o nosso script para sua criação com os statements do SQLite.
        String scriptSql = "";

        try{
            //Utilizando de ClassLoader para achar o arquivo e funcionar mesmo que seja JAR.
            InputStream is = TabelaSQLite.class.getClassLoader().getResourceAsStream(ARQUIVO_SCRIPT);

            if (is == null){
                System.err.println("Arquivo SQL não encontrado: " + ARQUIVO_SCRIPT);
                return;
            }


            try(BufferedReader leitorArquivosSql = new BufferedReader(new InputStreamReader(is))){
                scriptSql = leitorArquivosSql.lines().collect(Collectors.joining("\n"));
            
            }catch(Exception e){
                logger.error("Erro ao ler o script SQL: {}" + e.getMessage(), e);
                return;
            }

            try(Statement stmt = conn.createStatement()){
                //Idealmente, para o SQLite, o arquivo deve ser simples (vários creates), permitindo vários statements separados por ';'
                //Se tudo for atendido corretamente, executamos a criação :)

                stmt.executeUpdate(scriptSql);
                logger.info("Tabelas verificadas/criadas com sucesso.");
            }catch(SQLException e){
                logger.error("Erro ao executar scripts SQL: {}" + e.getMessage(), e);
            }
        
        } catch (Exception e) { 
            logger.error("Ocorreu um erro durante a criação das tabelas: {}" + e.getMessage(), e);
        }
    }
}
