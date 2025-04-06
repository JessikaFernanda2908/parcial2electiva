#!/bin/bash

BASE_URL="http://localhost:8080/api/estudiantes"

echo "📌 Probando endpoints del microservicio CRUD..."

# 1. Crear un estudiante
echo -e "\n📤 Creando estudiante..."
curl -s -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{
        "nombre": "Juan Pérez",
        "correo": "juan.perez@universidad.edu",
        "edad": 22
      }' | jq

# 2. Obtener todos los estudiantes
echo -e "\n📥 Listando todos los estudiantes..."
curl -s "$BASE_URL" | jq

# 3. Obtener estudiante por ID (suponiendo ID=1)
echo -e "\n🔍 Consultando estudiante con ID=1..."
curl -s "$BASE_URL/1" | jq

# 4. Actualizar estudiante con ID=1
echo -e "\n✏️ Actualizando estudiante con ID=1..."
curl -s -X PUT "$BASE_URL/1" \
  -H "Content-Type: application/json" \
  -d '{
        "nombre": "Juan Carlos Pérez",
        "correo": "juan.c.perez@universidad.edu",
        "edad": 23
      }' | jq

# 5. Eliminar estudiante con ID=1
echo -e "\n🗑️ Eliminando estudiante con ID=1..."
curl -s -X DELETE "$BASE_URL/1"

# 6. Verificar eliminación
echo -e "\n🔍 Verificando eliminación del estudiante con ID=1..."
curl -s "$BASE_URL/1" | jq

echo -e "\n✅ Pruebas finalizadas."
