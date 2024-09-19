##################### DOCKERFILE diagnosis_gateway ####################

#################### STAGE 1 : Construction du projet ##########################

# Definition de l'image de base
FROM maven AS build

# Definition du fichier de travail dans le container
WORKDIR /diagnosis_gateway

# Copie du pom et du code source dans le fichier de travail
COPY pom.xml /diagnosis_gateway
COPY src /diagnosis_gateway/src

# Package de l'appli (sans execution des tests - DskipTests)
RUN mvn clean package -DskipTests


#################### STAGE 2 : Execution de l'appli ####################
FROM openjdk:21-jdk-slim

WORKDIR /diagnosis_gateway

# Copie du fichier packagé (jar) vers le fichier de travail
COPY --from=build /diagnosis_gateway/target/diagnosis-gateway-0.0.1-SNAPSHOT.jar /diagnosis_gateway/diagnosis-gateway.jar

# Exposition du port d'accès à l'appli
EXPOSE 8084

#RUN de l'appli (par defaut au demarrage)
ENTRYPOINT ["java", "-Dspring.config.location=classpath:/application.properties", "-jar", "diagnosis-gateway.jar"]





