#!/bin/bash
# Script para criar as tabelas DynamoDB do chatbot do salão de cabeleireiro.
# Para DynamoDB Local (desenvolvimento): AWS_DYNAMODB_ENDPOINT=http://localhost:8000
# Para AWS (produção): remover --endpoint-url

ENDPOINT="${AWS_DYNAMODB_ENDPOINT:-http://localhost:8000}"
REGION="${AWS_REGION:-us-east-1}"

echo "Criando tabelas no endpoint: $ENDPOINT"

# Tabela: chatbot-sessoes
aws dynamodb create-table \
  --table-name chatbot-sessoes \
  --attribute-definitions AttributeName=telefone,AttributeType=S \
  --key-schema AttributeName=telefone,KeyType=HASH \
  --billing-mode PAY_PER_REQUEST \
  --endpoint-url "$ENDPOINT" \
  --region "$REGION" && echo "✅ chatbot-sessoes criada"

# Habilitar TTL na tabela de sessões
aws dynamodb update-time-to-live \
  --table-name chatbot-sessoes \
  --time-to-live-specification "Enabled=true, AttributeName=ttl" \
  --endpoint-url "$ENDPOINT" \
  --region "$REGION" && echo "✅ TTL habilitado em chatbot-sessoes"

# Tabela: chatbot-clientes
aws dynamodb create-table \
  --table-name chatbot-clientes \
  --attribute-definitions AttributeName=telefone,AttributeType=S \
  --key-schema AttributeName=telefone,KeyType=HASH \
  --billing-mode PAY_PER_REQUEST \
  --endpoint-url "$ENDPOINT" \
  --region "$REGION" && echo "✅ chatbot-clientes criada"

# Tabela: chatbot-profissionais
aws dynamodb create-table \
  --table-name chatbot-profissionais \
  --attribute-definitions AttributeName=profissionalId,AttributeType=S \
  --key-schema AttributeName=profissionalId,KeyType=HASH \
  --billing-mode PAY_PER_REQUEST \
  --endpoint-url "$ENDPOINT" \
  --region "$REGION" && echo "✅ chatbot-profissionais criada"

# Tabela: chatbot-servicos
aws dynamodb create-table \
  --table-name chatbot-servicos \
  --attribute-definitions AttributeName=servicoId,AttributeType=S \
  --key-schema AttributeName=servicoId,KeyType=HASH \
  --billing-mode PAY_PER_REQUEST \
  --endpoint-url "$ENDPOINT" \
  --region "$REGION" && echo "✅ chatbot-servicos criada"

# Tabela: chatbot-agendamentos (com GSI profissionalId-dataHora-index)
aws dynamodb create-table \
  --table-name chatbot-agendamentos \
  --attribute-definitions \
    AttributeName=clienteTelefone,AttributeType=S \
    AttributeName=agendamentoId,AttributeType=S \
    AttributeName=profissionalId,AttributeType=S \
    AttributeName=dataHora,AttributeType=S \
  --key-schema \
    AttributeName=clienteTelefone,KeyType=HASH \
    AttributeName=agendamentoId,KeyType=RANGE \
  --global-secondary-indexes '[
    {
      "IndexName": "profissionalId-dataHora-index",
      "KeySchema": [
        {"AttributeName": "profissionalId", "KeyType": "HASH"},
        {"AttributeName": "dataHora", "KeyType": "RANGE"}
      ],
      "Projection": {"ProjectionType": "ALL"}
    }
  ]' \
  --billing-mode PAY_PER_REQUEST \
  --endpoint-url "$ENDPOINT" \
  --region "$REGION" && echo "✅ chatbot-agendamentos criada (com GSI)"

echo ""
echo "✅ Todas as tabelas criadas com sucesso!"
