# Imagem base
FROM openjdk:11-jdk-slim

# Definindo as variáveis de ambiente
ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}

# Pasta de trabalho no container
WORKDIR /app

# Copia o arquivo .jar para o container
COPY ../build/libs/auth-service.jar /app/app.jar

# Expõe a porta 8080
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "/app/app.jar"]