# MerkAsia - Examen Abril 2026

Aplicación de gestión de pedidos para una tienda de informática. El sistema está compuesto por tres módulos independientes que se comunican mediante una API REST sobre JSON: un cliente de importación de datos, un backend de servlets Java y una app Android de consulta.

## Estructura del repositorio

```cmd
.
├── data/                       # CSV de datos de prueba (clientes, pedidos, productos)
├── deployment/                  # Scripts de despliegue (docker-compose, bbdd.sql)
├── MerkAsia-ImportDataClient/   # Cliente Java (Gradle) - lee el CSV y lo envía al backend
├── MerkAsia-Servlets/           # Backend Java EE (Maven) - API REST sobre Tomcat 9
├── MerkAsia-Android/            # App Android - consulta clientes y productos
├── Dockerfile                   # Imagen del backend (Tomcat 9 + JDK 21)
├── examen.txt                   # Enunciado del examen
└── README.md
```

## Arquitectura

```cmd
data.csv → [ImportDataClient] --OkHttp/JSON--> [Servlets/Tomcat] <--JSON-- [Android App]
                                                       |
                                                   MySQL (merkasia)
```

### Modelo de datos (árbol)

```cmd
Cliente
 └── Pedido[]
      └── LineaPedido[]  (idProducto, cantidad, precioUnitario)

Producto[]  (catálogo independiente)
```

## MerkAsia-ImportDataClient

Lee `data/data.csv`, lo parsea a objetos `Cliente → Pedido → LineaPedido` y extrae el catálogo de `Producto` en paralelo. Envía ambos al backend mediante un único POST en JSON con OkHttp.

**Payload enviado a `/import-data`:**

```json
{
  "clientes": [ { "dni": "...", "pedidos": [ { "lineas": [...] } ] } ],
  "productos": [ { "idProducto": 10001, "descripcion": "...", "precioUnitario": 650.00 } ]
}
```

**Ejecutar:**

```bash
cd MerkAsia-ImportDataClient
./gradlew run
```

## MerkAsia-Servlets

Backend Java EE (Maven) desplegado en Tomcat 9. Implementa arquitectura en capas: `view` (servlets) → `controller` → `dataservice` (acceso a MySQL vía JDBC).

### Endpoints

| Método | Endpoint | Parámetros | Descripción |
|---|---|---|---|
| GET | `/list-productos` | — | Lista todos los productos |
| GET | `/find-producto?code={id}` | `code` | Busca producto por código |
| GET | `/find-cliente?dni={dni}` | `dni` (opcional) | Busca cliente por DNI, o lista todos si vacío |
| GET | `/list-producto-pedido?dni={dni}&code={idPedido}` | `dni`, `code` | Líneas del pedido `code` del cliente `dni` |
| POST | `/import-data` | body JSON | Importa el JSON generado por ImportDataClient |

### Base de datos

Esquema definido en `deployment/bbdd.sql` (base de datos `merkasia`, MySQL 8.x):

- `clientes` (PK `dni`)
- `productos` (PK `id_producto`)
- `pedidos` (PK `id_pedido`, FK → `clientes`)
- `lineas_pedido` (FK → `pedidos`, FK → `productos`)

## MerkAsia-Android

App de consulta con dos botones en pantalla principal:

- **Clientes**: lista todos los clientes (`/find-cliente`)
- **Productos**: lista todo el catálogo (`/list-productos`)

Configurar la IP del backend en `MainActivity.BASE_URL` antes de compilar.

## Configuración / Variables de entorno

Por simplicidad didáctica, las credenciales de la base de datos están hardcodeadas en `DatabaseConnection.java`:

```java
private static final String URL = "jdbc:mysql://bbdd:3306/merkasia";
private static final String USER = "merkasia_user";
private static final String PASSWORD = "onlyforyoureyes";
```

En un entorno real, estos valores deberían externalizarse mediante variables de entorno (`.env` + `docker-compose.yml`) en lugar de quedar embebidos en el código fuente.

## Despliegue

```bash
cd deployment
docker-compose up -d
```

Esto levanta:

- Contenedor `bbdd` (MySQL) inicializado con `bbdd.sql`
- Contenedor del backend (Tomcat 9, puerto 8888), construido desde `Dockerfile`

## Requisitos

- Java 21
- Maven (servlets) / Gradle (import client)
- Docker y Docker Compose
- Android Studio (app Android)

## Autoría

Examen Abril 2026 — DAW
