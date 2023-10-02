# API-CadastroDePessoa
Esse foi um projeto com a finalidade de construir uma API de cadastro de Pessoa com Java e SpringBoot. Ela foi pensada para o gerenciamento de Pessoas, sendo possível incluir, alterar, consultar e excluir.

## Configurando o banco de dados

Nesse projeto foi utilizado o Docker para executar o PostgreSQL da aplicação.

Acesse a pasta do projeto e execute:

    docker-compose up -d

Informações do banco de dados:

    spring.datasource.url=jdbc:postgresql://localhost:5432/cadastro  
    spring.datasource.username=admin  
    spring.datasource.password=admin

## Postman

Arquivo disponível do postman para GET, POST, PUT e DELETE

 - [Collection](https://github.com/junior-calado/API-CadastroDePessoa/blob/main/CadastroPessoa.postman_collection.json)

## Endpoints

### Incluir Pessoa

#### Request

- URL: `/api/contacts`
- Method: `POST`

#### Request Body

```json
{
    "name": "Junior",
    "cpf": "104.268.129-56",
    "birthDate": "2004-06-30",
    "contacts": [
        {
            "name": "Alison",
            "phone": "44 9 9999-8888",
            "email": "contato@gmail.com"
        },
        {
            "name": "joao",
            "phone": "44 9 9999-8888",
            "email": "contato@gmail.com"
        }
    ]
}
```

### Buscar Pessoa

#### Request

- URL: `/api/contacts/search`
- Method: `GET`
- Parâmetros de consulta: 
  - `nome`
  - `cpf`
 
### Busca Paginada para buscar Pessoa

#### Request

- URL: `/api/contacts/`
- Method: `GET`
- Parâmetros de consulta: 
  - `page`
  - `size`

### Buscar Pessoa por ID

#### Request

- URL: `/api/contacts/{id}`
- Method: `GET`


### Editar Pessoa

#### Request

- URL: `/api/contacts/{id}`
- Method: `PUT`

##### Request Body

```json
{
    "name": "Vitao",
    "cpf": "09473787901",
    "birthDate": "2004-06-30",
    "contacts": [
        {
            "name": "Pedro",
            "phone": "44 9 9999-8888",
            "email": "contato@gmail.com"
        },
        {
            "name": "Marcelo",
            "phone": "44 9 9999-8888",
            "email": "contato@gmail.com"
        }
    ]
}
```

### Deletar Pessoa

#### Request

- URL: `/api/contacts/{id}`
- Method: `DELETE`
