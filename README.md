# SkyZero

SkyZero é uma aplicação para registro e cálculo de emissões de carbono com base em voos. Este projeto integra back-end em Java, banco de dados Oracle, e um front-end para interface com o usuário.

## 🌐 Link do Front-End
Acesse o front-end do projeto em: [SkyZero](https://fiap-gs-skyzero.vercel.app/)

## Equipe

| **Nome**                | **RM**   |
|-------------------------|----------|
| **Allan Brito Moreira** | RM558948 |
| **Caio Liang**          | RM558868 |
| **Levi Magni**          | RM98276  |

## Estrutura do Projeto

- **.env.sample**: Arquivo de configuração na raiz do projeto que deve ser duplicado e renomeado para `.env`, onde serão
  inseridas as credenciais do banco de dados.
- No diretório **utils** do projeto, você encontrará os arquivos:
  - **Script SQL**: Execute os scripts SQL para criar as tabelas e sequences do banco de dados;
  - **Arquivo JSON**: Use o arquivo de collections no Postman para realizar testes das APIs.


```
src/
├── br/
    ├── com/
        ├── skyzero/
            ├── utils/
                └── skyzero-script.sql
                └── skyzero_postman_collection.json
```

## Instruções para Rodar o Projeto

1. Clone o repositório:

  ```bash
  git clone https://github.com/levmn/fiap-skyzero.git
  ```

  ```bash
  cd fiap-skyzero
  ```

2. Duplique o arquivo `.env.sample` para `.env` e insira as suas credenciais:

  ```
  DB_URL=jdbc:oracle:thin:@<host>:<port>:<service>
  DB_USER=<seu_usuario>
  DB_PASSWORD=<sua_senha>
  ```

3. **Inicie a aplicação**: A classe Main localizada no pacote **br.com.skyzero.app** é a responsável por subir o
   servidor e iniciar o projeto. Para executá-la:

    - Navegue até a classe Main em `src/br/com/skyzero/app/Main.java`.
    - Execute a classe Main para iniciar o servidor com o Grizzly HTTP.

A aplicação ficará disponível em http://localhost:8080/.