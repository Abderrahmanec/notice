# notizen-speicher
Eine einfache Notizen-Verwaltungsanwendung, die mit Spring Boot entwickelt wurde. Dieses Projekt ermöglicht es Benutzern, persönliche Notizen zu erstellen, anzuzeigen und zu verwalten. Es umfasst Benutzerauthentifizierung, JWT-basierte Sicherheit und eine REST-API zur Interaktion mit Notizen und Benutzerdaten.
# NotizenSpeicher

**NotizenSpeicher** ist eine Webanwendung, mit der Benutzer Notizen, Weblinks und Bilder speichern und verwalten können. Die Anwendung ermöglicht es, Notizen mit Schlagwörtern zu versehen und diese Notizen nach verschiedenen Kriterien wie Schlagwörtern, Datum und Kategorie zu filtern. Außerdem können Notizen über einzigartige Freigabelinks zugänglich gemacht werden, was die einfache Weitergabe von Notizen ermöglicht.

## Features
- **Benutzerverwaltung:** Benutzer können Konten erstellen, sich anmelden und ihre Notizen verwalten.
- **Notizen verwalten:** Notizen, Links und Bilder speichern und mit Schlagwörtern und Kategorien versehen.
- **Filtern und Suchen:** Notizen nach Schlagwörtern, Kategorien und Zeitraum filtern.
- **Freigabe:** Notizen können über eindeutige Links freigegeben werden.

## Technologien
- Backend: Spring Boot (Java), Spring Data JPA, MySQL
- Frontend: React.js
- Datenbank: MySQL
- Authentifizierung: JWT (JSON Web Token)
- Tools: Lombok, Maven

## Installation und Setup

**Backend (Spring Boot):**
1. Stelle sicher, dass du MySQL installiert hast und konfiguriere die `application.properties`-Datei mit deinen MySQL-Datenbankdetails.
2. Starte das Backend-Projekt:
   ```bash
   mvn spring-boot:run
   ```

**Frontend (React):**
1. Navigiere zum Frontend-Ordner:
   ```bash
   cd frontend
   ```
2. Installiere die Abhängigkeiten:
   ```bash
   npm install
   ```
3. Starte das React-Frontend:
   ```bash
   npm start
   ```

## Beispiel-API-Endpunkte:
- `POST /api/users/register` – Benutzer registrieren
- `POST /api/users/login` – Benutzer anmelden (JWT erhalten)
- `GET /api/notes` – Alle Notizen des angemeldeten Benutzers abrufen
- `POST /api/notes` – Neue Notiz erstellen
- `DELETE /api/notes/{id}` – Notiz löschen
