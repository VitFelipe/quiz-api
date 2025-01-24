# Quiz Aleitamento

Este é um projeto Spring Boot para um quiz sobre aleitamento materno.

## Requisitos

- Java 17
- MySQL 8
- Maven

## Configuração do Banco de Dados

1. Crie um banco de dados MySQL chamado `quiz_aleitamento`
2. Configure as credenciais do banco no arquivo `application.properties`

## Como executar

1. Clone o repositório
2. Configure o banco de dados conforme instruções acima
3. Execute o comando: `mvn spring-boot:run`

## Estrutura do Projeto

O projeto possui as seguintes entidades principais:

- Usuario: Gerencia os usuários do sistema
- Nivel: Define os níveis de dificuldade do quiz
- Pergunta: Armazena as perguntas do quiz
- Opcao: Armazena as opções de resposta para cada pergunta
- Jogo: Registra as partidas jogadas
- RespostaJogo: Registra as respostas dadas em cada jogo
- ConfiguracaoJogo: Configurações gerais do jogo

## Validações

O projeto utiliza Bean Validation para validar as requisições:

- Campos obrigatórios são validados com @NotNull e @NotBlank
- Tamanho de strings é validado com @Size
- Emails são validados com @Email

## Segurança

O projeto utiliza Spring Security com JWT para autenticação e autorização.
