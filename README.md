# Mini-CMS API

## Beschreibung
Eine RESTful API zur Verwaltung von Blog-Beiträgen und Kategorien. Diese API unterstützt CRUD-Operationen für Blogbeiträge und Kategorien.

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
  Aktualisiert teilweise einen Blogbeitrag.

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

- **CategoryDTO**
    - `categoryName` (string, 3–255 Zeichen)

- **BlogPostRequestDTO**
    - `title` (string, 5–100 Zeichen)
    - `body` (string, 10+ Zeichen)
    - `author` (string, 3+ Zeichen)
    - `categoriesDTO` (Array von `CategoryDTO`)

- **BlogPostResponseDTO**
    - `id` (integer)
    - `title` (string)
    - `body` (string)
    - `author` (string)
    - `publicationDate` (string, Datum)
    - `blogPostStatus` (enum: DRAFT, PUBLISHED, ARCHIVED)
    - `categoriesDTO` (Array von `CategoryDTO`)