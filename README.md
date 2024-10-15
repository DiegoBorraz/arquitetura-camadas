# JAVA - Arquitetura em camadas(Layered Architecture)

Um exemplo de  arquitetura em camadas que segue um padrão de design que organiza um sistema em camadas distintas, cada uma com suas responsabilidades específicas.

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
└── main
   ├── java
   ├── com.br.javaCrud
   │   ├── config
   │   ├── controller 
   │   ├── domain
   │   ├── dto
   │   ├── enums
   │   ├── record
   │   ├── repository
   │   └── service 
   └── resources
       ├── db
       ├── migration
       └── application.properties

```

* **config**
  * Contém classes de configuração com as exceções padrões, classe de configuração da Specification e configuração do ModelMapper.
* **controller**
  * Contém classes responsáveis por lidar com as requisições HTTP.
* **domain**
  * Contém classes que representam as tabelas do banco.
* **dto**
  * Contém classes responsáveis por transportar informações de servidor/cliente protejendo a domain.
* **enums**
  * Contém todos enums utilizados no sistema.
* **record**
  * Contém classes do tipo Record que simplificam o tranposrte de dados, utilizada para tipar as propriedades de entrada da requisição HTTP.
* **repository**
  * Contém Classes que encapsulam a logica JPA para consulta no banco.
* **service**
  * Contém Classes que encapsulam a lógica da regra de negócioe utiliza o repository.
* **util**
  * Contém Classes que auxiliam uma determinada tarefa.

* **application.properties**
  * Arquivo de configuração da aplicação, onde são definidas as propriedades do sistema.

  
### 📦 Instalação

* Clonar o Projeto
    ```
    git clone 
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



