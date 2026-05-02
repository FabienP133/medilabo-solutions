# Medilabo Solutions - Projet 9 OpenClassrooms

## Description
Application microservices développée dans le cadre du projet 9 OpenClassrooms.

Le projet vise à mettre en place une application de gestion de dossiers patients pour Medilabo Solutions, avec une architecture microservices basée sur Spring Boot.

## Architecture du Sprint 1
Le Sprint 1 contient 3 microservices :

- `patient-service` : gestion des patients
- `gateway-service` : point d’entrée unique via Spring Cloud Gateway
- `ui-service` : interface utilisateur avec Thymeleaf

## Technologies utilisées
- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Cloud Gateway
- Thymeleaf
- MySQL
- Maven
- Docker
- Docker Compose

## Structure du projet
```text
NeuviemeProjet/
├── patient-service/
├── gateway-service/
├── ui-service/
├── docker-compose.yml
└── README.md