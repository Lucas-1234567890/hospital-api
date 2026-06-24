# Hospital API - Spring Boot + PostgreSQL

## Stack
- Java 17
- Spring Boot 3.2.5
- PostgreSQL 16
- Docker + Docker Compose

---

## Como rodar com Docker (recomendado)

### Pré-requisitos
- Docker Desktop instalado (ver instruções abaixo)

### Subir tudo com um comando
```bash
docker-compose up --build
```

A API estará disponível em: http://localhost:8080
Health check: http://localhost:8080/actuator/health

---

## Como instalar o Docker

### Windows
1. Acesse: https://www.docker.com/products/docker-desktop/
2. Baixe o "Docker Desktop for Windows"
3. Execute o instalador e reinicie o PC
4. Abra o Docker Desktop e aguarde o ícone ficar verde na bandeja do sistema

### Mac
1. Acesse: https://www.docker.com/products/docker-desktop/
2. Escolha a versão para Intel ou Apple Silicon (M1/M2/M3)
3. Instale e abra o Docker Desktop

### Linux (Ubuntu/Debian)
```bash
sudo apt-get update
sudo apt-get install docker.io docker-compose
sudo systemctl start docker
sudo usermod -aG docker $USER
```

---

## Como rodar sem Docker (Maven local)

### Pré-requisitos
- Java 17+
- Maven 3.8+
- PostgreSQL rodando na porta 5432 com banco `hospitaldb`

### Criar o banco no PostgreSQL
```sql
CREATE DATABASE hospitaldb;
```

### Rodar a aplicação
```bash
mvn spring-boot:run
```

### Rodar os testes
```bash
mvn test
```

---

## Endpoints disponíveis

### Pacientes
| Método | URL | Descrição |
|--------|-----|-----------|
| POST | /api/pacientes | Cadastrar paciente |
| GET | /api/pacientes | Listar todos |
| GET | /api/pacientes/{id} | Buscar por ID |
| DELETE | /api/pacientes/{id} | Remover paciente |

### Médicos
| Método | URL | Descrição |
|--------|-----|-----------|
| POST | /api/medicos | Cadastrar médico |
| GET | /api/medicos | Listar todos |
| GET | /api/medicos/ranking | Ranking por consultas |

### Consultas
| Método | URL | Descrição |
|--------|-----|-----------|
| POST | /api/consultas?pacienteId=1&medicoId=1 | Cadastrar consulta |

### Actuator
| Método | URL | Descrição |
|--------|-----|-----------|
| GET | /actuator/health | Status da aplicação |

---

## Exemplos de JSON

### Cadastrar Paciente
```json
POST /api/pacientes
{
  "nome": "João Silva",
  "cpf": "12345678901",
  "dataNascimento": "1985-03-15",
  "telefone": "71999990001"
}
```

### Cadastrar Médico
```json
POST /api/medicos
{
  "nome": "Dr. Carlos Andrade",
  "crm": "CRM-BA-12345",
  "especialidade": "Cardiologista"
}
```

### Cadastrar Consulta
```json
POST /api/consultas?pacienteId=1&medicoId=1
{
  "dataConsulta": "2025-06-25T10:00:00",
  "observacoes": "Paciente com pressão elevada"
}
```

---

## Dados iniciais (inseridos automaticamente)

**Médicos:**
- Dr. Carlos Andrade - Cardiologista (CRM-BA-12345)
- Dra. Fernanda Lima - Ortopedista (CRM-BA-67890)

**Pacientes:**
- João Silva - CPF: 12345678901
- Maria Oliveira - CPF: 98765432100
