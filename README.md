# TalkTrack

## 📌 Projektbeschreibung

TalkTrack ist eine Web-App, die als Capstone-Projekt entwickelt wurde. Sie ermöglicht die automatische Erstellung von Zusammenfassungen aus Meetings, indem sie Audiodateien transkribiert und die Transkripte zusammenfasst. Dies wird durch die Nutzung der **AssemblyAi** API für die Transkription und der **ChatGPT** API für die Zusammenfassung realisiert.

### Ziel des Projekts:
Die Anwendung richtet sich an Nutzer, die regelmäßig Meetings führen und schnell auf prägnante Zusammenfassungen zugreifen möchten. Sie können Audiodateien hochladen, die dann automatisch in Text umgewandelt und zusammengefasst werden.

## 🛠️ Verwendete Technologien

- **Java (Spring Boot)** – Backend-Entwicklung
- **React mit TypeScript** – Frontend-Entwicklung
- **MongoDB** – Datenbank zur Speicherung von Transkripten und Zusammenfassungen
- **TailwindCSS** – Styling der Anwendung
- **AssemblyAi** – API zur Transkription der Audiodateien
- **ChatGPT** – KI-Modell zur Erstellung der Zusammenfassungen

## 🚀 Funktionen

- ✅ **Upload und Transkription**: Lade eine Audiodatei hoch, die dann von **AssemblyAi** transkribiert wird.
- ✅ **Zusammenfassung**: Das Transkript wird anschließend von **ChatGPT** analysiert und automatisch in eine prägnante Zusammenfassung umgewandelt.
- ✅ **Dashboard**: Ein einfaches Dashboard bietet einen Überblick über alle hochgeladenen Audiodateien und deren zugehörige Zusammenfassungen.
  

## 🖼️ Screenshots
LandingPage:
<img width="1432" alt="image" src="https://github.com/user-attachments/assets/1690bd42-a44b-476d-9955-268ec03edc46" />

Dashboard:
<img width="1432" alt="image" src="https://github.com/user-attachments/assets/4c517b42-d2dd-475e-9e4c-6600d33b6a51" />

UploadPage:
<img width="1432" alt="image" src="https://github.com/user-attachments/assets/ae4e5c67-adf3-4d22-9dd9-5b2248addcf0" />

SummaryPage:
<img width="1432" alt="image" src="https://github.com/user-attachments/assets/bb42deb3-80dc-4680-9e15-14b9d2b1dfa3" />

## 📂 Projektstruktur
```bash
neuefische-capstone-project-talktrack/
│── github/ # Backend-Code (Spring Boot) 
│── idea/ # Backend-Code (Spring Boot) 
│── backend/ # Backend-Code (Spring Boot) 
│   ├── src/main/java/com/beispiel/projekt/ # Hauptcode für das Backend 
│   │   ├── controller/ # Enthält den REST-API-Controller 
│   │   ├── model/ # Datenbank-Modelle (Entities) 
│   │   ├── repo/ # Repo für die MongoDB Datenbank 
│   │   ├── service/ # Business-Logik 
│   │   ├── Application.java # Hauptklasse zum Starten der App 
│   ├── src/main/resources/ 
│   │   ├── application.properties # Konfigurationsdatei 
│   ├── pom.xml # Maven-Abhängigkeiten für Spring Boot 
│── frontend/ # Frontend-Code (React)
│   ├── public/ # Icon-Bilder für das Frontend
│   ├── src/ # Quellcode des Frontends 
│   │   ├── component/ # Wiederverwendbare UI-Komponenten 
│   │   ├── page/ # Seiten der Anwendung
│   │   ├── type/ # Typen der Anwendung
│   │   ├── utlis/ # DataService für Backend-Anbindung  
│   │   ├── App.js # Haupt-React-Komponente 
│   │   ├── index.js # Einstiegspunkt des Frontends 
│   │   ├── styles.css # Globale CSS-Dateien 
│   ├── package.json # Abhängigkeiten für React
│   ├── postcss.config.cjs # Konfigurationsdatei für PostCSS
│   ├── tailwind.config.ts # Konfigurationsdatei für TailwindCSS
│   ├── vite.config.js
│── .gitignore # Dateien, die nicht ins Git-Repo sollen
```


## 🔒 Sicherheit & Einschränkungen

⚠️ Dieses Repository enthält keine **sensiblen Konfigurationsdateien** oder **Security-Variablen**, daher ist es nicht direkt einsatzbereit.  
Falls eine Bereitstellung geplant ist, müssen diese Variablen korrekt gesetzt werden.
