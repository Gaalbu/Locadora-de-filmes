package com.dti.locadora.app;
import java.io.IOException;

import com.dti.locadora.util.Leitor;
import com.dti.locadora.model.Filme;
import com.dti.locadora.dao.FilmeDAO;

/*
Entendendo que é uma aplicação de console, a classe menu
funcionará para organizar as funcionalidades do projeto,
deixando a classe App mais limpa.

*/

public class Menu {
    // Instancia DAO para acessar o banco.
    private final FilmeDAO filmeDAO = new FilmeDAO();

    //Instancia leitor para capturarmos o IO do usuário.
    private final Leitor leitorInputs = new Leitor();
    
    public void executar(){
        int opcao = -1;
        
        // loop padrão da aplicação de console.
        while (opcao != 0){
            exibirOpcoes();
            System.out.println("Escolha uma opção de 0 a 5");
            
            //opcao = leitorInputs.lerInt("Escolha uma opção: ");
            
            
            processarOpcao(opcao);
        }

        System.out.println("Saindo da aplicação. Até logo!");
    }

    private void exibirOpcoes(){
        System.out.println("=====================");
            System.out.println("1. Cadastrar um novo filme");
            System.out.println("2. Listar todos os filmes");
            System.out.println("3. Buscar um filme por ID");
            System.out.println("4. Atualizar informações do filme");
            System.out.println("5. Deletar filme cadastrado");
            System.out.println("0. Sair");
        System.out.println("=====================");
    }

    //Esqueleto das opções.
    private void processarOpcao(int opcao){
        switch (opcao) {
                case 1:
                    //cadastrarFilme();    
                    
                    break;
                case 2:
                    //listarFilmes();
                    

                    break;
                case 3:
                    //buscarFilmes();

                    break;
                case 4:
                    //atualizarFilme();
                      
                    break;
                case 5:
                    //excluirFilme();  
                    break;
                case 0: 
                    //loop externo termina
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private void cadastrarFilme() throws Exception{
        System.out.println("\n --- Cadastro --- ");
        
        //String tituloFilme = leitorInputs.lerString("Título: ");
        //int duracao = leitor.lerInt("Duração (em minutos): ")    
        //String genero = leitor.lerString("Gênero: ");

        //Filme novoFilme = new Filme(tituloFilme,duracao ,genero);
        //filmeDAO.inserir(novoFilme);
    }
    

    //continuar criando métodos crud no menu
}
