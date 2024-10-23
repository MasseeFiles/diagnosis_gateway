# DIAGNOSIS APP
# _Microservice GATEWAY_

DIAGNOSIS est une application d'aide à la détection du diabète de type 2 comportant 5 microservices (Gateway, View, Patient, Risk et Note). Le microservice GATEWAY constitue le point d'entrée de l'appli et assurer la sécurisation de son accès. Il contient le fichier docker-compose pour le démarrage de l'appli avec DOCKER.


### Point d'entrée de l'appli (URI):
http://localhost:8084/view/patientList


### Port
Le microservice GATEWAY est exposé sur le port 8084.


### Spring Security 
La configuration de Spring Security contenue dans ce microservice permet de tester l'appli avec deux profils d'utilisateurs différents (DOCTOR et ASSISTANT) stockés dans une BDD MySQL. Ci-dessous les identifiants à entrer dans le formulaire d'authentification :

Utilisateur DOCTOR
- Login : doctor
- Password : 123

Utilisateur ASSISTANT
- Login : assistant
- Password : 123


### Docker
Chaque microservice comporte un fichier Dockerfile à la racine du projet pour la création de son image DOCKER.


### Docker-compose 
Le fichier docker-compose expose les différents microservices de l'appli dans des containers en utilisant les ports suivants:

- Diagnosis_Gateway : 8084
- Diagnosis_View : 8082
- Diagnosis_Note : 8083
- Diagnosis_Risk : 8085
- Diagnosis_Patient : 8081





