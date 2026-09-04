# 🚗 Tienda Parking — Sistema de Gestión de Parqueadero (SGP)

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Java](https://img.shields.io/badge/Java-17%2B%20%2F%2026-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-tienda__parking-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-HTML5-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Wrapper-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

**Tienda Parking** es una aplicación web empresarial desarrollada sobre el framework **Spring Boot 3.3.5** implementando el patrón de arquitectura **Modelo-Vista-Controlador (MVC)**. Diseñada para la administración integral de parqueaderos, control de flota vehicular, especificaciones mecánicas, conductores autorizados, directorio de pasajeros y despacho operativo de viajes.

---

## 🌟 Características y Módulos del Sistema

- 📊 **Panel de Control (Dashboard):** Métricas KPI en tiempo real (Viajes despachados, Vehículos, Motores, Choferes y Pasajeros registrados).
- 🚗 **Gestión de Vehículos (Carros):** Módulo dedicado al registro y control de la flota por placa, marca y modelo.
- ⚙️ **Especificaciones de Motores (Módulo Independiente):** Módulo desacoplado para la administración técnica de motores (número de serie único, tipo de motor y cilindraje).
- 👨‍✈️ **Control de Conductores (Choferes):** Registro de choferes autorizados para operar vehículos con cédula y número de licencia.
- 👥 **Directorio de Pasajeros:** Administración de clientes/pasajeros con documento de identidad y teléfonos de contacto.
- 📋 **Fichas de Despacho de Viajes (HU-01):** Asignación y despacho de operaciones vinculando en una sola entidad a un vehículo, un motor, un conductor y un pasajero.
- 📑 **Exportación Consolidada a Excel (.xlsx):** Botón centralizado en el Dashboard (**Exportar Reporte General**) que genera un libro Excel estructurado con **5 hojas formateadas** (Viajes, Carros, Motores, Choferes y Pasajeros) con anchos de columna automáticos y estilos corporativos.
- 🔍 **Buscador en Tiempo Real:** Filtrado dinámico instantáneo por teclado en cada una de las tablas del sistema.
- 🎨 **Diseño Moderno y Responsivo:** Interfaz construida con una paleta oscura/clara corporativa, tipografía moderna, microinteracciones suaves y diseño adaptativo.

---

## 🏗️ Arquitectura del Proyecto (Patrón MVC y Capas)

El proyecto implementa el desacoplamiento de responsabilidades mediante cinco capas arquitectónicas:

```text
┌────────────────────────────────────────────────────────────────────────┐
│                   NAVEGADOR WEB (Cliente HTTP / UI)                    │
└───────────────────┬────────────────────────────────▲───────────────────┘
                    │ 1. Petición (GET / POST)       │ 6. Renderizado HTML
                    ▼                                │
┌────────────────────────────────────────────────────┴───────────────────┐
│              CAPA CONTROLADOR (Controller Layer)                       │
│    Maneja rutas, valida entrada, invoca servicios y alimenta Model    │
└───────────────────┬────────────────────────────────▲───────────────────┘
                    │ 2. Ejecuta lógica de negocio   │ 5. Retorna DTOs / Model
                    ▼                                │
┌────────────────────────────────────────────────────┴───────────────────┐
│               CAPA DE SERVICIO (Service Layer)                         │
│           Reglas del negocio, validaciones y orquestación              │
└───────────────────┬────────────────────────────────▲───────────────────┘
                    │ 3. Invoca persistencia         │ 4. Retorna Entidades Java
                    ▼                                │
┌────────────────────────────────────────────────────┴───────────────────┐
│             CAPA REPOSITORIO (Repository Layer / JPA)                  │
│       Mapeo ORM automático, abstracción CRUD sin SQL manual            │
└───────────────────┬────────────────────────────────▲───────────────────┘
                    │ Consultas SQL (Hibernate)      │ Filas de datos
                    ▼                                │
┌────────────────────────────────────────────────────┴───────────────────┐
│                 BASE DE DATOS RELACIONAL (MySQL)                       │
└────────────────────────────────────────────────────────────────────────┘
```

---

## 📁 Estructura del Repositorio

```text
Tienda_Parking_Springboot/
├── README.md
└── Tienda version springboot/
    ├── pom.xml                                   <-- Dependencias y configuración Maven
    ├── mvnw / mvnw.cmd / .mvn/                   <-- Maven Wrapper portable
    └── src/
        └── main/
            ├── java/com/tienda/
            │   ├── TiendaSpringBootApplication.java  <-- Clase Principal (@SpringBootApplication)
            │   ├── controller/                       <-- Controladores Web MVC & REST
            │   │   ├── HomeController.java           <-- Dashboard y API /api/reporte-general
            │   │   ├── CarroController.java          <-- /carros
            │   │   ├── MotorController.java          <-- /motores
            │   │   ├── ChoferController.java         <-- /choferes
            │   │   ├── PasajeroController.java       <-- /pasajeros
            │   │   └── ViajeController.java          <-- /viajes
            │   ├── model/                            <-- Entidades JPA mapeadas a MySQL
            │   │   ├── Carro.java
            │   │   ├── Motor.java
            │   │   ├── Chofer.java
            │   │   ├── Pasajero.java
            │   │   └── Viaje.java                    <-- Relaciones @ManyToOne
            │   ├── repository/                       <-- Interfaces JpaRepository
            │   │   ├── CarroRepository.java
            │   │   ├── MotorRepository.java
            │   │   ├── ChoferRepository.java
            │   │   ├── PasajeroRepository.java
            │   │   └── ViajeRepository.java
            │   └── service/                          <-- Lógica del negocio y transacciones
            │       ├── CarroService.java
            │       ├── MotorService.java
            │       ├── ChoferService.java
            │       ├── PasajeroService.java
            │       └── ViajeService.java
            └── resources/
                ├── application.properties            <-- Conexión MySQL & Puerto 8085
                ├── static/                           <-- Recursos estáticos del cliente
                │   ├── css/styles.css                <-- Estilos UI corporativos
                │   └── js/main.js                    <-- Buscador y generador Excel multi-hoja
                └── templates/                        <-- Vistas Thymeleaf HTML5
                    ├── index.html                    <-- Dashboard y Exportación General
                    ├── carros.html                   <-- Gestión de Vehículos
                    ├── motores.html                  <-- Especificaciones de Motores
                    ├── choferes.html                 <-- Gestión de Choferes
                    ├── pasajeros.html                <-- Directorio de Pasajeros
                    └── viajes.html                   <-- Fichas de Despacho Operativo
```

---

## 🗺️ Mapa de Rutas Web

| Módulo / Función | Método HTTP | Ruta | Descripción |
| :--- | :---: | :--- | :--- |
| **Dashboard** | `GET` | `/` | Vista de inicio con KPIs y botón de exportación general |
| **Reporte General** | `GET` | `/api/reporte-general` | Endpoint JSON que consolida todas las entidades |
| **Vehículos** | `GET` / `POST` | `/carros` / `/guardar` | Listado y registro de vehículos |
| **Vehículos** | `GET` | `/carros/eliminar/{placa}` | Eliminación de vehículo por placa (Ruta dinámica) |
| **Motores** | `GET` / `POST` | `/motores` / `/guardar` | Listado y registro técnico de motores |
| **Motores** | `GET` | `/motores/eliminar/{numeroSerie}` | Eliminación de motor por serie (Ruta dinámica) |
| **Choferes** | `GET` / `POST` | `/choferes` / `/guardar` | Listado y registro de choferes autorizados |
| **Choferes** | `GET` | `/choferes/eliminar/{cedula}` | Eliminación de chofer por cédula (Ruta dinámica) |
| **Pasajeros** | `GET` / `POST` | `/pasajeros` / `/guardar` | Listado y registro de pasajeros |
| **Pasajeros** | `GET` | `/pasajeros/eliminar/{cedula}` | Eliminación de pasajero por cédula (Ruta dinámica) |
| **Viajes** | `GET` / `POST` | `/viajes` / `/guardar` | Listado y despacho de viajes con selección vinculada |
| **Viajes** | `GET` | `/viajes/eliminar/{idViaje}` | Cancelación/eliminación de viaje por ID (Ruta dinámica) |

---

## 🚀 Requisitos e Instalación

### Prerrequisitos
- **Java JDK 17** o superior (Compatible con JDK 26).
- **MySQL Server** (a través de XAMPP / MariaDB / Workbench).
- Git.

### 1. Configuración de la Base de Datos en MySQL
Abre phpMyAdmin (`http://localhost/phpmyadmin`) o la consola de MySQL y crea la base de datos:
```sql
CREATE DATABASE tienda_parking CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

### 2. Configurar la Conexión (`application.properties`)
Verifica los parámetros en `src/main/resources/application.properties`:
```properties
server.port=8085
spring.datasource.url=jdbc:mysql://localhost:3306/tienda_parking?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

### 3. Ejecutar la Aplicación

Navega al directorio del proyecto:
```bash
cd "Tienda version springboot"
```

**Opción A: Utilizando Maven Wrapper (Recomendado, no requiere Maven instalado)**
- **Windows (PowerShell/CMD):**
  ```powershell
  .\mvnw.cmd spring-boot:run
  ```
- **Linux / macOS:**
  ```bash
  ./mvnw spring-boot:run
  ```

**Opción B: Utilizando Maven instalado en el sistema**
```bash
mvn spring-boot:run
```

---

## 🌐 Acceso a la Interfaz Web

Una vez que Spring Boot inicie en la consola, ingresa desde tu navegador:

👉 **[http://localhost:8085](http://localhost:8085)**

---

## 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Java 17+
- **Framework:** Spring Boot 3.3.5
- **Capa Web:** Spring MVC
- **Persistencia:** Spring Data JPA & Hibernate ORM
- **Motor de Plantillas:** Thymeleaf HTML5
- **Base de Datos:** MySQL 8.0
- **Herramienta de Construcción:** Apache Maven 3.9 & Maven Wrapper
- **Exportación Excel:** SheetJS (`xlsx.full.min.js`)

---

### 🎓 Proyecto de Formación Técnica
Desarrollado para el programa **Análisis y Desarrollo de Software (ADSO)** — **SENA Regional CIES**.
