# Exposición: Testing en Android — De lo básico a lo avanzado

Estructura pensada para cubrir casi todo el ecosistema de testing en Android, organizada en 5 bloques de dificultad ascendente. Cada bloque incluye qué tipo de test es, qué herramientas se usan, un ejemplo mínimo de qué mostrar en vivo, y el "por qué importa" para justificarlo ante la clase.

---

## Bloque 1 — Fundamentos: Unit Testing puro (JVM, sin Android)

**Dificultad: ⭐**

| Tipo | Qué prueba | Herramienta |
|---|---|---|
| Unit test básico | Funciones puras, lógica de negocio sin dependencias de Android | JUnit 4/5 |
| Aserciones | Validar resultados esperados | JUnit `assert*`, o AssertJ/Truth para aserciones más legibles |

**Qué mostrar en vivo:** una función simple (ej. validar un email o calcular un total) con 3-4 casos de prueba (`@Test`), incluyendo un caso límite y uno que falla a propósito para explicar el reporte de error.

**Punto clave para la exposición:** estos tests corren en la JVM local (no necesitan emulador), por lo que son rapidísimos — es la base de la pirámide de testing.

---

## Bloque 2 — Aislar dependencias: Mocking y Fakes

**Dificultad: ⭐⭐**

| Tipo | Qué prueba | Herramienta |
|---|---|---|
| Mocks | Simular una dependencia (ej. un repositorio o API) | Mockito / MockK (si usan Kotlin) |
| Fakes | Implementación simplificada real en vez de mock | Clases fake escritas a mano |
| Testing de ViewModel | Lógica de presentación sin UI | JUnit + Mockito/MockK + `InstantTaskExecutorRule` |

**Qué mostrar en vivo:** un ViewModel que depende de un repositorio; mockear el repositorio con Mockito/MockK y verificar que el ViewModel expone el estado correcto (loading → success/error).

**Punto clave:** aquí se explica la diferencia entre mock (verifica interacciones) y fake (se comporta como el real pero simplificado), y por qué esto permite testear en aislamiento.

---

## Bloque 3 — Testing de flujos asíncronos y datos

**Dificultad: ⭐⭐⭐**

| Tipo | Qué prueba | Herramienta |
|---|---|---|
| Coroutines | Funciones `suspend` | `kotlinx-coroutines-test`, `runTest` |
| Flow / StateFlow | Streams de datos reactivos | `Turbine` (librería para testear Flows) |
| Room (base de datos) | Queries y DAOs | Room in-memory database + JUnit |
| Retrofit / red | Llamadas HTTP simuladas | `MockWebServer` (OkHttp) |

**Qué mostrar en vivo:** un DAO de Room probado con una base de datos en memoria (insertar, consultar, borrar), y/o un endpoint simulado con MockWebServer devolviendo una respuesta JSON fija.

**Punto clave:** distinguir entre "mockear la red" (rápido, no depende de internet) y "test de integración real" (más lento pero más realista) — trade-off importante para justificar decisiones de testing.

---

## Bloque 4 — UI Testing (Instrumented Tests)

**Dificultad: ⭐⭐⭐⭐**

| Tipo               | Qué prueba                                 | Herramienta                                 |
| ------------------ | ------------------------------------------ | ------------------------------------------- |
| Espresso           | Interacciones de UI en Views XML clásicas  | Espresso (`onView`, `perform`, `check`)     |
| Compose UI Test    | Interacciones de UI en Jetpack Compose     | `createComposeRule`, `onNodeWithText`, etc. |
| Navegación         | Flujo entre pantallas                      | Espresso + Navigation Testing               |
| Screenshot testing | Comparar UI visualmente contra un baseline | Paparazzi o Shot                            |

**Qué mostrar en vivo:** un test instrumentado que abre una pantalla, escribe en un campo, presiona un botón y verifica que aparece el resultado esperado — corriendo en el emulador en vivo (esto siempre engancha a la audiencia).

**Punto clave:** explicar que estos corren en un dispositivo/emulador real (más lentos), y por qué se recomienda tener pocos comparados con los unit tests (pirámide de testing: muchos unit, menos integración, pocos UI).

---

## Bloque 5 — Integración avanzada y automatización

**Dificultad: ⭐⭐⭐⭐⭐**

| Tipo | Qué prueba | Herramienta |
|---|---|---|
| Hilt/Dagger testing | Inyección de dependencias en tests | `hilt-android-testing`, módulos de test |
| Testing con corrutinas + Flow en ViewModel completo | Integración end-to-end de una feature | Combinación de todo lo anterior |
| UI Automator | Testing fuera de tu propia app (permisos del sistema, notificaciones) | UI Automator |
| Cobertura de código | Medir qué % del código está cubierto | JaCoCo |
| CI/CD | Correr tests automáticamente en cada push | GitHub Actions / GitLab CI con Gradle |

**Qué mostrar en vivo (si el tiempo alcanza):** un pipeline simple de GitHub Actions que corre `./gradlew test` en cada push, mostrando el reporte de JaCoCo.

**Punto clave de cierre:** la pirámide de testing completa — resumir cuántos tests de cada tipo debería tener un proyecto real y por qué (costo vs. velocidad vs. confianza).

---

## Sugerencia de cronograma para la exposición (si es de ~20-30 min)

1. Introducción a la pirámide de testing (3 min)
2. Bloque 1 y 2 en vivo — unit tests + mocks (7 min)
3. Bloque 3 — Room/Flow/Retrofit, con demo rápida (5 min)
4. Bloque 4 — demo de Espresso/Compose en el emulador (8 min)
5. Bloque 5 — mención rápida de CI y cobertura, cierre (5 min)

## Nota sobre ejemplos

Si quieres, los ejemplos de código de cada bloque los puedo armar sobre un proyecto simple genérico (una app de notas o de lista de tareas), o adaptarlos a un proyecto Android que ya tengas — dime cuál prefieres.
