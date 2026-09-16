# Requerimientos — App de Notas (Room + ViewModel)

Documento de requerimientos del proyecto demostrativo de testing en Android. El objetivo es que un solo proyecto cubra los 5 bloques de la exposición, así que cada requerimiento está etiquetado con el bloque de testing que permite demostrar.

---

## 1. Requerimientos Funcionales (RF)

| ID | Requerimiento | Detalle |
|---|---|---|
| RF-01 | Crear nota | El usuario puede crear una nota con título y contenido. |
| RF-02 | Listar notas | La pantalla principal muestra todas las notas ordenadas por fecha de modificación (más reciente primero). |
| RF-03 | Editar nota | Al tocar una nota se abre la pantalla de edición y se puede modificar título/contenido. |
| RF-04 | Eliminar nota | El usuario puede borrar una nota (con confirmación o swipe). |
| RF-05 | Persistencia | Las notas se guardan en una base de datos Room local y sobreviven al reinicio de la app. |
| RF-06 | Validación de título | No se puede guardar una nota sin título; se muestra un mensaje de error. |
| RF-07 | Validación de contenido | No se puede guardar una nota con título y contenido vacíos a la vez. |
| RF-08 | Estado de carga | Mientras se leen las notas de la BD se muestra un estado de "cargando" y luego el de "éxito/vacío". |

## 2. Requerimientos Técnicos / Arquitectura (RT)

| ID | Requerimiento | Detalle |
|---|---|---|
| RT-01 | Arquitectura | MVVM: `View`, `ViewModel`, `Model` en capas separadas. |
| RT-02 | Patrón repositorio | Un `NoteRepository` media entre el ViewModel y el DAO de Room. |
| RT-03 | Estado reactivo | El ViewModel expone `StateFlow<NotesUiState>` con estados: `Loading`, `Success(List<Note>)`, `Error`. |
| RT-04 | Coroutines | Toda operación de BD (insertar, consultar, borrar) corre en `Dispatchers.IO`; el ViewModel usa `viewModelScope`. |
| RT-05 | Inyección de dependencias | Hilt para inyectar DAO, repositorio y ViewModel. |
| RT-06 | UI | Jetpack Compose (o Views XML clásicas con ViewBinding). |
| RT-07 | Fuente de verdad única | El repositorio escucha los cambios con `Flow<Note>` de Room para que la UI se actualice automáticamente. |

## 3. Requerimientos de Testing (RTest) — alineados a los bloques de la exposición

| ID | Bloque | Requerimiento | Detalle |
|---|---|---|---|
| RTest-01 | 1 | Unit test de validación | Tests JUnit de la lógica de validación (título vacío, longitud, contenido vacío) con casos límite y un caso que falla a propósito. |
| RTest-02 | 2 | Tests de ViewModel | ViewModel probado con un repositorio fake (FakeNoteRepository) y con MockK/Mockito (verificación de interacciones): `loading → success`, `loading → error`. Usa `InstantTaskExecutorRule` si es Views/ViewModel clásico. |
| RTest-03 | 2 | Diferencia mock vs fake | El mismo ViewModel test tiene una versión con Fake y otra con Mock para explicar la diferencia en la exposición. |
| RTest-04 | 3 | Tests de DAO Room | Base de datos Room en memoria (`inMemoryDatabaseBuilder`) para insertar, consultar, actualizar y borrar notas. |
| RTest-05 | 3 | Tests de Flow | Verificar el `StateFlow` del ViewModel con `kotlinx-coroutines-test` + `runTest` y Turbine. |
| RTest-06 | 4 | UI test Compose/Espresso | Test instrumentado: crear una nota desde la UI (escribir título, guardar) y verificar que aparece en la lista. |
| RTest-07 | 4 | Test de navegación | Flujo lista → detalle → guardar → volver a la lista con la nota actualizada. |
| RTest-08 | 5 | Hilt en tests | Un test instrumentado que inyecta el repositorio real contra una BD Room en memoria usando `hilt-android-testing`. |
| RTest-09 | 5 | Cobertura | JaCoCo configurado para generar el reporte de cobertura. |
| RTest-10 | 5 | CI/CD | Pipeline de GitHub Actions que corre `./gradlew test` y `./gradlew connectedCheck` (o `assembleDebug`) en cada push. |

## 4. Entidades de datos

| Campo | Tipo | Regla |
|---|---|---|
| `id` | Long (PK, autogenerada) | Único |
| `title` | String | Obligatorio, no vacío |
| `content` | String | Opcional, pero no puede estar vacío junto con el título |
| `updatedAt` | Long (timestamp) | Se actualiza al crear/modificar |

## 5. Restricciones y supuestos

- R-01: App en **Kotlin**. Sin dependencias de autenticación ni red (la BD es local); la red se puede agregar después para ampliar la demo del Bloque 3 con MockWebServer.
- R-02: Min SDK 24+, target SDK actual estable.
- R-03: La validación de reglas de negocio vive en una clase pura JVM (ej. `NoteValidator`) para ser testeable sin emulador.
- R-04: Sin backend; todo es local. (Si se quiere mostrar MockWebServer, se agrega como feature opcional.)

## 6. Criterios de aceptación (ejemplos de casos para la demo)

- Se crea una nota con título y se guarda → aparece al instante en la lista (RF-01, RF-08).
- Se intenta guardar sin título → aparece mensaje de error (RF-06).
- Se reinicia la app → las notas siguen ahí (RF-05).
- El test del Bloque 1 falla a propósito con un título vacío → se muestra el reporte de JUnit (RTest-01).