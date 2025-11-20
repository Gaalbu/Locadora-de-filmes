package com.dti.locadora.app;

import com.dti.locadora.util.Leitor;
import com.dti.locadora.model.Filme;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    //Formatador para validação (o mesmo usado no DAO)
    private static final DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
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

    /*
    Força o usuário a digitar uma data válida ou deixar em branco.
    */
   private String lerDataValida(String mensagem){
        while(true){
            String input = leitorInputs.lerString(mensagem + " (yyyy-MM-dd HH:mm:ss) [ou 'Enter' para Data Atual]: ");

            if(input.trim().isEmpty()){
                return "";
            }

            try {
                LocalDateTime.parse(input,formatador);
                return input;
            } catch (Exception e) {
                System.out.println("Data inválida! Use o formato exato: yyyy-MM-dd HH:mm:ss");
                System.out.println("Exemplo: 2024-12-31 23:59:59");

            }
        }
   }

    private void cadastrarFilme(){
        System.out.println("\n --- Cadastro de filme --- ");
        String tituloFilme = leitorInputs.lerString("Título: ");
        String genero = leitorInputs.lerString("Gênero: ");
        
        int duracao = -1;
        while (duracao < 1 || duracao > 300) {
            //Valor arbitrário para duração de filmes.
            duracao = leitorInputs.lerInt("Duração (30 a 300 minutos): ");
            if (duracao < 30 || duracao > 300){
                System.err.println("Duração inválida. Deve estar entre 30 e 300 minutos.");
            }
        }
        

        //Data é opcional aqui, sendo now() por default...
        String data = lerDataValida("Data de lançamento");

        
        Filme novoFilme = new Filme(tituloFilme,duracao ,genero, data);
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
        int id = leitorInputs.lerInt("Digite o ID do filme que deseja alterar: ");
        
        //Busca aqui (debug)
        Filme filme = filmeDAO.buscarPorId(id);

        //VALIDAÇÃO se de fato um filme foi achado
        if(filme == null){
            System.out.println("Filme não encontrado");
            return;
        }

        //Loop específico para alterar
        int opcao = -1;
        while(opcao != 0){
            System.out.println("\n--------------------------------");
            System.out.println("Editando: " + filme.getTitulo()); // filme que tá no foco atual.
            System.out.println("Dados Atuais: " + filme); // toString que formatamos previamente
            System.out.println("--------------------------------");
            System.out.println("O que você deseja alterar?");
            System.out.println("1. Título");
            System.out.println("2. Duração");
            System.out.println("3. Gênero");
            System.out.println("4. Data de Lançamento");
            System.out.println("9. SALVAR E SAIR (Confirmar Alterações)");
            System.out.println("0. CANCELAR (Sair sem salvar)");

            opcao = leitorInputs.lerInt("Escolha: ");

            switch (opcao) {
                case 1:
                    System.out.println("Título atual: " + filme.getTitulo());
                    String novoTitulo = leitorInputs.lerString("Novo título: ");

                    //Aqui ainda altera apenas na memória de execução do programa.
                    //O que significa que não foi para o db...
                    filme.setTitulo(novoTitulo); 
                    System.out.println("Título alterado na memória.");
                    break;
                
                case 2:
                    System.out.println("Duração atual: " + filme.getDuracaoMinutos());
                    int novaDuracao = leitorInputs.lerInt("Nova duração: ");
                    while (novaDuracao < 0) {
                        novaDuracao = leitorInputs.lerInt("Digite uma nova duração válida (inteiro positivo.): ");
                    }
                    filme.setDuracaoMinutos(novaDuracao);
                    break;
            
                case 3:
                    System.out.println("Gênero atual: " + filme.getGenero());
                    String novoGenero = leitorInputs.lerString("Novo gênero: ");
                    filme.setGenero(novoGenero);
                    break;

                case 4:
                    System.out.println("Data atual: " + filme.getAnoLancamento());
                    System.out.println("Deixe vazio para usar data/hora atual.");
                    String novaData = lerDataValida("Nova Data");

                    if(novaData.isEmpty()){
                        novaData = null;
                    } 

                    filme.setAnoLancamento(novaData);
                    break;

                case 9:
                    //Enviamos o objeto com os novos dados para atualizar no db!
                    if(filmeDAO.atualizar(filme)){
                        System.out.println("Filme atualizado com sucesso no banco de dados!");
                    }else{
                        System.out.println("Erro ao salvar alterações.");
                    }
                    return; //Sai do método

                case 0:
                    System.out.println("Operação cancelada. Nenhuma alteração foi salva.");
                    return; //Sai como failsafe, sem alterações.


                default:
                    System.out.println("Opção inválida.");
                    break;
            }
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
