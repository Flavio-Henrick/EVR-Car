# EVR Car — Backend Java

API REST inicial para a loja EVR Car, construída com Java 21, Spring Boot, Spring Security, JWT e PostgreSQL.

## Requisitos
- Java 21
- Maven 3.9+
- Docker Desktop (ou PostgreSQL 16 instalado)
- Conta Cloudinary para upload de fotos

## Executar localmente
1. Copie `.env.example` para `.env` e configure as credenciais. O Spring Boot não carrega `.env` automaticamente: exporte as variáveis no terminal ou use uma configuração de execução da IDE.
2. Inicie o banco: `docker compose up -d`
3. Configure as variáveis de ambiente do `.env.example` no terminal/IDE.
4. Execute: `mvn spring-boot:run`

Health check: `GET http://localhost:8080/api/health`

## Login administrativo
`POST /api/auth/login`
```json
{"username":"admin","password":"SUA_SENHA"}
```
Resposta:
```json
{"token":"...","tokenType":"Bearer","expiresInSeconds":43200}
```
Envie `Authorization: Bearer TOKEN` nas rotas administrativas.

O usuário e a senha configurados em `ADMIN_USERNAME` e `ADMIN_PASSWORD` são um acesso inicial via variáveis de ambiente. Não deixe a senha padrão em produção. Para produção, implemente também fluxo de troca/rotação de senha e cadastro persistente de administradores.

## Rotas públicas
- `GET /api/cars` — lista somente veículos disponíveis.
- `GET /api/cars/{id}` — detalhes de veículo não vendido.
- `GET /api/health` — status da API.

## Rotas administrativas (JWT obrigatório)
- `GET /api/admin/cars` — lista todos os veículos.
- `POST /api/admin/cars` — cadastra veículo.
- `PUT /api/admin/cars/{id}` — edita dados, preço, status e lista de URLs de fotos.
- `DELETE /api/admin/cars/{id}` — exclui veículo.
- `POST /api/admin/cars/{id}/photos` — upload multipart com campo `file`.
- `DELETE /api/admin/cars/{id}/photos/{index}` — remove foto pela posição (começa em zero).

### Exemplo de veículo
```json
{
  "brand": "Toyota",
  "model": "Corolla XEi",
  "yearModel": 2022,
  "yearManufacture": 2021,
  "mileage": 35000,
  "fuel": "Flex",
  "transmission": "Automático",
  "color": "Prata",
  "price": 129900.00,
  "description": "Veículo em excelente estado.",
  "status": "AVAILABLE",
  "photos": []
}
```
Status aceitos: `AVAILABLE`, `RESERVED`, `SOLD`.

## Upload de fotos
Configure `CLOUDINARY_CLOUD_NAME`, `CLOUDINARY_API_KEY` e `CLOUDINARY_API_SECRET`. O endpoint de upload armazena a imagem no Cloudinary e grava sua URL no veículo. Limite: 10 MB por imagem.

## Importante antes de produção
- Trocar todas as senhas e o `JWT_SECRET`.
- Restringir CORS ao domínio definitivo do frontend.
- Usar HTTPS e PostgreSQL gerenciado com backup.
- Configurar variáveis secretas no provedor de hospedagem, nunca commitar `.env`.
- Adicionar rate limiting, logs/auditoria e política de retenção.
- O frontend precisa consumir os endpoints desta API; o painel administrativo visual ainda precisa ser integrado/criado no frontend.

## Contrato de integração
O backend retorna os veículos como JSON com campos `id`, `brand`, `model`, `yearModel`, `yearManufacture`, `mileage`, `fuel`, `transmission`, `color`, `price`, `description`, `status`, `photos`, `createdAt` e `updatedAt`.
