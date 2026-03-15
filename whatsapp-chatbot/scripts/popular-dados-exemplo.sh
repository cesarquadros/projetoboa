#!/bin/bash
# Script para popular o DynamoDB com dados de exemplo para teste.

ENDPOINT="${AWS_DYNAMODB_ENDPOINT:-http://localhost:8000}"
REGION="${AWS_REGION:-us-east-1}"

echo "Populando dados de exemplo..."

# Serviços
aws dynamodb put-item \
  --table-name chatbot-servicos \
  --item '{"servicoId":{"S":"srv-001"},"nome":{"S":"Corte Feminino"},"duracao":{"N":"60"},"preco":{"N":"80.00"}}' \
  --endpoint-url "$ENDPOINT" --region "$REGION" && echo "✅ Serviço: Corte Feminino"

aws dynamodb put-item \
  --table-name chatbot-servicos \
  --item '{"servicoId":{"S":"srv-002"},"nome":{"S":"Corte Masculino"},"duracao":{"N":"30"},"preco":{"N":"40.00"}}' \
  --endpoint-url "$ENDPOINT" --region "$REGION" && echo "✅ Serviço: Corte Masculino"

aws dynamodb put-item \
  --table-name chatbot-servicos \
  --item '{"servicoId":{"S":"srv-003"},"nome":{"S":"Coloração"},"duracao":{"N":"120"},"preco":{"N":"180.00"}}' \
  --endpoint-url "$ENDPOINT" --region "$REGION" && echo "✅ Serviço: Coloração"

aws dynamodb put-item \
  --table-name chatbot-servicos \
  --item '{"servicoId":{"S":"srv-004"},"nome":{"S":"Escova"},"duracao":{"N":"60"},"preco":{"N":"60.00"}}' \
  --endpoint-url "$ENDPOINT" --region "$REGION" && echo "✅ Serviço: Escova"

aws dynamodb put-item \
  --table-name chatbot-servicos \
  --item '{"servicoId":{"S":"srv-005"},"nome":{"S":"Hidratação"},"duracao":{"N":"90"},"preco":{"N":"120.00"}}' \
  --endpoint-url "$ENDPOINT" --region "$REGION" && echo "✅ Serviço: Hidratação"

# Profissionais
# Horário: Segunda a Sexta (1-5), das 9h às 18h, intervalos de 60 min
HORARIO_SEG_SEX='[{"diaSemana":1,"horaInicio":"09:00","horaFim":"18:00","intervaloMinutos":60},{"diaSemana":2,"horaInicio":"09:00","horaFim":"18:00","intervaloMinutos":60},{"diaSemana":3,"horaInicio":"09:00","horaFim":"18:00","intervaloMinutos":60},{"diaSemana":4,"horaInicio":"09:00","horaFim":"18:00","intervaloMinutos":60},{"diaSemana":5,"horaInicio":"09:00","horaFim":"18:00","intervaloMinutos":60}]'

# Horário: Terça a Sábado (2-6), das 8h às 17h, intervalos de 60 min
HORARIO_TER_SAB='[{"diaSemana":2,"horaInicio":"08:00","horaFim":"17:00","intervaloMinutos":60},{"diaSemana":3,"horaInicio":"08:00","horaFim":"17:00","intervaloMinutos":60},{"diaSemana":4,"horaInicio":"08:00","horaFim":"17:00","intervaloMinutos":60},{"diaSemana":5,"horaInicio":"08:00","horaFim":"17:00","intervaloMinutos":60},{"diaSemana":6,"horaInicio":"08:00","horaFim":"14:00","intervaloMinutos":60}]'

aws dynamodb put-item \
  --table-name chatbot-profissionais \
  --item "{\"profissionalId\":{\"S\":\"prof-001\"},\"nome\":{\"S\":\"Ana Silva\"},\"especialidades\":{\"SS\":[\"Coloração\",\"Corte Feminino\"]},\"ativo\":{\"BOOL\":true},\"horarioAtendimento\":{\"S\":\"$HORARIO_SEG_SEX\"}}" \
  --endpoint-url "$ENDPOINT" --region "$REGION" && echo "✅ Profissional: Ana Silva"

aws dynamodb put-item \
  --table-name chatbot-profissionais \
  --item "{\"profissionalId\":{\"S\":\"prof-002\"},\"nome\":{\"S\":\"Carlos Santos\"},\"especialidades\":{\"SS\":[\"Corte Masculino\",\"Barba\"]},\"ativo\":{\"BOOL\":true},\"horarioAtendimento\":{\"S\":\"$HORARIO_TER_SAB\"}}" \
  --endpoint-url "$ENDPOINT" --region "$REGION" && echo "✅ Profissional: Carlos Santos"

aws dynamodb put-item \
  --table-name chatbot-profissionais \
  --item "{\"profissionalId\":{\"S\":\"prof-003\"},\"nome\":{\"S\":\"Mariana Costa\"},\"especialidades\":{\"SS\":[\"Hidratação\",\"Escova\",\"Corte Feminino\"]},\"ativo\":{\"BOOL\":true},\"horarioAtendimento\":{\"S\":\"$HORARIO_SEG_SEX\"}}" \
  --endpoint-url "$ENDPOINT" --region "$REGION" && echo "✅ Profissional: Mariana Costa"

echo ""
echo "✅ Dados de exemplo inseridos com sucesso!"
