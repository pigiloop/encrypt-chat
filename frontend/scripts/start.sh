#!/bin/bash
#######################################################
# User Management Frontend - Startup Script
#
# Этот скрипт запускает frontend приложение локально
# через http-server на порту 3000.
#######################################################
set -e  # Остановить при первой ошибке
# Цвета для вывода
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color
# Функция для вывода логов
log_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}
log_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}
log_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}
log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}
# Баннер
echo ""
echo "╔════════════════════════════════════════════╗"
echo "║  User Management Frontend                  ║"
echo "║  Version: 1.0.0                            ║"
echo "╚════════════════════════════════════════════╝"
echo ""
# Определяем директорию скрипта
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
APP_DIR="$(cd "$SCRIPT_DIR/.." && pwd )"
log_info "Рабочая директория: $APP_DIR/app/app"
cd "${APP_DIR}/app/app"
# Проверка наличия Node.js
if ! command -v node &> /dev/null; then
    log_error "Node.js не установлен!"
    log_info "Установите Node.js с https://nodejs.org/"
    exit 1
fi
# Проверка наличия npm
if ! command -v npm &> /dev/null; then
    log_error "npm не установлен!"
    log_info "Установите npm (обычно идет с Node.js)"
    exit 1
fi
# Вывод версий
NODE_VERSION=$(node --version)
NPM_VERSION=$(npm --version)
log_info "Node.js версия: $NODE_VERSION"
log_info "npm версия: $NPM_VERSION"
# Проверка наличия package.json
if [ ! -f "dist/package.json" ]; then
    log_error "Файл package.json не найден!"
    log_info "Убедитесь, что вы находитесь в корневой директории проекта"
    exit 1
fi
# Проверка наличия собранных файлов
if [ ! -d "dist/" ] || [ ! -f "dist/index.html" ]; then
    log_error "Собранные файлы не найдены!"
    log_info "Директория dist должна содержать готовые файлы"
    log_info ""
    log_info "Текущее содержимое директории:"
    ls -la
    log_info ""
    log_error "❌ Невозможно запустить сервер без собранных файлов"
    log_info "Убедитесь, что архив был собран правильно через Gradle"
    exit 1
fi
log_success "Собранные файлы найдены в dist/"

# Определение порта (можно передать как аргумент)
PORT="${1:-3000}"
log_info "Запуск сервера на порту $PORT..."
echo ""
log_success "✨ Сервер запущен!"
log_info "🌐 Frontend доступен по адресу: ${GREEN}http://localhost:$PORT${NC}"
log_info "📡 Backend API должен быть доступен на: ${GREEN}http://localhost:8080${NC}"
echo ""
log_info "Для остановки сервера нажмите ${YELLOW}Ctrl+C${NC}"
echo ""
# Запуск сервера
npm install --global http-server
npx http-server dist -p "$PORT" -c-1 --cors
# Эта часть выполнится после остановки сервера (Ctrl+C)
echo ""
log_info "Сервер остановлен"
log_success "Спасибо за использование User Management Frontend! 👋"