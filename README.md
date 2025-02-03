# Mini-CMS API

## Beschreibung
Eine RESTful API zur Verwaltung von Blog-Beiträgen und Kategorien. Diese API unterstützt CRUD-Operationen für Blogbeiträge und Kategorien.

## API Documentation
Die vollständige OpenAPI-Dokumentation ist [hier](docs/openapi.json) verfügbar.

## Endpunkte

### Kategorien

- **POST /category**  
  Erstellt eine neue Kategorie.

- **DELETE /category/{id}**  
  Löscht eine Kategorie basierend auf der ID.

### Blogbeiträge

- **GET /blogpost**  
  Holt alle Blogbeiträge.

- **POST /blogpost**  
  Erstellt einen neuen Blogbeitrag.

- **GET /blogpost/{id}**  
  Holt einen Blogbeitrag anhand der ID.

- **DELETE /blogpost/{id}**  
  Löscht einen Blogbeitrag anhand der ID.

- **PATCH /blogpost/{id}**  
  Aktualisiert einen Blogbeitrag mit neuen Titel oder neuer Inhalt.

- **PATCH /blogpost/{id}/status**  
  Aktualisiert den Status eines Blogbeitrags.

- **GET /blogpost/category/{categoryName}**  
  Holt Blogbeiträge einer bestimmten Kategorie.

## Fehlercodes

- **400 Bad Request**  
  Ungültige Anfrage.

- **404 Not Found**  
  Ressource nicht gefunden.

- **409 Conflict**  
  Konflikt mit der bestehenden Ressource.

- **500 Internal Server Error**  
  Serverfehler.

## Datenmodelle

### BlogPost

Repräsentiert einen Blogbeitrag.

- **id** (Long) – Eindeutige Identifikation (automatisch generiert).
- **title** (String) – Titel des Blogbeitrags.
- **body** (String) – Inhalt des Blogbeitrags.
- **author** (String) – Autor des Blogbeitrags.
- **publicationDate** (LocalDate) – Veröffentlichungsdatum.
- **blogPostStatus** (Enum: DRAFT, PUBLISHED, ARCHIVED) – Status des Blogbeitrags.
- **categories** (Liste von CategoryDTO) – Zugehörige Kategorien.

### Category

Repräsentiert eine Kategorie.

- **id** (Long) – Eindeutige Identifikation (automatisch generiert).
- **categoryName** (String) – Name der Kategorie.
- **blogPosts** (Liste von BlogPost) – Zugehörige Blogbeiträge.

## DTOs

- **CategoryDTO**
  - `categoryName` (String)

- **BlogPostRequestDTO**
  - `title` (String)
  - `body` (String)
  - `author` (String)
  - `categoriesDTO` (Array von `CategoryDTO`)

- **BlogPostResponseDTO**
  - `id` (Long)
  - `title` (String)
  - `body` (String)
  - `author` (String)
  - `publicationDate` (String)
  - `blogPostStatus` (Enum: DRAFT, PUBLISHED, ARCHIVED)
  - `categoriesDTO` (Array von `CategoryDTO`)