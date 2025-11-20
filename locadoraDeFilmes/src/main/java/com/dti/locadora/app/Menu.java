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
            
            opcao = leitorInputs.lerInt("Escolha uma opção: ");
            
            
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
                    cadastrarFilme();    
                    
                    break;
                case 2:
                    listarFilmes();
                    

                    break;
                case 3:
                    buscarFilmes();

                    break;
                case 4:
                    atualizarFilme();
                      
                    break;
                case 5:
                    excluirFilme();  
                    break;
                case 0: 
                    //loop externo termina
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
        }
    }

    private void cadastrarFilme(){
        System.out.println("\n --- Cadastro de filme --- ");
        
        String tituloFilme = leitorInputs.lerString("Título: ");
        int duracao = leitorInputs.lerInt("Duração (em minutos): ");    
        String genero = leitorInputs.lerString("Gênero: ");

        Filme novoFilme = new Filme(tituloFilme,duracao ,genero);
        filmeDAO.inserir(novoFilme);
    }
    

    private void buscarFilmes(){
        System.out.println("\n --- Busca por filme ---");

        int id = leitorInputs.lerInt("Digite o ID do filme: ");

        Filme filme = filmeDAO.buscarPorId(id);

        if (filme != null){
            System.out.println("Filme encontrado:");
            System.out.println(filme);
        } else {
            System.out.println("Filme com ID " + id + " não encontrado.");
        }
    }

    private void listarFilmes(){

        System.out.println("\n --- Listagem de filmes ---");

        var listaFilmes = filmeDAO.buscarTodos();

        if (listaFilmes.isEmpty()){
            System.out.println("Nenhum filme cadastrado no momento.");
        } else{
            for(Filme filme : listaFilmes){
                System.out.println(filme);
            }
        }
    }

    private void atualizarFilme(){

        System.out.println("\n --- Atualizar filme ---");

        int id = leitorInputs.lerInt("Digite o ID do filme a ser atualizado: ");

        Filme filmeExistente = filmeDAO.buscarPorId(id);

        if(filmeExistente == null){
            System.out.println("Filme não encontrado. Operação cancelada.");
            return;
        }

        System.out.println("Dados atuais: " + filmeExistente.getTitulo());
        System.out.println(">> Digite os NOVOS dados abaixo:");

        String novoTitulo = leitorInputs.lerString("Novo Título: ");
        int novaDuracao = leitorInputs.lerInt("Nova duração (min): ");
        String novoGenero = leitorInputs.lerString("Novo gênero: ");

        Filme filmeAtualizado = new Filme(id, novoTitulo, novaDuracao, novoGenero);

        if(filmeDAO.atualizar(filmeAtualizado)){
            System.out.println("Filme atualizado com sucesso!");
        }else{
            System.out.println("Erro ao atualizar filme.");
        }
    }

    private void excluirFilme(){
        System.out.println("\n --- Excluir filme ---");

        int id = leitorInputs.lerInt("Digite o ID do filme a ser excluído: ");

        var escolha = leitorInputs.lerInt("Tem certeza? digite 1 para 'Sim'");

        if (escolha == 1){
            if(filmeDAO.excluir(id)){
                System.out.println("Filme excluído com sucesso!");
            }else{
                System.out.println("Erro ao excluir: Filme não encontrado ou erro no banco.");
            }
        }else{
            System.out.println("Cancelando operação...");
            return;
        }
    }
}
