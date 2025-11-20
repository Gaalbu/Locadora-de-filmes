package locadoraFilmes;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class TabelaSQLite {
    //Caminho relativo para 'resources', acessando sem erro.
    private static final String ARQUIVO_SCRIPT = "sql/criar_tabelas.sql";

    public static void criarTabelas(Connection conn){
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
                System.err.println("Erro ao ler o script SQL: " + e.getMessage());
                return;
            }

            try(Statement stmt = conn.createStatement()){
                //Idealmente, para o SQLite, o arquivo deve ser simples (vários creates), permitindo vários statements separados por ';'
                //Se tudo for atendido corretamente, executamos a criação :)

                stmt.executeUpdate(scriptSql);
                System.out.println("");
            }catch(SQLException e){
                System.err.println("Erro ao executar scripts SQL: " + e.getMessage());
            }
        
        } catch (Exception e) { 
            System.err.println("Ocorreu um erro durante a criação das tabelas: " + e.getMessage());
        }
    }
}
