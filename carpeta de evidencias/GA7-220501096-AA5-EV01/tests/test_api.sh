#!/usr/bin/env bash
# Pruebas automatizadas simples para la API de registro/login
# Requiere: curl, jq (opcional para formateo)

API="http://localhost:8082/api"

echo "Iniciando pruebas de API en $API"

# Generar email único para evitar colisiones
TS=$(date +%s)
EMAIL="test_${TS}@example.com"

echo "Usando email: $EMAIL"

echo "\n1) Registro"
REG_RESP=$(curl -s -w "HTTPSTATUS:%{http_code}" -X POST "$API/registro" -H "Content-Type: application/json" -d '{"username":"tester","email":"'$EMAIL'","password":"secret123"}')
REG_BODY=$(echo "$REG_RESP" | sed -e 's/HTTPSTATUS:.*//g')
REG_STATUS=$(echo "$REG_RESP" | tr -d '\n' | sed -e 's/.*HTTPSTATUS://')
echo "Status: $REG_STATUS"
echo "Body: $REG_BODY"

if [ "$REG_STATUS" -ne 201 ]; then
  echo "Registro fallido (esperado 201). Abortando tests."; exit 1
fi

echo "\n2) Login correcto"
LOGIN_RESP=$(curl -s -w "HTTPSTATUS:%{http_code}" -X POST "$API/login" -H "Content-Type: application/json" -d '{"email":"'$EMAIL'","password":"secret123"}')
LOGIN_BODY=$(echo "$LOGIN_RESP" | sed -e 's/HTTPSTATUS:.*//g')
LOGIN_STATUS=$(echo "$LOGIN_RESP" | tr -d '\n' | sed -e 's/.*HTTPSTATUS://')
echo "Status: $LOGIN_STATUS"
echo "Body: $LOGIN_BODY"

if [ "$LOGIN_STATUS" -ne 200 ]; then
  echo "Login correcto falló (esperado 200)."; exit 1
fi

echo "\n3) Login incorrecto (password equivocado)"
BADLOGIN_RESP=$(curl -s -w "HTTPSTATUS:%{http_code}" -X POST "$API/login" -H "Content-Type: application/json" -d '{"email":"'$EMAIL'","password":"wrongpass"}')
BADLOGIN_STATUS=$(echo "$BADLOGIN_RESP" | tr -d '\n' | sed -e 's/.*HTTPSTATUS://')
echo "Status esperado 401, obtenido: $BADLOGIN_STATUS"

if [ "$BADLOGIN_STATUS" -ne 401 ]; then
  echo "El test de login incorrecto falló."; exit 1
fi

echo "\nTodos los tests pasaron."
exit 0
