# AtlasExp API

## Descrição do Projeto

AtlasExp é uma API REST desenvolvida como projeto final da disciplina de Engenharia de Software. O sistema permite o gerenciamento de viagens, atividades e fotos de forma colaborativa, com funcionalidades como auditoria de ações e sistema de curtidas em fotos.

## Integrantes do Grupo

- Evandro Luiz Rodrigues Damazio  
- Abdiel Torazzi Paulino

## Descrição do Problema

Muitos usuários que desejam registrar e compartilhar suas experiências de viagem não possuem uma plataforma estruturada para isso. O AtlasExp oferece uma solução completa que organiza viagens, atividades e fotos em um sistema auditável e colaborativo.

## Tecnologias Utilizadas

- Java 17  
- Spring Boot  
- PostgreSQL  
- JPA/Hibernate  
- Swagger/OpenAPI  
- JUnit e Mockito  
- Maven

## Limitações do Projeto

- Upload real de imagens não está implementado (simulado via DTO).
- Interface gráfica web ainda não integrada (utilização via Swagger ou Postman).
- Apenas entidades principais estão disponíveis nesta versão inicial (MVP).

## Descrição das Entidades

- **User**: nome, email, senha (criptografada), status premium.
- **Trip**: título, descrição, data, vínculo com usuário.
- **Activity**: descrição, data, vínculo com viagem.
- **Photo**: imagem (representada como URL ou base64), vínculo com usuário e viagem.
- **PhotoLike**: representa uma curtida de um usuário em uma foto.
- **AuditLog**: registro de ações CREATE, UPDATE, DELETE, LIKE e UNLIKE com timestamp e usuário responsável.

## Descrição das Rotas

A documentação completa está disponível via Swagger:

```
http://localhost:8080/swagger-ui.html
```

### Exemplos de Endpoints

| Método | Rota              | Descrição                |
|--------|-------------------|--------------------------|
| POST   | /users            | Criação de usuário       |
| POST   | /trips            | Criação de viagem        |
| GET    | /trips            | Listagem de viagens      |
| PUT    | /trips/{id}       | Atualização de viagem    |
| DELETE | /trips/{id}       | Remoção de viagem        |
| POST   | /activities       | Criação de atividade     |
| DELETE | /activities/{id}  | Remoção de atividade     |
| POST   | /photos           | Registro de foto         |
| POST   | /photos/{id}/like | Curtida em uma foto      |
| DELETE | /photos/{id}/like | Remoção de curtida       |
| DELETE | /photos/{id}      | Deletar foto             |
| GET    | /log              | Visualizar auditoria     |

## Exemplos de Requisições e Respostas

**POST /users**

```json
{
  "name": "Maria Silva",
  "email": "maria@email.com",
  "password": "123456"
}
```

**Resposta:**
```json
{
  "id": 1,
  "name": "Maria Silva",
  "email": "maria@email.com",
  "premium": false
}
```

**GET /trips**

**Resposta:**
```json
[
  {
    "id": 1,
    "title": "Viagem ao Chile",
    "description": "Uma aventura pelos Andes",
    "date": "2024-12-01",
    "userId": 3
  }
]
```

## Exemplos de Erros HTTP

| Código | Descrição                           |
|--------|-------------------------------------|
| 400    | Dados inválidos ou ausentes         |
| 401    | Autenticação requerida              |
| 403    | Acesso não autorizado               |
| 404    | Recurso não encontrado              |

## Como Executar o Projeto Localmente

1. Clonar o repositório:

```bash
git clone https://github.com/seu-usuario/atlasexp-api.git
cd atlasexp-api
```

2. Configurar o banco de dados PostgreSQL conforme `application.properties`.

3. Rodar com Maven:

```bash
./mvnw spring-boot:run
```

4. Acessar a API via navegador ou ferramentas como Swagger ou Postman.

## Outros Conteúdos Relevantes

- **Auditoria:** Todas as ações sensíveis (create, update, delete, like, unlike) são registradas na tabela `audit_log`, acessível via rota `/log`.
- **Testes:** Cobertura de teste com JUnit e Mockito.
- **DTOs e Validadores:** Separação clara entre entidades e objetos de transporte, com validações de entrada.