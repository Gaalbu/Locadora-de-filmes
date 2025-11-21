@echo off
echo ==========================================
echo   Iniciando Locadora DTI (Windows)
echo ==========================================
echo.

::Força o terminal a usar UTF-8 para aceitar acentos (erro comum)
chcp 65001 > nul

::Executa o Maven com encoding UTF-8 forçado
call mvn exec:java -Dfile.encoding=UTF-8