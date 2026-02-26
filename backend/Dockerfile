# ==========================================================
# Stage 1: Build stage
# ==========================================================
ARG GRADLE_VERSION_ARG=8.14.4-jdk21-alpine
FROM gradle:${GRADLE_VERSION_ARG} AS builder

WORKDIR app

COPY backend ./backend/

COPY ../build.gradle ../gradle.properties ../settings.gradle  ./

RUN gradle backend:clean backend:build -x test

#CMD ["sleep", "infinity"]

# ==========================================================
# Stage 2: Run stage
# ==========================================================
#CMD ["sleep", "infinity"]
#FROM openjdk:21-ea-jdk
ARG JAVA_VERSION_ARG=21
FROM eclipse-temurin:21-jdk-alpine
ENV JETTY_GPG_KEYS \
	# Jan Bartel      <janb@mortbay.com>
	AED5EE6C45D0FE8D5D1B164F27DED4BF6216DB8F \
	# Jesse McConnell <jesse.mcconnell@gmail.com>
	2A684B57436A81FA8706B53C61C3351A438A3B7D \
	# Joakim Erdfelt  <joakim.erdfelt@gmail.com>
	5989BAF76217B843D66BE55B2D0E1FB8FE4B68B4 \
	# Joakim Erdfelt  <joakime@apache.org>
	B59B67FD7904984367F931800818D9D68FB67BAC \
	# Joakim Erdfelt  <joakim@erdfelt.com>
	BFBB21C246D7776836287A48A04E0C74ABB35FEA \
	# Simone Bordet   <simone.bordet@gmail.com>
	8B096546B1A8F02656B15D3B1677D141BCF3584D \
	# Olivier Lamy    <olamy@apache.org>
	F254B35617DC255D9344BCFA873A8E86B4372146 \
	# Ludovic Orban   <lorban@bitronix.be>
	E22488CC94F63E3FC928536C4241C08270D999C3

WORKDIR /app

COPY --from=builder /home/gradle/app/backend/build/libs/my-app-1.0-all.jar backend.jar

RUN apk add --no-cache gnupg curl ; \
    chmod +x backend.jar

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "/app/backend.jar"]