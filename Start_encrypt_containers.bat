:: Создание сети для контейнеров
docker network create net_backend_local
docker network create net_backend_remote
docker network create net_frontend_local
docker network create net_frontend_remote

::Установка базы данных и запуск контейнера
docker run -d --network=net_backend_local -p 5432:5432 -v .\resources\db_local:/docker-entrypoint-initdb.d  -e POSTGRES_INITDB_ARGS -e POSTGRES_PASSWORD=admin -e POSTGRES_USER=admin -e POSTGRES_DB=db-encrypt-chat --name postgres_local --hostname postgres postgres:18.1-alpine3.22
docker run -d --network=net_backend_remote -p 5433:5432 -v .\resources\db_remote:/docker-entrypoint-initdb.d  -e POSTGRES_INITDB_ARGS -e POSTGRES_PASSWORD=admin -e POSTGRES_USER=admin -e POSTGRES_DB=db-encrypt-chat --name postgres_remote --hostname postgres postgres:18.1-alpine3.22

::Создание образа и запуск backend
docker build -t encrypt-chat-backend:1.0.0  -f backend\Dockerfile .
docker run -d --network=net_backend_local -p 8088:8088 --name backend_local encrypt-chat-backend:1.0.0
docker run -d --network=net_backend_remote -p 8089:8088 --name backend_remote encrypt-chat-backend:1.0.0

::Создание образа и запуск frontend
docker build -t encrypt-chat-frontend:1.0.0  -f frontend\Dockerfile .
docker run -d --network=net_frontend_local -p 8084:3000 --name frontend_local encrypt-chat-frontend:1.0.0
docker run -d --network=net_frontend_remote -p 8085:3000 --name frontend_remote encrypt-chat-frontend:1.0.0



docker network connect net_frontend_local backend_local
docker network connect net_frontend_local backend_remote

docker network connect net_frontend_remote backend_local
docker network connect net_frontend_remote backend_remote
