# 🏘️ Sistema de Administración de Condominio Vista Verde

Sistema de escritorio desarrollado en **Java Swing** para la gestión de cobro y control de cuotas de mantenimiento del Condominio Vista Verde (30 casas).

---

## 📋 Descripción del Proyecto

El sistema permite al administrador del condominio gestionar de forma completa:
- Registro de propietarios por casa
- Registro y control de pagos de cuotas mensuales
- Configuración del monto de la cuota
- Generación de reportes generales y de morosos
- Persistencia de datos entre sesiones (no se pierden al cerrar)
- Notificación por correo electrónico al registrar un pago *(punto extra)*

---

## 👥 Integrantes del Equipo

| Nombre | Carné | Rol |
|--------|-------|-----|
| [Nombre 1] | [Carné 1] | Líder de equipo / Backend (modelos y lógica) |
| [Nombre 2] | [Carné 2] | Frontend (pantallas Swing) |
| [Nombre 3] | [Carné 3] | Documentación y pruebas |
| [Nombre 4] | [Carné 4] | Integración y versionamiento |

---

## 🚀 Instrucciones para ejecutar el proyecto

### Requisitos
- Java JDK 11 o superior
- IDE recomendado: IntelliJ IDEA o Eclipse

### Librerías opcionales (puntos extra)
| Librería | Para qué | Dónde descargar |
|----------|----------|----------------|
| `flatlaf-3.x.x.jar` | Tema visual moderno | https://www.formdev.com/flatlaf/ |
| `javax.mail.jar` | Envío de correos | https://eclipse-ee4j.github.io/mail/ |

### Pasos para compilar y ejecutar

**Sin IDE (línea de comandos):**
```bash
# Compilar (desde la raíz del proyecto)
javac -cp "src;libs/*" -d out src/model/*.java src/logic/*.java src/ui/*.java Main.java

# Ejecutar
java -cp "out;libs/*" Main
```

**Con IntelliJ IDEA:**
1. Abrir la carpeta `VistaVerde` como proyecto
2. Marcar `src` como Sources Root (clic derecho → Mark Directory As)
3. Agregar los `.jar` de `libs/` en Project Structure → Libraries
4. Ejecutar `Main.java`

**Con Eclipse:**
1. File → Import → Existing Project
2. Añadir los `.jar` a Build Path
3. Ejecutar como Java Application desde `Main.java`

### Credenciales de acceso
| Campo | Valor |
|-------|-------|
| Usuario | `iusr_vistaverde` |
| Contraseña | `R3sidencial2026%` |

---

## 📸 Capturas de Pantalla

> *(Insertar capturas reales del sistema aquí después de ejecutarlo)*

| Pantalla | Descripción |
|----------|-------------|
| Login | Acceso con credenciales del administrador |
| Inicio | Menú principal con los 6 módulos |
| Registro Propietario | Registro de dueños de cada casa |
| Registro Pago | Registro de pagos mensuales |
| Configurar Cuota | Modificar el monto de mantenimiento |
| Estado de Cuenta | Ver historial de pagos por casa |
| Reporte General | Tabla con el estado de las 30 casas |
| Casas Morosas | Lista de casas sin pago del mes actual |

---

## 🏗️ Estructura del Proyecto

```
VistaVerde/
├── Main.java                    # Punto de entrada del sistema
├── src/
│   ├── model/
│   │   ├── Casa.java            # Modelo de casa con pagos
│   │   ├── Propietario.java     # Modelo de propietario
│   │   ├── Pago.java            # Modelo de pago de cuota
│   │   └── Condominio.java      # Administra las 30 casas
│   ├── logic/
│   │   ├── Validaciones.java    # Validaciones reutilizables
│   │   ├── Persistencia.java    # Guardar/cargar datos (binario)
│   │   └── CorreoService.java   # Envío de correos (punto extra)
│   └── ui/
│       ├── Tema.java            # Estilos y componentes visuales
│       ├── PantallaLogin.java
│       ├── PantallaInicio.java
│       ├── PantallaRegistroPropietario.java
│       ├── PantallaRegistroPago.java
│       ├── PantallaConfiguracionCuota.java
│       ├── PantallaEstadoCuenta.java
│       ├── PantallaReporteGeneral.java
│       └── PantallaCasasMorosas.java
├── docs/
│   ├── manual/                  # Manual de usuario en PDF
│   └── diagramas/               # Diagrama de clases
├── libs/                        # Librerías externas (.jar)
└── README.md
```

---

## ✨ Puntos Extra Implementados

| Funcionalidad | Puntos | Estado |
|---------------|--------|--------|
| Persistencia de datos (archivos binarios) | +5 pts | ✅ Implementado |
| Envío de correo al propietario | +2 pts | ✅ Implementado (requiere config.) |
| Diseño con framework FlatLaf | +2 pts | ✅ Implementado (requiere jar) |

### Configurar envío de correo
1. Abrir `src/logic/CorreoService.java`
2. Reemplazar `GMAIL_USER` y `GMAIL_APP_PASSWORD` con las credenciales del condominio
3. Cambiar `habilitado = false` a `habilitado = true`
4. Para Gmail: activar "Contraseñas de aplicación" en la cuenta Google

---

## 📋 Tablero Jira

> [Link al tablero Jira del equipo aquí]

---

## 📐 Contexto del Negocio

| Dato | Valor |
|------|-------|
| Nombre del condominio | Vista Verde |
| Número de casas | 30 (numeradas del 1 al 30) |
| Cuota mensual inicial | Q1,500.00 |
| Recaudación esperada | Q45,000.00 mensual |
| Tipo de sistema | Escritorio Java Swing |

---

*Universidad Mariano Gálvez de Guatemala · Facultad de Ingeniería en Sistemas · Programación I · 2026*
