**<h2>🎬 Locadora de Filmes DTI</h2>**
Este projeto é uma aplicação Java de console para gerenciamento de um catálogo de filmes, desenvolvida como estudo de caso para o processo seletivo da **DTI Digital**.

A aplicação implementa um **CRUD completo** (Create, Read, Update e Delete), persistindo dados em banco **SQLite**, seguindo boas práticas de arquitetura de software, testes unitários automatizados e logging profissional.

**<h2>🚀 Tecnologias Utilizadas</h2>**
* **Java 17**: Linguagem principal.

* **Apache Maven**: Gerenciamento de dependências e build.

* **SQLite (JDBC)**: Banco de dados relacional (Serverless).

* **JUnit 5 (Jupiter)**: Framework de testes unitários e integração.

* **SLF4J + SimpleLogger**: Fachada de logging para controle de verbosidade.

**<h2>🏗️ Arquitetura do Projeto</h2>**
O projeto segue o padrão **DAO (Data Access Object)** para isolar a lógica de persistência da regra de negócio.



* `com.dti.locadora.model`: Classes que representam entidades(Filme).

* `com.dti.locadora.dao`: Interfaces e implementações de acesso a dados.

* `com.dti.locadora.util`: Utilitários de conexão (JDBC) e leitura de scripts SQL.

* `com.dti.locadora.app`: Ponto de entrada (App.java) e interface de console(Menu).

* `resources/sql`: Scripts SQL para criação automática das tabelas.

**<h2>📂 Descrição do Recurso: Filme</h2>**

O sistema gerencia a entidade **Filme**, que representa o item principal do catálogo da locadora

**Propriedades e Tipos de Dados**

| Propriedade | Tipo (Java) | Tipo (SQL) | Obrigatório? | Descrição|

**ID** | `int` | `INTEGER PK` | **Sim** | Identificador único gerado automaticamente pelo banco. |
**Título** | `String` | `TEXT` | **Sim** | Nome do filme. Não pode ser vazio ou nulo. |
**Duração** | `int` | `INTEGER` | **Sim** | Duração em minutos (Validação: 30 a 300 min). |
**Gênero** | `String` | `TEXT` | **Não** | Gênero principal da obra (Ação, Drama, etc). |
**Data Lançamento** | `String` | `DATETIME` | **Não** | Data de cadastro. *Se vazio, assume a data/hora atual.* |


**<h2>⚙️ Pré-requisitos</h2>**
Certifique-se de ter instalado:
1. **Java JDK 17** (ou superior).
2. **Maven** (instalado e configurado no PATH).

**<h2>📦 Instalação e Build</h2>**
1. **Clone o repositório**:

`git clone https://github.com/Gaalbu/Locadora-de-filmes.git`

`cd locadoraDeFilmes`

2. **Compile e baixe as dependências**:

`mvn clean install`

*obs: Este comando também executará os testes unitários automaticamente.*

**<h2>▶️ Como Rodar a Aplicação</h2>**
A aplicação pode ser executada diretamente via Maven ou através da sua IDE de preferência.

**Via terminal (Recomendado!)**
Para iniciar o Menu:
`mvn exec:java`

**Obs:** Na primeira execução, o sistema criará automaticamente o arquivo do banco de dados `locadora_db.db` na raiz do projeto.

**<h2>🧪Executando Testes Unitários</h2>**
O projeto possui um conjunto de testes que valida regras de negócio e integridade do banco de dados (garantindo que inserções e atualizações respeitem as restrições).

Para rodar os testes isoladamente:

`mvn test`

*Os testes são configurados para criar e popular as tabelas antes de cada execução.*

**<h2>📝 Configuração de Logs (Debug vs. Usuário)</h2>**
O projeto utiliza *SLF4J* para gerenciar logs. Você pode alternar entre ver detalhes técnicos (SQL, conexões) ou apenas a interface limpa do usuário.

1. **Modo Usuário (Padrão)**
Exibe apenas o menu e mensagens importantes.

`mvn exec:java -Dorg.slf4j.simpleLogger.defaultLogLevel=info`

2. **Modo Desenvolvedor (Debug com mais detalhes)**
Exibe queries SQL executadas, tentativas de conexão e logs internos do DAO.

`mvn exec:java -Dorg.slf4j.simpleLogger.defaultLogLevel=debug`

**<h2>🛠️ Solução de Problemas Comuns</h2>**

**Erro: "Database is locked"**

* **Causa:** Você provavelmente está com o arquivo `locadora_db.db` aberto em algum visualizador de banco de dados (DBeaver, DB Browser).
* **Solução:** Feche a conexão no visualizador antes de rodar a aplicação ou os testes.

**Erro: "no such column: anoLancamento"**

* **Causa:** O arquivo de banco de dados antigo possui uma estrutura desatualizada.

* **Solução:** Delete o arquivo `locadora_db.db` da raiz do projeto e rode a aplicação novamente. O sistema criará um novo com a estrutura correta.

**Caracteres estranhos (WINDOWS)**

* **Solução:** Se houver problemas de acentuação no terminal do Windows, execute o comando abaixo antes de rodar o Maven:

`env:MAVEN_OPTS="-Dfile.encoding=UTF-8"`

**<h2>✒️ Autor</h2>**
Desenvolvido por **Gabriel Albuquerque Alencar** para o processo seletivo da DTI digital.