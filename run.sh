#!/bin/bash
echo "=========================================="
echo "  Iniciando Locadora DTI (Linux/Mac)"
echo "=========================================="
echo ""

# Executa o Maven forçando UTF-8,, para evitar erros de acentuação.
mvn exec:java -Dfile.encoding=UTF-8