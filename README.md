# alquiler-eventos-common

Módulo compartido (**`common`**) del sistema de alquiler de equipos para eventos.

Contiene todo lo que los microservicios de negocio comparten: el modelo de datos
(entidades JPA), los repositorios de acceso a base de datos, los DTOs y la
configuración de esquema con Liquibase. Se empaqueta como un **JAR** y se consume
como dependencia desde:

- `alquiler-eventos-ms-alquiler`
- `alquiler-eventos-ms-compras`

> Este proyecto **no expone endpoints REST de negocio**. La API de ejemplo que se
> documenta más abajo sirve solo para demostrar la generación automática de DTOs
> a partir de OpenAPI.

---

## Stack y tecnologías

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 21 (LTS, Temurin) | Lenguaje de programación |
| Spring Boot | 3.4.5 | Framework base |
| Spring Data JPA | incluido en Spring Boot | Persistencia y repositorios |
| Hibernate | administrado por Spring Boot | Proveedor JPA (valida el schema) |
| PostgreSQL | driver provisto | Motor de base de datos |
| Liquibase | core | Gestión y versionado del esquema de base de datos |
| Lombok | última estable | Generación de *getters/setters/constructores* |
| SpringDoc OpenAPI | 2.6.0 | Generación de OpenAPI en tiempo de ejecución |
| OpenAPI Generator (Maven) | 7.4.0 | Generación automática de DTOs desde `openapi.yaml` |
| Jackson Databind Nullable | 0.2.6 | Soporte de campos nullable en DTOs generados |
| Reflections | 0.10.2 | Utilidades de reflexión/escaneo de clases |
| Maven | 3.9+ | Build y gestión de dependencias |

### Versiones

- `groupId`: `com.alquilereventos`
- `artifactId`: `alquiler-eventos-common`
- `version`: `1.0.0-SNAPSHOT`
- Paquete base: `com.alquilereventos.common`
- Compilado para **Java 21**

---

## Estructura del proyecto

```
alquiler-eventos-common/
├── pom.xml                                            # Build, dependencias y plugins
└── src/
    └── main/
        ├── java/com/alquilereventos/common/
        │   ├── CommonApplication.java                 # Clase de arranque Spring Boot
        │   ├── entity/                                # Entidades JPA del modelo de datos
        │   │   ├── enums/                             # Enums de estados y tipos
        │   │   ├── HistorialEstado.java               # Clase abstracta @MappedSuperclass
        │   │   └── ...                                # 16 entidades concretas
        │   └── repository/                            # Repositorios Spring Data JPA
        │       └── ...                                # 16 interfaces (uno por entidad)
        └── resources/
            ├── application.yml                        # Config de Datasource, JPA y Liquibase
            ├── db/changelog/db.changelog-master.yaml  # Migraciones Liquibase (esquema + FK)
            └── openapi.yaml                           # Especificación OpenAPI (DTOs de ejemplo)
```

---

## Cómo funciona

### Modelo de datos (`entity/`)

Cada clase es una entidad JPA anotada con `@Entity`, `@Table`, `@Id` y
`@GeneratedValue(IDENTITY)`, usando **Lombok** (`@Getter`, `@Setter`,
`@NoArgsConstructor`, `@AllArgsConstructor`) para reducir boilerplate.

Dominios del modelo:

- **Seguridad / usuarios**: `Usuario`, `Rol`, `Permiso`, `RolPermiso`, `Configuracion`
- **Maestros de negocio**: `Cliente`, `Proveedor`, `CategoriaEquipo`, `Equipo`
- **Alquileres**: `OrdenAlquiler`, `OrdenAlquilerDetalle`, `ChecklistItem`
- **Compras**: `OrdenCompra`, `OrdenCompraDetalle`
- **Historial**: `OrdenAlquilerHistorial`, `OrdenCompraHistorial`

Los estados se modelan con **enums** guardados como texto (`@Enumerated(EnumType.STRING)`),
ubicados en `entity/enums/`.

### Herencia y POO (`HistorialEstado`)

`HistorialEstado` es una clase **abstracta** marcada con `@MappedSuperclass` que
declara el historial común: `id`, `usuario`, `estadoAnterior`, `estadoNuevo` y
`fecha`. Sus subclases **heredan** esos campos y agregan los suyos:

- `OrdenAlquilerHistorial` → agrega `ordenAlquiler` y `montoMora`
- `OrdenCompraHistorial` → agrega `ordenCompra`

Esta jerarquía muestra de forma explícita los cuatro pilares de la POO pedidos en
la entrega (herencia, polimorfismo, encapsulación y abstracción), documentados en
el Javadoc de `HistorialEstado.java`.

### Acceso a datos (`repository/`)

Una interfaz `extends JpaRepository<Entidad, Integer>` por entidad. Los
microservicios inyectan estos repositorios para leer/escribir sin escribir SQL.
Incluyen algunos métodos de búsqueda derivados por convención de nombres
(ej.: `findByEmail`, `findByClienteId`).

### Base de datos (Liquibase)

Liquibase **gestiona el esquema**: cada tabla se crea con su propio `changeSet`
en `db.changelog-master.yaml`, respetando las **foreign keys** con constraints
con nombre (`foreignKeyName` + `references`).

En `application.yml`:

- `spring.jpa.hibernate.ddl-auto: validate` → Hibernate **valida** que las
  entidades coincidan con el esquema, sin modificarlo.
- Las credenciales de PostgreSQL se leen de variables de entorno con valores por
  defecto para desarrollo local.

### DTOs y OpenAPI (`openapi.yaml`)

En `openapi.yaml` se definen **schemas y operaciones de ejemplo** para el módulo
compartido. Al compilar, el plugin `openapi-generator-maven-plugin` genera
automáticamente los DTOs en `target/generated-sources/openapi/` dentro del paquete
`com.alquilereventos.common.dto`.

DTOs disponibles actualmente:

- **Clientes**: `Cliente`, `ClienteCrearRequest`, `ApiError`
- **Proveedores**: `Proveedor`, `ProveedorCrearRequest`, `PageProveedor`
- **Compras**: `OrdenCompra`, `OrdenCompraCrearRequest`, `OrdenCompraDetalle`,
  `OrdenCompraEstadoRequest`, `PageOrdenCompra`, `EstadoOrdenCompra`
- **Alquiler**: `OrdenAlquiler`, `OrdenAlquilerCrearRequest`, `OrdenAlquilerDetalle`,
  `OrdenAlquilerEstadoRequest`, `PageOrdenAlquiler`, `EstadoOrdenAlquiler`

> Los DTOs se generan SOLOS al compilar. **No se editan a mano**: cualquier cambio
> se hace en `openapi.yaml` y se regenera con `mvn clean install`.

### Cómo agregar DTOs (paso a paso)

Este fue el procedimiento usado para incorporar los DTOs de `OrdenAlquiler`
(feature `feature/dto-orden-alquiler`):

1. **Editar `src/main/resources/openapi.yaml`**:
   - Agregar bajo `components/schemas/` los objetos (con `type: object`, `properties`,
     `required`, `example`) y los enums (`type: string` + `enum`).
   - Marcar `readOnly: true` lo que el servidor calcula o genera (ej. `id`, `total`,
     `createdAt`, `subtotal`): esos campos NO tendrán `setter` y entrarán por el
     constructor generado.
   - Si se quiere, documentar también los `paths` (solo referencia informativa:
     `generateApis=false`, los microservicios implementan sus propios controllers).
2. **Regenerar e instalar**:
   ```bash
   mvn clean install
   ```
   Deja el JAR actualizado en `~/.m2/repository/com/alquilereventos/...` para que
   los microservicios lo consuman como dependencia.
3. **Verificar la generación**:
   Revisar los archivos nuevos en
   `target/generated-sources/openapi/src/main/java/com/alquilereventos/common/dto/`
   y confirmar los constructores/setters que usará el mapper del microservicio.
4. **Publicar el cambio (regla del proyecto)**:
   ```bash
   git checkout -b feature/<nombre-que-describe-el-dto>
   git add src/main/resources/openapi.yaml
   git commit -m "Agrega DTOs de <dominio>"
   git push -u origin feature/<nombre>
   ```
   Abrir el **Pull Request** hacia `main` desde GitHub y esperar la review del
   otro integrante antes de mergear.
5. **Sincronización del otro integrante** (después del merge):
   ```bash
   git pull
   mvn clean install
   ```

> **Formato común a respetar en el yaml:** nombres de propiedades en camelCase que
> coincidan con los atributos de las entidades (`equipoId`, `fechaEvento`,
> `fechaDevolucion`, `precioDia`, ...) para simplificar el mapeo entity <-> dto.

---

## Requisitos previos

- **Java 21** (JDK, por ejemplo Temurin)
- **Maven 3.9+**
- **PostgreSQL** disponible para levantar la base (opcional para compilar)

## Build

```bash
mvn clean install
```

Genera el JAR en `target/alquiler-eventos-common-1.0.0-SNAPSHOT.jar` y lo deja
instalado en el repositorio local de Maven para que los microservicios puedan
consumirlo.

## Configuración de base de datos

La conexión se define con variables de entorno (los valores por defecto
corresponden a una base local):

| Variable | Defecto |
|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/alquiler_eventos` |
| `DB_USER` | `postgres` |
| `DB_PASSWORD` | `postgres` |

Al arrancar una aplicación que consuma este JAR, Liquibase crea las 16 tablas y
sus claves foráneas, y Hibernate valida el esquema contra las entidades.

---

## Estado del trabajo

Progreso por pasos (en orden de implementación, reflejado en el historial de git):

1. **PASO 1** — Proyecto Maven/Spring Boot base (`pom.xml`, config y estructura).
2. **PASO 2** — Entidades JPA con Lombok, enums y jerarquía `HistorialEstado`.
3. **PASO 3** — Repositorios Spring Data JPA, changelog Liquibase y `application.yml`.
4. **PASO 4** — `openapi.yaml` con endpoints de ejemplo, generación de DTOs y verificación del build.
5. **PASO 5** — Preparación para la entrega 2: JAR como librería, borrado lógico,
   queries con filtros/paginación y DTOs de compras.
6. **PASO 6** — DTOs y endpoints de `OrdenAlquiler` en `openapi.yaml` (rama
   `feature/dto-orden-alquiler`, pendiente de PR).