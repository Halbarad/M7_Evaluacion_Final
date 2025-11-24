# 📱 Labor Forum - Bolsa de Empleo Móvil

![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-purple?style=for-the-badge&logo=kotlin)
![Android](https://img.shields.io/badge/Platform-Android-green?style=for-the-badge&logo=android)
![Architecture](https://img.shields.io/badge/Architecture-MVVM-blue?style=for-the-badge)
![Database](https://img.shields.io/badge/Database-Room-lightgrey?style=for-the-badge)

### **Repositorio:** https://github.com/Halbarad/M7_Evaluacion_Final

## 📄 Descripción del Proyecto

**Labor Forum** es una aplicación nativa de Android diseñada para conectar a empleadores con trabajadores en Chile. La plataforma permite publicar ofertas laborales detalladas y postular a ellas de manera eficiente, gestionando automáticamente los conflictos de agenda y priorizando oportunidades locales.

El sistema implementa una lógica inteligente que muestra al usuario primero las ofertas de su misma comuna, luego las de su región, y finalmente el resto del país, facilitando la búsqueda de empleo local.

---

## 🎯 Motivación

Este proyecto fue desarrollado como parte de la **Evaluación Final del Módulo 7 (Desarrollo de Aplicaciones Móviles)**.

El objetivo principal fue demostrar el dominio de prácticas modernas de desarrollo Android, incluyendo:
*   Persistencia de datos local robusta.
*   Manejo de concurrencia y operaciones asíncronas.
*   Arquitectura limpia y escalable.
*   Implementación de pruebas unitarias e instrumentadas para asegurar la calidad del software.

---

## ✨ Funcionalidades Clave

### 🏢 Para Empleadores (Publicadores)
*   **Creación de Ofertas:** Formulario intuitivo con selectores de Región/Comuna dinámicos y rangos de fechas validados.
*   **Gestión de "Mis Ofertas":** Visualización de todas las ofertas creadas por el usuario.
*   **Edición de Ofertas:** Capacidad de actualizar los datos de una oferta existente (título, salario, cupos, etc.) reutilizando el flujo de creación.

### 👷 Para Trabajadores (Postulantes)
*   **Exploración Inteligente:** Las ofertas se ordenan automáticamente por proximidad geográfica (Comuna > Región).
*   **Postulación Segura:** Sistema de validación que impide postular si las fechas del nuevo trabajo se solapan con un contrato ya activo ("Tope de Horario").
*   **Contratos Activos:** Vista dedicada para revisar los trabajos en los que el usuario ha sido aceptado y están vigentes.

### ⚙️ Características Técnicas Transversales
*   **Filtros de Visualización:** Un usuario no ve sus propias ofertas en la lista general de postulación.
*   **Validación de Datos:** Manejo de formatos de moneda (CLP) y fechas UTC para evitar errores de zona horaria.
*   **Navegación Fluida:** Uso de Navigation Component para transiciones y paso de argumentos entre pantallas.

---

## 🛠️ Tecnologías Utilizadas

El proyecto está construido utilizando **Kotlin** y el **Android SDK**, siguiendo la arquitectura **MVVM (Model-View-ViewModel)**.

*   **Android Jetpack:**
    *   **Room Database:** Para persistencia local de datos (Usuarios, Ofertas, Contratos) y relaciones (Foreign Keys).
    *   **ViewModel & LiveData:** Para la gestión del estado de la UI y el ciclo de vida.
    *   **ViewBinding:** Para la interacción segura con las vistas XML.
    *   **Navigation Component:** Para el flujo de navegación y paso de argumentos (`SafeArgs`).
*   **Asincronía:**
    *   **Kotlin Coroutines:** Para operaciones en segundo plano (Base de datos).
    *   **Flow:** Para flujos de datos reactivos desde la base de datos.
*   **Testing:**
    *   **JUnit 4:** Pruebas unitarias de lógica de negocio.
    *   **Espresso & AndroidX Test:** Pruebas instrumentadas de integración de base de datos.

---

## 🧪 Testing y Calidad

El proyecto incluye una suite de pruebas para garantizar la estabilidad:

1.  **Pruebas Unitarias (`src/test`):**
    *   Verificación de lógica auxiliar (`DatosGeograficos`).
    *   Validación de algoritmos de ordenamiento y filtrado de ofertas (`OfertaSorter`).
2.  **Pruebas Instrumentadas (`src/androidTest`):**
    *   Validación de consultas SQL complejas.
    *   Verificación de integridad referencial (Foreign Keys).
    *   Comprobación de filtros de base de datos (ej. no mostrar ofertas propias).

---

## 🚀 Guía de Instalación y Uso

### Prerrequisitos
*   Android Studio Koala o superior.
*   JDK 11 o superior.
*   Dispositivo o Emulador con Android 8.1 (API 27) o superior.

### Pasos
1.  **Clonar el repositorio:**
    ```bash
    git clone https://github.com/Halbarad/M7_Evaluacion_Final.git
    ```
2.  **Abrir en Android Studio:**
    *   Selecciona `Open` y busca la carpeta clonada.
    *   Espera a que Gradle sincronice las dependencias.
3.  **Ejecutar la App:**
    *   Presiona el botón `Run` (▶️) en la barra superior.
4.  **Ejecutar Pruebas:**
    *   Para correr los tests, haz clic derecho en la carpeta `cl.unab.m7_evaluacion_final` dentro de `androidTest` (o `test`) y selecciona "Run Tests".

### Pasos para instalar en dispositivo

1. **Descargar el .aab**
    * Aceptar en el dispositivo e instalar.
2. **Funcionalidades de la aplicación**
    * Una vez abierta la aplicación, se debe registrar un usuario.
    * Iniciar sesión con el usuario creado.
    * En la pantalla principal "Ofertas Laborales" se visualizan los trabajos a los que se puede postular.
    * Utilizando la barra de navegación inferior se puede navegar a "Contratos" donde se muestran los trabajos postulados.
    * En "Mis Ofertas" se muestran las ofertas de trabajo creadas por el usuario logueado.
    * En "Perfil" se muestra la información del usuario.
    * En la barra de navegación se puede seleccionar los 3 puntos para desplegar un menú donde se puede crear una oferta laboral o cerrar seción.

---

**Desarrollado por:** Sebastián Ramírez - UNAB 2025
