package com.dti.locadora.testes;

import com.dti.locadora.dao.FilmeDAO;
import com.dti.locadora.model.Filme;
import org.junit.jupiter.api.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
    Classe para validar o input, conforme solicitado na case.
    Essa classe é voltada para validar as regras de FilmeDAO.

*/

class FilmeDAOValidationTest { 
    
    private FilmeDAO filmeDAO;
    private static final DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM--dd HH:mm:ss");

    //Config ANTES de cada teste
    @BeforeEach
    void setUP(){
        //Simplificação da limpeza no banco de dados. 
        filmeDAO = new FilmeDAO();
    }

    //Validando a criação/inserção de cadastros

    @Test
    @DisplayName("Teste 1: Data de lançamento opcional (Deve ser data atual)")
    void testInserir_DataOpcional(){
        //Create com data vazia
        String dataVazia = "";
        Filme filme = new Filme("O teste vazio", 42, "Terror", dataVazia);

        filmeDAO.inserir(filme);

        //Buscando aqui o último inserido
        List<Filme> filmes = filmeDAO.buscarTodos();
        Filme filmeSalvo = filmes.get(filmes.size() - 1);

        assertNotNull(filmeSalvo.getAnoLancamento(), "A data não deve ser nula.");

        LocalDateTime dataSalva = LocalDateTime.parse(filmeSalvo.getAnoLancamento(), formatador);
        LocalDateTime agora = LocalDateTime.now();

        assertTrue(dataSalva.isAfter(agora.minusSeconds(60)),"A data de lançamento não foi preenchida com o valor NOW().");

    }

    //Validando alterações no banco de dados (Update)
    @Test
    @DisplayName("Teste 2: Título não nulo no UPDATE (barrar apagar texto)")
    void testAtualizar_TituloNaoPodeSerVazio(){
        //Aqui é validado para um filme VÁLIDO
        Filme filmeOriginal = new Filme("Titulo muito original", 99, "Comédia", "2022-01-02 01:02:03");
        filmeDAO.inserir(filmeOriginal);

        Filme filmeSalvo = filmeDAO.buscarTodos().stream()
                        .filter(f -> f.getTitulo().equals("Titulo muito original"))
                        .findFirst().orElse(null);
        assertNotNull(filmeSalvo, "Filme original não foi salvo.");

        //Validando um filme com título vazio (INCORRETO)
        Filme filmeParaAtualizar = new Filme(filmeSalvo.getId(), "", filmeSalvo.getDuracaoMinutos(), filmeSalvo.getGenero(), filmeSalvo.getAnoLancamento());

        //a implementação do DAO retorna uma exception caso não atualize
        boolean sucesso = filmeDAO.atualizar(filmeParaAtualizar);

        assertFalse(sucesso, "O UPDATE deveria ter falhado (return false) ao tentar inserir um título vazio.");

        //Validando se houve ruído no valor do banco.
        Filme filmeAposTentativa = filmeDAO.buscarPorId(filmeSalvo.getId());
        assertEquals("Titulo muito original", filmeAposTentativa.getTitulo(), "O título no banco não deveria ter sido apagado.");
        
    }
}
