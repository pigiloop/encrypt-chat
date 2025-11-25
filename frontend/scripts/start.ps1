#!/usr/bin/env pwsh

#######################################################
# User Management Frontend - Startup Script
#
# Этот скрипт запускает frontend приложение локально
# через http-server на порту 3000.
#######################################################

$ErrorActionPreference = "Stop"  # Остановить при первой ошибке

# Цвета для вывода
$RED = 'Red'
$GREEN = 'Green'
$YELLOW = 'Yellow'
$BLUE = 'Blue'
$NC = 'White' # No Color

# Функция для вывода логов
function log_info {
    param($message)
    Write-Host "[INFO] $message" -ForegroundColor $BLUE
}

function log_success {
    param($message)
    Write-Host "[SUCCESS] $message" -ForegroundColor $GREEN
}

function log_warning {
    param($message)
    Write-Host "[WARNING] $message" -ForegroundColor $YELLOW
}

function log_error {
    param($message)
    Write-Host "[ERROR] $message" -ForegroundColor $RED
}

# Баннер
Write-Host ""
Write-Host "╔════════════════════════════════════════════╗"
Write-Host "║  User Management Frontend                  ║"
Write-Host "║  Version: 1.0.0                            ║"
Write-Host "╚════════════════════════════════════════════╝"
Write-Host ""

# Определяем директорию скрипта
$SCRIPT_DIR = Split-Path -Parent $MyInvocation.MyCommand.Path
$APP_DIR = Split-Path -Parent $SCRIPT_DIR

log_info "Рабочая директория: $APP_DIR"
Set-Location "$APP_DIR"

# Проверка наличия Node.js
try {
    $nodeVersion = Get-Command node -ErrorAction Stop
} catch {
    log_error "Node.js не установлен!"
    log_info "Установите Node.js с https://nodejs.org/"
    exit 1
}

# Проверка наличия npm
try {
    $npmVersion = Get-Command npm -ErrorAction Stop
} catch {
    log_error "npm не установлен!"
    log_info "Установите npm (обычно идет с Node.js)"
    exit 1
}

# Вывод версий
$NODE_VERSION = node --version
$NPM_VERSION = npm --version
log_info "Node.js версия: $NODE_VERSION"
log_info "npm версия: $NPM_VERSION"

# Проверка наличия package.json
if (-not (Test-Path "dist/package.json")) {
    log_error "Файл package.json не найден!"
    log_info "Убедитесь, что вы находитесь в корневой директории проекта"
    exit 1
}
    log_info "package.json найден"

Write-Host "Проверяем из директории $PWD"


# Проверка наличия собранных файлов
if (-not (Test-Path "dist") -or -not (Test-Path "dist/index.html")) {
    log_error "Собранные файлы не найдены!"
    log_info "Директория dist должна содержать готовые файлы"
    log_info ""
    log_info "Текущее содержимое директории:"
    Get-ChildItem
    log_info ""
    log_error "❌ Невозможно запустить сервер без собранных файлов"
    log_info "Убедитесь, что архив был собран правильно через Gradle"
    exit 1
}

log_success "Собранные файлы найдены в dist/"

# Проверяем наличие node_modules
if (-not (Test-Path "node_modules")) {
    log_info "Директория node_modules не найдена"
    log_info "Устанавливаем зависимости..."

    # Проверяем наличие package-lock.json
    if (Test-Path "package-lock.json") {
        npm ci
    } else {
        npm install
    }

    if ($LASTEXITCODE -ne 0) {
        log_error "Ошибка при установке зависимостей!"
        exit 1
    }

    log_success "Зависимости установлены"
} else {
    log_info "Директория node_modules найдена, пропускаем установку"
}

# Определение порта (можно передать как аргумент)
if ($args.Count -gt 0) {
    $PORT = $args[0]
} else {
    $PORT = 3000
}

log_info "Запуск сервера на порту $PORT..."
Write-Host ""
log_success "✨ Сервер запущен!"
log_info "🌐 Frontend доступен по адресу: http://localhost:$PORT"
log_info "📡 Backend API должен быть доступен на: http://localhost:8080"
Write-Host ""
log_info "Для остановки сервера нажмите Ctrl+C"
Write-Host ""

try {
    # Запуск сервера
    npx http-server dist -p "$PORT" -c-1 --cors
} finally {
    # Эта часть выполнится после остановки сервера (Ctrl+C)
    Write-Host ""
    log_info "Сервер остановлен"
    log_success "Спасибо за использование User Management Frontend! 👋"
}
