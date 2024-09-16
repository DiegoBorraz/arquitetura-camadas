# JAVA - Arquitetura em camadas (Layered Architecture)

Um exemplo de  arquitetura em camadas que segue um padrão de design que organiza um sistema em camadas distintas, cada uma com suas responsabilidades específicas.


Neste projeto utilizei algumas tecnologias como:

* **Docker**
  * Onde especifico as imagens necessarias para rodar minha aplicação como o **Maven**, **JDK 17** e copio o jar da minha aplicação para dentro do container.
* **Compose**
  * Onde com um unico comando crio todos containers necessarios como banco **PostgreSQL**, executo um script de criação do banco chamado **init.sql** e executo o Docker onde esta a configuração da aplicação.
* **Swagger**
  * Onde visualiso todas requisições da api e posso executa-las.
* **Flyway**
  * Uma ferramenta de migração de banco de dados para criação das tabelas utilizadas.
* **JUnit**
  * Ferramenta para testes unitarios para cada requisição

## Quando devo usar?

* Quando o sistema é complexo
* Possui muitas regras de negócio, entidades e validações
* Quando precisa ser escalável ou quando a equipe é grande

**Exemplo**: Sistemas de e-commerce (CRM), sistemas de gestão empresarial (ERP) ou aplicações web complexas.

## Quando não devo usar?

* Quando o sistema é muito simples
* Projetos onde a velocidade de desenvolvimento e entrega é mais importante do que a estrutura do sistema
* Sistemas que exijam consumo de pouca memória e processamento
* Sistemas com comunicação em tempo real onde a complexidade da arquitetura pode afetar o desempenho 

**Exemplo**: Scripts de automação de tarefas, Sistemas embarcados, Sistema de aviação

## 🚀 Começando

Essas instruções permitirão que você obtenha uma cópia do projeto em operação na sua máquina local.

Consulte **[Instalação](#-instala%C3%A7%C3%A3o)** para saber como implantar o projeto.

### 📋 Pré-requisitos

Tudo você precisa para instalar e executar o software

```
Java JDK 17
Spring
Postgres
Docker
Docker Compose
Git
```
###  🛠️ Arquitetura
```
Arquitetura em camadas
```
```aiignore

src
.
├── main
│   ├── java
│   ├── com.br.javaCrud
│   │   ├── controller 
│   │   ├── core 
│   │   └── infrastructure
│   ├── resources
│   │   ├── db
│   │   └──── migration
│   └── application.properties
└── test
```

* **controller**
  * Contém as classes responsáveis por lidar com as requisições HTTP.
* **core**
  * Contém a lógica de negócio da aplicação, contém entidades, repositórios, services e especifications.
* **infrastructure**
  * Contém configurações de frameworks ou bibliotecas ou serviços externos.
* **migration**
  * Contém scripts para realizar migrações de banco de dados, ou seja, atualizar o esquema do banco de dados ao longo do tempo.
* **application.properties**
  * Arquivo de configuração da aplicação, onde são definidas as propriedades do sistema.
* **test**
  * Contém os testes unitários e de integração da aplicação.
  
### 📦 Instalação

* Clonar o Projeto
    ```
    git clone https://github.com/DiegoBorraz/arquitetura-camadas.git
    ```
* Acessar o diretório do Projeto
  ```
  cd java-crud
  ```
* Instalar Depencencias e já gerar o .jar do projeto
  ```
  mvn clean package
  ```
* Iniciar os containers do projeto Spring e do banco de dados PostgreSQL
  ```
  docker-compose up -d
  ``` 
* Para encerrar todos containers, volumes e imagem de um projeto.
    ```
    docker-compose down
    ```
* O projeto Spring rodar no endereço:
    ```
    http://localhost:8080
    ```
* Para acessar o Swagger e testa-lo:
    ```
    http://localhost:8080/swagger-ui/index.html
    ```
* Para acessar o banco **PostgreSQL** que esta no container:
    ```
    URL: jdbc:postgresql://0.0.0.0:5433/javacrudbd
    Host: 0.0.0.0
    Port: 5433
    Banco de dados: javacrudbd
    ```
* **Observação**:  Garanta que não tenha outras aplicações utilizando as portas **8080** e **5433** 



