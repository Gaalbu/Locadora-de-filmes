package locadoraFilmes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDatabase {
    
    //Url foi feita com path relativa a execução do projeto
    private static final String URL_CONEXAO = "jdbc:sqlite:locadora_db.db";
    
    public static Connection conectar(){
        Connection conn = null;

        try {
            //Criando a conexão com o banco 
            
            conn = DriverManager.getConnection(URL_CONEXAO);
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
        } catch (SQLException e) {
            // erro de conexão detectado.
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());

        } finally{
            //Fechando a conexão para liberar recursos do sistema.

            try {
                if (conn != null){
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
        return conn;
    }
}
