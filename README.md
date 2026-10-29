# Atividade Prática – Persistência de Dados com ORM e CRUD

**Aluno:** Carlos  
**Disciplina:** Programação Orientada a Objetos  
**Tecnologias:** Java, BlueJ, ORMLite e SQLite

## 1. Classe escolhida: `Paciente`

A classe `Paciente` representa os pacientes de uma clínica. Ela possui os atributos id, nome, CPF, telefone e data de nascimento.

O ORMLite é utilizado para conectar os objetos Java ao banco de dados SQLite, permitindo salvar e consultar as informações dos pacientes.

## 2. Estrutura do projeto

O projeto possui as seguintes classes:

- `Database`: realiza a conexão com o banco de dados.
- `Paciente`: armazena os dados dos pacientes.
- `PacienteRepository`: realiza as operações no banco de dados.
- `TesteCRUD`: executa as operações para demonstrar o funcionamento.
- `PacienteRepositoryTest`: realiza os testes automatizados.

## 3. Operações CRUD

Foram implementadas as quatro operações do CRUD:

- **Create:** cadastra um paciente no banco de dados.
- **Read:** consulta um paciente pelo ID ou lista todos os cadastrados.
- **Update:** atualiza os dados de um paciente.
- **Delete:** exclui um paciente do banco de dados.

O ORMLite facilita essas operações, sem a necessidade de escrever os comandos SQL manualmente.

## 4. Testes realizados

Primeiramente, foram cadastrados dois pacientes: Ana Souza e João Lima.

Em seguida, foram realizadas consultas para verificar os dados cadastrados. O telefone da paciente Ana Souza foi atualizado e o paciente João Lima foi excluído.

Também foi realizada uma consulta direta no banco de dados para verificar se as alterações foram salvas corretamente.

Por fim, a conexão com o banco foi fechada e aberta novamente. Após a nova conexão, foi possível consultar os dados atualizados, confirmando que as informações continuaram salvas.

Os testes automatizados também foram executados, e os quatro testes passaram com sucesso.
