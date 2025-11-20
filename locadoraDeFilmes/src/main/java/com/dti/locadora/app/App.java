package com.dti.locadora.app;

import com.dti.locadora.util.ConexaoDatabase;
import com.dti.locadora.util.TabelaSQLite;
import java.sql.Connection;
import java.sql.SQLException;

//Importando para sanar erros de compilação
import com.dti.locadora.app.Menu;

public class App 
{
    public static void main( String[] args )
    {
        Menu menu = new Menu();

        System.out.println("Inicializando sistema...");

        
        //Testa se a conexão do banco de dados realmente está funcionando.
        try (Connection conn = ConexaoDatabase.conectar()){
            TabelaSQLite.criarTabelas(conn);
            System.out.println("Conexão aberta com sucesso!");
        } //Fecha a conexão ao banco.
        
        catch (SQLException e) {
            System.err.println("Erro fatal ao inicializar banco de dados: " + e.getMessage());
            return; //Encerra o progama se não conseguir criar o banco.
        }
        
        
        menu.executar();
        
    }
}
