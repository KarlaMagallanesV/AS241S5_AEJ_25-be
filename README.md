<br clear="both">

<h1 align="center">YouTube & Spotify MP3 Downloader API</h1>

###

<div><img style="100%" src="https://capsule-render.vercel.app/api?type=waving&height=70&section=header&reversal=false&text=%C2%BFQu%C3%A9%20hace%20esta%20Api?&fontSize=50&fontColor=FFFFFF&fontAlign=50&fontAlignY=50&stroke=-&animation=blink&descSize=20&descAlign=50&descAlignY=50&textBg=false&color=#FFC5D3"  /></div>

###

<img align="right" height="250" src="https://cdn-icons-png.flaticon.com/512/2913/2913133.png"  />

###

<h4 align="left">YouTube & Spotify MP3 Downloader es una API reactiva construida con Spring WebFlux que permite convertir videos de YouTube a MP3 y descargar canciones de Spotify. Utiliza APIs externas de RapidAPI para procesar las conversiones y almacena los resultados en MongoDB Atlas.<br><br>La API soporta ingresar el enlace completo o solo el ID del video/canción. Guarda metadatos como título, artista, miniatura/portada del álbum, enlace de descarga y permite gestionar los registros con operaciones CRUD completas incluyendo eliminado lógico, restauración y marcado de favoritos.</h4>

###

<br clear="both">

###

<img align="left" height="180" src="https://i.imgflip.com/65efzo.gif"  />

###

<h4 align="right"><strong>Tecnologías utilizadas:</strong><br><br>Java: JDK 17<br>IDE: IntelliJ IDEA | Visual Studio Code<br>Base de datos: MongoDB Atlas<br>Gestor de dependencias: Apache Maven<br>Framework: Spring Boot + WebFlux (Reactivo)</h4>

###

<div align="right">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="40" alt="java logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" height="40" alt="spring logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mongodb/mongodb-original.svg" height="40" alt="mongodb logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/vscode/vscode-original.svg" height="40" alt="vscode logo"  />
</div>

###

<br clear="both">

###

<h3 align="left">📦 Dependencias del proyecto</h3>

<p align="left">
• <strong>spring-boot-starter-webflux</strong> - API REST reactiva con WebFlux<br>
• <strong>spring-boot-starter-data-mongodb-reactive</strong> - Integración reactiva con MongoDB<br>
• <strong>jackson-databind</strong> - Serialización/deserialización JSON<br>
• <strong>lombok</strong> - Reducir código boilerplate<br>
• <strong>spring-boot-starter-test</strong> - Para testing
</p>

###

## Spring Boot WebFlux

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

## Spring Boot Data MongoDB Reactive

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>
</dependency>
```

## Jackson Databind

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>
```

## Lombok

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

## Spring Boot Test

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

###

<h3 align="left">🚀 Endpoints de la API</h3>

### YouTube

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/youtube` | Listar todos los videos |
| GET | `/api/youtube/{id}` | Obtener video por ID |
| POST | `/api/youtube/convert?videoId=` | Convertir video (URL o ID) |
| PUT | `/api/youtube/{id}?favorite=true` | Marcar como favorito |
| PATCH | `/api/youtube/{id}/delete` | Eliminado lógico |
| PATCH | `/api/youtube/{id}/restore` | Restaurar registro |

### Spotify

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/spotify` | Listar todas las canciones |
| GET | `/api/spotify/{id}` | Obtener canción por ID |
| POST | `/api/spotify/download?songId=` | Descargar canción (URL o ID) |
| PUT | `/api/spotify/{id}?favorite=true` | Marcar como favorito |
| PATCH | `/api/spotify/{id}/delete` | Eliminado lógico |
| PATCH | `/api/spotify/{id}/restore` | Restaurar registro |

### Ejemplos de uso

```
POST http://localhost:8085/api/youtube/convert?videoId=dQw4w9WgXcQ
POST http://localhost:8085/api/youtube/convert?videoId=https://www.youtube.com/watch?v=dQw4w9WgXcQ
POST http://localhost:8085/api/youtube/convert?videoId=https://youtu.be/dQw4w9WgXcQ

POST http://localhost:8085/api/spotify/download?songId=48i055G1OT5KxGGftwFxWy
POST http://localhost:8085/api/spotify/download?songId=https://open.spotify.com/track/48i055G1OT5KxGGftwFxWy
```

###

<h3 align="left">⚙️ Configuración</h3>

### application.yml

```yaml
spring:
  application:
    name: mongodb
  mongodb:
    uri: ${DATABASE_URL:mongodb+srv://[USER]:[PASSWORD]@cluster0.mongodb.net/YouTube_MP3}

server:
  port: ${SERVER_PORT:8085}

rapidapi:
  key: ${RAPIDAPI_KEY}
  host: ${RAPIDAPI_HOST}
  spotify:
    key: ${RAPIDAPI_SPOTIFY_KEY}
    host: ${RAPIDAPI_SPOTIFY_HOST}
```

###

<h3 align="left">🏗️ Arquitectura del Proyecto</h3>

```
mongodb/
├── src/main/java/youtubeMp3/mongodb/
│   ├── model/
│   │   ├── VideoModel.java
│   │   └── SpotifyDownloadModel.java
│   ├── repository/
│   │   ├── VideoRepository.java
│   │   └── SpotifyRepository.java
│   ├── service/
│   │   ├── VideoService.java
│   │   └── SpotifyService.java
│   ├── rest/
│   │   ├── VideoRest.java
│   │   └── SpotifyRest.java
│   ├── util/
│   │   └── UrlParserUtil.java
│   └── MongodbApplication.java
└── src/main/resources/
    └── application.yml
```

###

<div align="center">
  <img src="https://capsule-render.vercel.app/api?type=waving&height=70&section=footer&color=gradient" />
</div>
