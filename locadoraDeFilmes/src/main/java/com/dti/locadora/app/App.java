package com.dti.locadora.app;

import com.dti.locadora.util.ConexaoDatabase;
import com.dti.locadora.util.TabelaSQLite;
import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger; //logging
import org.slf4j.LoggerFactory;

public class App 
{
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static void main( String[] args )
    {
        Menu menu = new Menu();

        logger.info("Inicializando sistema...");

        
        //Testa se a conexão do banco de dados realmente está funcionando.
        try (Connection conn = ConexaoDatabase.conectar()){
            TabelaSQLite.criarTabelas(conn);
        } //Fecha a conexão ao banco.
        
        catch (SQLException e) {
            logger.error("Erro fatal ao inicializar banco de dados: {}" + e.getMessage(), e);
            return; //Encerra o progama se não conseguir criar o banco.
        }
        
        
        menu.executar();
        
    }
}
