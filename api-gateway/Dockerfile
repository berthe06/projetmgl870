# Utilise une image OpenJDK officielle
FROM openjdk:21-jdk-slim

# Copie ton fichier jar dans le conteneur
COPY target/api-gateway-0.0.1-SNAPSHOT.jar /app/api-gateway-service.jar

# Définit le répertoire de travail
WORKDIR /app

# Commande d'exécution
ENTRYPOINT ["java", "-jar", "product-service.jar"]
