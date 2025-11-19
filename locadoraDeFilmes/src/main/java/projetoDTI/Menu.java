package projetoDTI;
import java.io.IOException;

/*
Entendendo que é uma aplicação de console, a classe menu
funcionará para organizar as funcionalidades do projeto,
deixando a classe App mais limpa.

*/

public class Menu {
    Leitor leitorInputs = new Leitor();
    
    public void executar(){
        int opcao = 0;
        
        // loop padrão da aplicação de console.
        do{
            System.out.println("=====================");
            System.out.println("1. Listar todos os filmes cadastrados");
            System.out.println("2. Buscar um filme por ID");
            System.out.println("3. Cadastrar um novo filme");
            System.out.println("4. Atualizar informações do filme");
            System.out.println("5. Deletar filme cadastrado");

            //Verificação se a entrada está correta.
            try {
                opcao = Integer.parseInt(leitorInputs.IOinput());
            } catch (IOException e) {
                System.out.println(e);
            }
            
            //Esqueleto das opções.
            switch (opcao) {
                case 1:
                    //Listagem de todos os filmes cadastrados
                    try {
                        //locadora.listarCadastros();
                    } catch (Exception e) {
                        
                    }

                    break;
                case 2:
                    //Buscar filme por ID.
                    try{
                       //locadora.buscarCadastro();
                    }catch (Exception e){
                        
                    }

                    break;
                case 3:
                    //Cadastro de filmes.
                    try{
                        //locadora.cadastrarFilme();
                    }catch (Exception e){
                        
                    }

                    break;
                case 4:
                    //Alteração em um cadastro.
                    try {
                        //locadora.alterarCadastro();
                    } catch (Exception e) {
                        System.out.println("Não há pets cadastrados.");
                    }
                    break;
                case 5:
                    //Deletar um cadastro.
                    try{
                        //locadora.excluirCadastro();
                    }catch(Exception e){
                        System.out.println("Não há pets cadastrados/Não há pets que atinjam os critérios. Motivo:" + e.getMessage());
                    }

                    break;
                case 6:
                    
                    
                    break;
            }

        }while (opcao != 6);
    }
}
