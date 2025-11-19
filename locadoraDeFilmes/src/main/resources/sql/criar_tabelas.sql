--criando a tabela de filmes a partir dos requisitos.
--Obrigatórios: duracaoMinutos(numérico) e titulo(Text ou string)
--Opcionais: anoLancamento


CREATE TABLE IF NOT EXISTS filmes(
    id INTEGER PRIMARY KEY,
    duracaoMinutos INTEGER NOT NULL,
    titulo TEXT NOT NULL,
    anoLancamento DATETIME 
);