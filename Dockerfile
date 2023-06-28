# Prepara a imagem para o build do projeto
FROM azul/zulu-openjdk:17-latest as builder

# Instala o Maven para Realizar o Build
RUN apt update -y && apt install -y maven

# Define uma variavel de ambiente para o diretorio do projeto
ENV APT_DIR=/opt/assertiva

# Define o diretorio de trabalho
WORKDIR ${APT_DIR}

# Copia o projeto para o diretorio de trabalho
COPY . ${APT_DIR}

# Builda o projeto
RUN mvn package -Dmaven.repo.local=$BUILD_CACHE_PATH -DskipTests

FROM azul/zulu-openjdk-alpine:17-latest

COPY --from=builder /opt/assertiva/target/*.jar /app/app/assertiva.jar

ENV APP_DIR=/opt/assertiva

EXPOSE 8080

ENTRYPOINT [ "java" , "-jar" , "/app/app/assertiva.jar" ]