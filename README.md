# Medilabo Solutions - Projet 9 OpenClassrooms

## Description

Application microservices développée dans le cadre du projet 9 OpenClassrooms.

Le projet vise à mettre en place une application de gestion de dossiers patients pour Medilabo Solutions, avec une architecture microservices basée sur Spring Boot.

L’application permet actuellement :
- de consulter les patients ;
- d’ajouter un patient ;
- de modifier un patient ;
- de consulter les notes médicales d’un patient ;
- d’ajouter une note médicale à un patient ;
- d’évaluer le niveau de risque diabète d’un patient.

---

## Architecture du projet

Le projet est structuré en plusieurs microservices :

- `patient-service` : gestion des patients avec une base SQL MySQL
- `notes-service` : gestion des notes médicales avec une base NoSQL MongoDB
- `assessment-service` : évaluation du risque diabète
- `gateway-service` : point d’entrée unique via Spring Cloud Gateway
- `ui-service` : interface utilisateur avec Thymeleaf

---

## Technologies utilisées

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Data MongoDB
- Spring Cloud Gateway
- Thymeleaf
- MySQL
- MongoDB
- Maven
- Lombok
- Docker
- Docker Compose

---

## Structure du projet

```text
NeuviemeProjet/
├── patient-service/
├── notes-service/
├── assessment-service/
├── gateway-service/
├── ui-service/
├── docker-compose.yml
└── README.md
```

---

## Fonctionnalités réalisées - Sprint 1

Le Sprint 1 met en place l’architecture de base et la gestion des patients.

Fonctionnalités :
- création du microservice `patient-service`
- connexion du microservice patient à une base MySQL
- exposition d’une API REST pour les patients
- consultation de la liste des patients
- consultation d’un patient par son identifiant
- ajout d’un patient
- modification d’un patient
- création du microservice `gateway-service`
- routage des appels patients via la gateway
- création du microservice `ui-service`
- affichage de la liste des patients dans l’interface
- ajout et modification d’un patient depuis l’interface

---

## Fonctionnalités réalisées - Sprint 2

Le Sprint 2 ajoute la gestion des notes médicales.

Fonctionnalités :
- création du microservice `notes-service`
- connexion du microservice notes à MongoDB
- exposition d’une API REST pour les notes médicales
- consultation des notes d’un patient
- ajout d’une note médicale pour un patient
- conservation des retours à la ligne dans les notes médicales
- routage des appels notes via la gateway
- affichage des notes sur la fiche patient dans `ui-service`
- ajout d’une note depuis la fiche patient

---

## Fonctionnalités réalisées - Sprint 3

Le Sprint 3 ajoute l’évaluation du risque diabète.

Fonctionnalités :
- création du microservice `assessment-service`
- récupération des informations patient via la gateway
- récupération des notes médicales via la gateway
- analyse des termes déclencheurs présents dans les notes
- calcul du niveau de risque diabète selon l’âge, le genre et le nombre de termes déclencheurs
- exposition d’une API REST pour l’évaluation du risque
- routage des appels d’évaluation via la gateway
- affichage du niveau de risque sur la fiche patient dans `ui-service`

Niveaux de risque gérés :
- `None`
- `Borderline`
- `In Danger`
- `Early onset`

---

## Ports utilisés

- `ui-service` : `8080`
- `gateway-service` : `8081`
- `patient-service` : `8082`
- `notes-service` : `8083`
- `assessment-service` : `8084`
- MySQL : `3306`
- MongoDB : `27017`

---

## Bases de données

### MySQL - Patients

Base utilisée par `patient-service` :

```sql
medilabo_patient
```

Table principale :

```sql
patients
```

### MongoDB - Notes médicales

Base utilisée par `notes-service` :

```text
medilabo_notes
```

Collection principale :

```text
notes
```

Le microservice `assessment-service` ne possède pas de base de données dédiée.

---

## Endpoints principaux

### Patients via gateway

```http
GET  http://localhost:8081/api/patients
GET  http://localhost:8081/api/patients/{id}
POST http://localhost:8081/api/patients
PUT  http://localhost:8081/api/patients/{id}
```

### Notes via gateway

```http
GET  http://localhost:8081/api/notes/patient/{patientId}
POST http://localhost:8081/api/notes
```

### Évaluation du risque via gateway

```http
GET http://localhost:8081/api/assessments/patient/{patientId}
```

---

## Lancement en local sans Docker

### 1. Démarrer MySQL

Vérifier que MySQL est lancé et que la base suivante existe :

```sql
medilabo_patient
```

### 2. Démarrer MongoDB

Vérifier que MongoDB est lancé sur le port par défaut :

```text
mongodb://localhost:27017
```

La base `medilabo_notes` sera créée automatiquement lors de la première insertion de note.

### 3. Lancer les microservices

Lancer les services dans cet ordre :

1. `patient-service`
2. `notes-service`
3. `gateway-service`
4. `assessment-service`
5. `ui-service`

### 4. Accéder à l’application

Interface utilisateur :

```text
http://localhost:8080/patients
```

API gateway patients :

```text
http://localhost:8081/api/patients
```

API gateway notes :

```text
http://localhost:8081/api/notes/patient/{patientId}
```

API gateway évaluation :

```text
http://localhost:8081/api/assessments/patient/{patientId}
```

---

## Construction du projet

Dans chaque microservice, exécuter :

```bash
mvn clean package
```

Cela génère les fichiers `.jar` dans le dossier `target/` de chaque microservice.

---

## Docker

Chaque microservice possède un `Dockerfile`.

Un fichier `docker-compose.yml` est présent à la racine du projet afin de lancer les services avec Docker Compose.

Commande prévue pour le lancement :

```bash
docker compose up --build
```

À ce stade, le fichier `docker-compose.yml` contient :
- `patient-service`
- `notes-service`
- `assessment-service`
- `gateway-service`
- `ui-service`
- un conteneur MongoDB

MySQL est encore utilisé depuis la machine hôte via `host.docker.internal`.

---

## Données de test

Les patients de test utilisés sont :

- `TestNone`
- `TestBorderline`
- `TestInDanger`
- `TestEarlyOnset`

Les notes médicales associées sont stockées dans MongoDB et rattachées aux patients via leur `patientId`.

Résultats attendus pour l’évaluation du risque :

- `TestNone` : `None`
- `TestBorderline` : `Borderline`
- `TestInDanger` : `In Danger`
- `TestEarlyOnset` : `Early onset`

---

## Auteur

Projet réalisé dans le cadre de la formation OpenClassrooms - Développeur Java Back-End.