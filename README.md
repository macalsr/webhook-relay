
# Projeto - API (Spring Boot) + PostgreSQL

API em **Spring Boot** com **PostgreSQL** e migrations via **Flyway**.

---

## ✅ Pré-requisitos

- Java (compatível com o projeto)
- Maven (ou Maven Wrapper)
- Docker (opcional, para subir o Postgres local)
- PostgreSQL (se não usar Docker)

---

## 🐘 Subindo PostgreSQL local com Docker (opcional)

Ajuste usuário/senha/db conforme necessário:

```bash
docker run --name postgres-local \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=app \
  -p 5432:5432 \
  -d postgres:16
````

---

## ⚙️ Configuração da aplicação

Configure o acesso ao banco no `application.yml` / `application.properties` (ou via variáveis de ambiente):

* `spring.datasource.url`
* `spring.datasource.username`
* `spring.datasource.password`

Exemplo (YAML):

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/app
    username: postgres
    password: postgres
```

---

## 📁 Migrations (Flyway)

As migrations ficam em:

* `src/main/resources/db/migration`

Padrão de nomes:

* `V1__init.sql`
* `V2__add_table_x.sql`

---

## ▶️ Rodando o projeto

### Linux/macOS

```bash
mvn -U clean package
```

### Windows (Maven Wrapper)

```bash
./mvnw.cmd -U clean package
```

Se quiser rodar direto pela IDE, basta iniciar a classe `Application` do Spring Boot.

---

## 🧪 Testes

```bash
mvn test
```

---

## 🧯 (Opcional) Desabilitar Flyway em um profile

Ex.: para `test`:

```yaml
spring:
  flyway:
    enabled: false
```

---

## 📌 Troubleshooting rápido

* **Porta 5432 ocupada**: pare o Postgres local ou mude a porta do Docker (`-p 5433:5432`) e ajuste a URL.
* **Banco não sobe**: confira logs do container:

  ```bash
  docker logs -f postgres-local
  ```
* **Conexão recusada**: verifique se o Postgres está rodando e se a URL/credenciais estão corretas.

```
::contentReference[oaicite:0]{index=0}
```
