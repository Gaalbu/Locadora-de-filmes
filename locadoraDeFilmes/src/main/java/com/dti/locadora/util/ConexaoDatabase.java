package com.dti.locadora.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDatabase {
    
    //Url foi feita com path relativa a execução do projeto
    private static final String URL_CONEXAO = "jdbc:sqlite:locadora_db.db";
    
    public static Connection conectar() throws SQLException{
        Connection conn = DriverManager.getConnection(URL_CONEXAO);

        if (!conn.isClosed()){
            System.out.println("Conexão aberta com sucesso!");
        }

        
        return conn;
    }
}
