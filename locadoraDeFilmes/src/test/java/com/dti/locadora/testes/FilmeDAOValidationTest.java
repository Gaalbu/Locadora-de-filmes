package com.dti.locadora.testes;

import com.dti.locadora.util.ConexaoDatabase;
import com.dti.locadora.util.TabelaSQLite;
import java.sql.Connection;
import java.sql.SQLException;
import com.dti.locadora.dao.FilmeDAO;
import com.dti.locadora.model.Filme;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

/*
    Classe para validar o input, conforme solicitado na case.
    Essa classe é voltada para validar as regras de FilmeDAO.

*/

class FilmeDAOValidationTest { 
    
    private FilmeDAO filmeDAO;
    private static final DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //Config ANTES de cada teste
    @BeforeEach
    void setUP(){
        //Simplificação da limpeza no banco de dados.   
        filmeDAO = new FilmeDAO();

        try (Connection conn = ConexaoDatabase.conectar()) {
            TabelaSQLite.criarTabelas(conn);
        } catch (SQLException e) {
            fail("Erro ao criar tabela para testes: " + e.getMessage());
        }
    }

    //Validando a criação/inserção de cadastros

    @Test
    @DisplayName("Teste 1: Data de lançamento opcional (Deve ser data atual)")
    void testInserir_DataOpcional(){
        //Create com data vazia
        String dataVazia = "";
        Filme filme = new Filme("O teste vazio", 42, "Terror", dataVazia);

        int idSalvo = filmeDAO.inserir(filme);

        assertTrue(idSalvo > 0);

        Filme filmeSalvo = filmeDAO.buscarPorId(idSalvo);

        assertNotNull(filmeSalvo,"Filme não foi encontrado após inserção.");
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
        int idSalvo = filmeDAO.inserir(filmeOriginal);

        assertTrue(idSalvo > 0);

        Filme filmeSalvo = filmeDAO.buscarPorId(idSalvo);

        //Tenta atualizar o título para vazio
        Filme filmeParaAtualizar = new Filme(filmeSalvo.getId(), "", filmeSalvo.getDuracaoMinutos(), filmeSalvo.getGenero(), filmeSalvo.getAnoLancamento());

        boolean sucesso = filmeDAO.atualizar(filmeParaAtualizar);

        assertFalse(sucesso, "O UPDATE deveria ter falhado ao tentar inserir título vazio.");

        Filme filmeAposTentativa = filmeDAO.buscarPorId(idSalvo);
        assertEquals("Titulo muito original", filmeAposTentativa.getTitulo(), "O título no banco não deve foi apagado após a falha do UPDATE.");
    }
}
