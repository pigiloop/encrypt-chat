# ==========================================================
# Stage 1: Build stage
# ==========================================================
FROM gradle:8.14.4-jdk21 AS builder

LABEL authors="pigil"

WORKDIR app

COPY frontend ./frontend/


COPY build.gradle gradle.properties settings.gradle ./

RUN gradle frontend:build

#CMD ["sleep", "infinity"]

# ==========================================================
# Stage 2: Run stage
# ==========================================================
FROM node:25-alpine3.22 AS production
WORKDIR app
COPY --from=builder /home/gradle/app/frontend/build/distributions/frontend_dist-1.0.3-SNAPSHOT.zip frontend.zip

RUN unzip frontend.zip
#RUN chmod +x ./app/scripts/start.sh

EXPOSE 3000/tcp

ENTRYPOINT ["sh", "./app/scripts/start.sh"]

