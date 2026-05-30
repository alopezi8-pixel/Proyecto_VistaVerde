# Sistema de Administración de Condominio Vista Verde

Sistema de escritorio desarrollado en Java Swing para la gestión de cobro y control de cuotas de mantenimiento del Condominio Vista Verde (30 casas).

---

## Descripción del Proyecto

El sistema permite al administrador del condominio gestionar de forma completa:
- Registro de propietarios por casa
- Registro y control de pagos de cuotas mensuales
- Configuración del monto de la cuota (global o por casa)
- Generación de reportes generales y de morosos
- Exportar reporte a archivo TXT
- Persistencia de datos entre sesiones (no se pierden al cerrar)
- Notificación por correo electrónico al registrar un pago

---

## Integrantes del Equipo

| Nombre | Carné | Rol |
|--------|-------|-----|
| Andres Emanuel López Illescas | 0900-24-4091 | Desarrollador principal |

---

## Instrucciones para ejecutar el proyecto

### Requisitos
- Java JDK 11 o superior
- Apache NetBeans 21 o superior

### Librerias utilizadas
| Libreria | Para que | Donde descargar |
|----------|----------|----------------|
| `flatlaf-3.4.1.jar` | Tema visual moderno (FlatLaf) | https://www.formdev.com/flatlaf/ |
| `jakarta.mail-2.0.1.jar` | Envio de correos electronicos | https://eclipse-ee4j.github.io/mail/ |

### Pasos para ejecutar en NetBeans
1. Abrir Apache NetBeans
2. File → Open Project
3. Navegar hasta la carpeta `Proyecto_VistaVerde`
4. Seleccionar la carpeta y hacer clic en Open Project
5. Clic derecho en el proyecto → Run (o presionar F6)

### Credenciales de acceso
| Campo | Valor |
|-------|-------|
| Usuario | `iusr_vistaverde` |
| Contraseña | `R3sidencial2026%` |

---

## Capturas de Pantalla

### Login
<img width="298" height="448" alt="image" src="https://github.com/user-attachments/assets/803c22e5-7966-4533-ab18-3e04715c51a1" />

### Menu Principal
<img width="434" height="296" alt="image" src="https://github.com/user-attachments/assets/e9835dfd-f724-4847-b222-b56885c006cb" />

### Registro de Propietario
<img width="453" height="291" alt="image" src="https://github.com/user-attachments/assets/eb0f6636-6fb2-44ed-8bef-aa9b8e7a6eb1" />

### Registro de Pago
<img width="322" height="286" alt="image" src="https://github.com/user-attachments/assets/131e83a0-aa20-43ff-b260-22c0bb7abe96" />

### Configurar Cuota
<img width="248" height="289" alt="image" src="https://github.com/user-attachments/assets/4169d163-8abf-4377-9e32-025a173a05c2" />

### Estado de Cuenta
<img width="360" height="284" alt="image" src="https://github.com/user-attachments/assets/cce97aea-9337-49de-9e60-acdca81e0e93" />

### Reporte General
<img width="442" height="334" alt="image" src="https://github.com/user-attachments/assets/10f0d087-d4ad-469d-a300-7ef8e193eafb" />

### Casas Morosas
<img width="413" height="294" alt="image" src="https://github.com/user-attachments/assets/9d2342fb-4740-40de-b769-280c84951f29" />

---

## Estructura del Proyecto
Proyecto_VistaVerde/
├── src/
│   └── proyecto_vistaverde/
│       ├── Casa.java
│       ├── Condominio.java
│       ├── Pago.java
│       ├── Propietario.java
│       ├── Persistencia.java
│       ├── ManejadorErrores.java
│       ├── EnvioCorreo.java
│       ├── LabelRedondeado.java
│       ├── Login.java
│       ├── Pantalla_Principal.java
│       ├── Registro_Propietario.java
│       ├── Registro_Pago.java
│       ├── Configurar_Cuota.java
│       ├── Estado_de_Cuenta.java
│       ├── Reporte_General.java
│       └── Casas_Morosas.java
├── docs/
│   ├── manual/
│   │   └── Manual_Usuario_VistaVerde.pdf
│   └── diagramas/
│       └── diagrama_clases.png
├── src/imagenes/
│   └── (imagenes del sistema)
├── nbproject/
├── build.xml
└── README.md

## Puntos Extra Implementados

| Funcionalidad | Puntos | Estado |
|---------------|--------|--------|
| Persistencia de datos con archivos binarios | +5 pts | Implementado |
| Envio de correo al propietario al registrar pago | +2 pts | Implementado |
| Diseno con framework FlatLaf | +2 pts | Implementado |

### Configurar envio de correo
1. Abrir `src/proyecto_vistaverde/EnvioCorreo.java`
2. Reemplazar `CORREO_REMITENTE` con el correo Gmail del condominio
3. Reemplazar `CONTRASENA` con la contrasena de aplicacion de Gmail
4. En Gmail: activar verificacion en dos pasos y crear contrasena de aplicacion

---

## Tablero Jira

[Ver tablero del proyecto en Jira](https://proyectoprogra12026.atlassian.net/jira/software/projects/KAN/boards/1?atlOrigin=eyJpIjoiYjkxYmZjNmMxM2ViNGUwMjg3ODc0NjU2YTUwNjk1NTkiLCJwIjoiaiJ9)

---

## Contexto del Negocio

| Dato | Valor |
|------|-------|
| Nombre del condominio | Vista Verde |
| Numero de casas | 30 (numeradas del 1 al 30) |
| Cuota mensual inicial | Q1,500.00 |
| Recaudacion esperada | Q45,000.00 mensual |
| Tipo de sistema | Escritorio Java Swing |

---
