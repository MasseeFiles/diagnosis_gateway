# DIAGNOSIS APP
# _Microservice GATEWAY_

DIAGNOSIS est une application d'aide à la détection du diabète de type 2 comportant 5 microservices (Gateway, View, Patient, Risk et Note). Le microservice GATEWAY a pour rôle de relier tous les microservices entre eux et d'assurer la sécurisation de leurs accès. Il contient le fichier docker-compose pour le démarrage de l'appli avec DOCKER.


### Point d'entrée de l'appli (URI):
http://localhost:8084/view/patientList


### Port
Le microservice GATEWAY est exposé sur le port 8084.


### Spring Security 
La configuration de Spring Security contenue dans ce microservice permet de tester l'appli avec deux profils d'utilisateurs différents (DOCTOR et ASSISTANT) stockés dans une BDD. Ci-dessous les identifiants à entrer dans le formulaire d'authentification :

Utilisateur DOCTOR
- Login : doctor
- Password : 123

Utilisateur ASSISTANT
- Login : assistant
- Password : 123


### Architecture des microservices / Communication
Tous les microservices sont codés en JAVA en suivant une architecture MVC. La communication entre eux se fait via des requetes HTTP qui transitent d'abord par la gateway avant d'être redirigées vers le microservice ciblé (voir class "Routes").


### Docker
Chaque microservice comporte un fichier Dockerfile à la racine du projet pour la création de son image DOCKER.


### Docker-compose 
Le fichier docker-compose expose les différents microservices de l'appli dans des containers en utilisant les ports suivants:

- Diagnosis_Gateway : 8084
- Diagnosis_View : 8082
- Diagnosis_Note : 8083
- Diagnosis_Risk : 8085
- Diagnosis_Patient : 8081





