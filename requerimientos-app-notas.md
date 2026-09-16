# Requerimientos — App de Notas (Room + ViewModel)

Documento de requerimientos del proyecto demostrativo de testing en Android, redactados como **historias de usuario con criterios de aceptación en Gherkin** (Dado / Cuando / Entonces). Cada historia está etiquetada con el bloque de testing de la exposición que permite demostrar.

---

## 1. Historias de usuario funcionales

### US-01 — Crear una nota
Como **usuario**, quiero **crear una nota con título y contenido**, para **guardar mis ideas y recuperarlas después**.

```gherkin
Escenario: Crear una nota con título y contenido
  Dado que estoy en la pantalla principal
  Cuando toco el botón de nueva nota
  Y escribo el título "Comprar pan"
  Y escribo el contenido "Pan integral y leche"
  Y guardo la nota
  Entonces veo la nota "Comprar pan" en la lista

Escenario: Crear una nota solo con título
  Dado que estoy en la pantalla de nueva nota
  Cuando escribo el título "Recordatorio"
  Y dejo el contenido vacío
  Y guardo la nota
  Entonces veo la nota "Recordatorio" en la lista
```

---

### US-02 — Listar notas
Como **usuario**, quiero **ver todas mis notas**, para **encontrar rápidamente cualquier idea guardada**.

```gherkin
Escenario: Ver las notas ordenadas por fecha de modificación
  Dado que tengo las notas "A" (antigua) y "B" (recientemente editada)
  Cuando abro la pantalla principal
  Entonces veo la nota "B" antes que la nota "A"

Escenario: Ver la pantalla vacía cuando no hay notas
  Dado que no tengo notas guardadas
  Cuando abro la pantalla principal
  Entonces veo el mensaje de lista vacía
```

---

### US-03 — Editar una nota
Como **usuario**, quiero **editar el contenido de una nota existente**, para **corregir o actualizar la información**.

```gherkin
Escenario: Editar el contenido de una nota
  Dado que existe la nota "Comprar pan" con contenido "Pan integral"
  Cuando toco la nota "Comprar pan"
  Y cambio el contenido a "Pan integral, leche y mantequilla"
  Y guardo los cambios
  Entonces la nota se muestra con el contenido actualizado

Escenario: Cancelar la edición
  Dado que estoy editando la nota "Comprar pan"
  Cuando presiono atrás sin guardar
  Entonces la nota conserva su contenido original
```

---

### US-04 — Eliminar una nota
Como **usuario**, quiero **eliminar una nota**, para **deshacerme de información que ya no necesito**.

```gherkin
Escenario: Eliminar una nota desde la pantalla de detalle con confirmación
  Dado que existe la nota "Borrador viejo"
  Cuando abro la nota "Borrador viejo"
  Y toco el botón de eliminar en la pantalla de detalle
  Y confirmo la eliminación
  Entonces la nota "Borrador viejo" desaparece de la lista

Escenario: Cancelar la eliminación desde la pantalla de detalle
  Dado que existe la nota "Borrador viejo"
  Cuando abro la nota "Borrador viejo"
  Y toco el botón de eliminar en la pantalla de detalle
  Y cancelo la confirmación
  Entonces la nota "Borrador viejo" sigue en la lista
```

---

### US-05 — Persistencia de las notas
Como **usuario**, quiero que **mis notas se conserven al cerrar la app**, para **no perder mi información**.

```gherkin
Escenario: Las notas sobreviven al reinicio de la app
  Dado que he creado la nota "Lista de mercado"
  Cuando cierro y vuelvo a abrir la app
  Entonces veo la nota "Lista de mercado" en la lista
```

---

### US-06 — Validación del título
Como **usuario**, quiero **que no me dejen guardar una nota sin título**, para **mantener mis notas identificables y ordenadas**.

```gherkin
Escenario: Intentar guardar una nota sin título
  Dado que estoy en la pantalla de nueva nota
  Cuando dejo el título vacío
  Y guardo la nota
  Entonces veo el mensaje de error "El título es obligatorio"
  Y la nota no se agrega a la lista

Escenario: Intentar guardar una nota con título de solo espacios
  Dado que estoy en la pantalla de nueva nota
  Cuando escribo "   " como título
  Y guardo la nota
  Entonces veo el mensaje de error "El título es obligatorio"
  Y la nota no se agrega a la lista

Escenario: Intentar guardar una nota con título demasiado largo
  Dado que estoy en la pantalla de nueva nota
  Cuando escribo un título de 61 caracteres
  Y guardo la nota
  Entonces veo el mensaje de error "El título no puede superar 60 caracteres"
  Y la nota no se agrega a la lista
```

---

### US-07 — Validación del contenido
Como **usuario**, quiero **que no me dejen guardar una nota completamente vacía**, para **no acumular notas inútiles**.

```gherkin
Escenario: Intentar guardar una nota con título y contenido vacíos
  Dado que estoy en la pantalla de nueva nota
  Cuando dejo el título vacío
  Y dejo el contenido vacío
  Y guardo la nota
  Entonces veo el mensaje de error de nota vacía
  Y la nota no se agrega a la lista

Escenario: Intentar guardar una nota con contenido demasiado largo
  Dado que estoy en la pantalla de nueva nota
  Cuando escribo un contenido de 5001 caracteres
  Y guardo la nota
  Entonces veo el mensaje de error "El contenido no puede superar 5000 caracteres"
  Y la nota no se agrega a la lista
```

---

### US-08 — Estados de carga y error
Como **usuario inclusivo**, quiero **ver un indicador mientras se cargan las notas**, para **saber que la app está trabajando y no ver una pantalla congelada**.

```gherkin
Escenario: Mostrar indicador de carga al abrir la app
  Dado que la base de datos tarda en responder
  Cuando abro la pantalla principal
  Entonces veo el indicador de "cargando"
  Y cuando termina la lectura veo la lista de notas
```

---

### US-09 — Buscar notas
Como **usuario**, quiero **buscar mis notas por título, contenido o etiqueta**, para **encontrar una idea sin recorrer toda la lista**.

```gherkin
Escenario: Buscar por título
  Dado que tengo las notas "Comprar pan" y "Viaje a Medellín"
  Cuando escribo "pan" en el buscador
  Entonces veo solo la nota "Comprar pan"

Escenario: Buscar por contenido
  Dado que tengo la nota "Mercado" con contenido "leche y lactosa"
  Cuando escribo "lactosa" en el buscador
  Entonces veo la nota "Mercado"

Escenario: Buscar por etiqueta
  Dado que la nota "Informe" tiene la etiqueta "trabajo"
  Cuando escribo "trabajo" en el buscador
  Entonces veo la nota "Informe"

Escenario: No hay coincidencias
  Dado que tengo las notas "Comprar pan" y "Viaje a Medellín"
  Cuando escribo "zoológico" en el buscador
  Entonces veo el mensaje "No se encontraron notas"

Escenario: Limpiar la búsqueda
  Dado que busqué "pan" y veo solo la nota "Comprar pan"
  Cuando borro el término de búsqueda
  Entonces vuelvo a ver todas las notas
```

---

### US-10 — Etiquetar notas
Como **usuario**, quiero **asignar una etiqueta a cada nota**, para **organizar mis notas por categoría**.

```gherkin
Escenario: Asignar una etiqueta al crear una nota
  Dado que estoy en la pantalla de nueva nota
  Cuando escribo el título "Recetas"
  Y selecciono la etiqueta "cocina"
  Y guardo la nota
  Entonces veo la nota "Recetas" con la etiqueta "cocina"

Escenario: Cambiar la etiqueta de una nota
  Dado que existe la nota "Correr" con la etiqueta "deporte"
  Cuando la edito y cambio la etiqueta a "salud"
  Y guardo los cambios
  Entonces la nota "Correr" muestra la etiqueta "salud"

Escenario: Quitar la etiqueta de una nota
  Dado que existe la nota "Correr" con la etiqueta "deporte"
  Cuando la edito y dejo la etiqueta vacía
  Y guardo los cambios
  Entonces la nota "Correr" queda sin etiqueta

Escenario: Crear una nota sin etiqueta
  Dado que estoy en la pantalla de nueva nota
  Cuando escribo el título "Borrador"
  Y no selecciono ninguna etiqueta
  Y guardo la nota
  Entonces veo la nota "Borrador" sin etiqueta

Escenario: Filtrar la lista por etiqueta
  Dado que tengo la nota "Informe" con etiqueta "trabajo" y la nota "Fiesta" sin etiqueta
  Cuando selecciono el filtro de etiqueta "trabajo"
  Entonces veo solo la nota "Informe"
```

---

### US-11 — Sincronizar notas con el servidor
Como **usuario**, quiero **que mis notas se descarguen del servidor**, para **tener mis notas disponibles aunque cambie de dispositivo**.

```gherkin
Escenario: Descargar notas del servidor al abrir la app
  Dado que el servidor tiene las notas "A" y "B"
  Cuando abro la app por primera vez
  Entonces veo las notas "A" y "B" descargadas

Escenario: Mostrar indicador de sincronización
  Dado que la red es lenta
  Cuando abro la pantalla principal
  Entonces veo el indicador de "sincronizando"
  Y cuando termina la descarga veo la lista de notas

Escenario: Error de red al sincronizar
  Dado que el servidor no responde
  Cuando abro la pantalla principal
  Entonces veo el mensaje de error de sincronización
  Y la app queda usable con las notas que ya estaban en el dispositivo
```

---

## 2. Historias técnicas (desarrollador)

### US-T01 — Aislar la lógica de negocio (Bloque 1)
Como **desarrollador**, quiero **tener la validación en una clase JVM pura sin dependencias de Android**, para **probarla con unit tests unitarios y rápidos sin emulador**.

```gherkin
Escenario: Probar la validación de título vacío
  Dado un `NoteValidator` y una nota sin título
  Cuando ejecuto la función de validación
  Entonces el resultado es inválido
  Y el mensaje de error es "El título es obligatorio"

Escenario: Probar la validación de título de solo espacios
  Dado un `NoteValidator` y una nota con título "   "
  Cuando ejecuto la función de validación
  Entonces el resultado es inválido
  Y el mensaje de error es "El título es obligatorio"

Escenario: Probar la validación de título demasiado largo
  Dado un `NoteValidator` y una nota con título de 61 caracteres
  Cuando ejecuto la función de validación
  Entonces el resultado es inválido
  Y el mensaje de error es "El título no puede superar 60 caracteres"

Escenario: Probar la validación de contenido demasiado largo
  Dado un `NoteValidator` y una nota con contenido de 5001 caracteres
  Cuando ejecuto la función de validación
  Entonces el resultado es inválido
  Y el mensaje de error es "El contenido no puede superar 5000 caracteres"

Escenario: Probar el recorte de espacios en el título
  Dado un `NoteValidator` y una nota con título "  Comprar pan  "
  Cuando ejecuto la función de validación
  Entonces el resultado es válido
  Y el título guardado es "Comprar pan"

Escenario: Probar una nota válida
  Dado un `NoteValidator` y una nota válida con título y contenido
  Cuando ejecuto la función de validación
  Entonces el resultado es válido
```

---

### US-T02 — Probar el ViewModel con repositorio fake y mock (Bloque 2)
Como **desarrollador**, quiero **probar el ViewModel aislado de la base de datos**, para **verificar el flujo de estados sin tocar el emulador**.

```gherkin
Escenario: Exponer estado Loading → Success
  Dado un repositorio fake que devuelve dos notas
  Cuando inicializo el ViewModel
  Entonces el estado es Loading
  Y luego el estado es Success con dos notas

Escenario: Exponer estado Error
  Dado un repositorio mock que lanza una excepción
  Cuando inicializo el ViewModel
  Entonces el estado final es Error
  Y el repositorio mock verifica que se llamó a `getNotes()`
```

---

### US-T03 — Probar el DAO de Room en memoria (Bloque 3)
Como **desarrollador**, quiero **probar el DAO contra una base de datos Room en memoria**, para **verificar las consultas reales sin persistir datos en disco**.

```gherkin
Escenario: Insertar y consultar notas
  Dado una base de datos Room en memoria
  Cuando inserto la nota "Prueba"
  Y consulto todas las notas
  Entonces obtengo una lista con la nota "Prueba"

Escenario: Actualizar una nota
  Dado que existe la nota "Prueba" con contenido "A"
  Cuando actualizo su contenido a "B"
  Entonces la consulta devuelve la nota con contenido "B"

Escenario: Eliminar una nota
  Dado que existe la nota "Prueba"
  Cuando la elimino
  Entonces la consulta devuelve una lista vacía
```

---

### US-T04 — Probar flujos asíncronos (Bloque 3)
Como **desarrollador**, quiero **probar el StateFlow del ViewModel con coroutines-test y Turbine**, para **verificar el flujo de datos como eventos en tiempo**.

```gherkin
Escenario: El StateFlow emite Loading y luego Success
  Dado un ViewModel con repositorio fake
  Cuando ejecuto el test con `runTest` y Turbine
  Entonces el flujo emite `Loading`
  Y luego emite `Success`
  Y el flujo se completa
```

---

### US-T05 — Probar la UI en vivo (Bloque 4)
Como **desarrollador**, quiero **probar el flujo completo de crear una nota desde la UI**, para **verificar que el usuario real puede usar la app de principio a fin**.

```gherkin
Escenario: Crear una nota desde Compose/Espresso
  Dado que la app está abierta en el emulador
  Cuando toco "Nueva nota"
  Y escribo el título "Test UI"
  Y guardo la nota
  Entonces veo "Test UI" en la lista

Escenario: Probar el flujo de navegación lista → detalle → lista
  Dado que existe la nota "Viaje"
  Cuando toco la nota "Viaje"
  Entonces se abre la pantalla de detalle con el título "Viaje"
  Cuando edito el contenido y vuelvo atrás
  Entonces la lista refleja el contenido actualizado
```

---

### US-T06 — Inyección de Hilt en tests (Bloque 5)
Como **desarrollador**, quiero **usar Hilt en los tests instrumentados con una base de datos en memoria**, para **probar la integración real de la app sin datos sucios**.

```gherkin
Escenario: Test instrumentado con Hilt y Room en memoria
  Dado un test con `@HiltAndroidTest`
  Cuando se inyecta el repositorio con una BD Room en memoria
  Entonces creo y consulto una nota en el flujo completo
  Y al terminar, la base de datos se cierra sin contaminar otras pruebas
```

---

### US-T07 — Cobertura de código (Bloque 5)
Como **desarrollador**, quiero **generar un reporte de cobertura con JaCoCo**, para **saber qué porcentaje del código está cubierto por tests**.

```gherkin
Escenario: Generar el reporte de cobertura
  Dado un proyecto con JaCoCo configurado
  Cuando ejecuto la tarea de cobertura de Gradle
  Entonces se genera el reporte con el porcentaje de cobertura por archivo
```

---

### US-T08 — CI/CD automático (Bloque 5)
Como **desarrollador**, quiero **que los tests corran solos en cada push**, para **detectar regresiones sin depender de que alguien los ejecute a mano**.

```gherkin
Escenario: Pipeline de GitHub Actions
  Dado un repositorio con el pipeline configurado
  Cuando hago push de un commit
  Entonces GitHub Actions ejecuta `./gradlew test`
  Y el build pasa si todos los tests pasan
  Y el build falla si algún test falla
```

### US-T09 — Probar la capa de red con MockWebServer (Bloque 3)
Como **desarrollador**, quiero **probar el repositorio remoto con MockWebServer**, para **verificar las llamadas HTTP y el parseo del JSON sin depender de internet ni de un servidor real**.

```gherkin
Escenario: Obtener notas del servidor con respuesta OK
  Dado un MockWebServer que responde 200 con un JSON de dos notas
  Cuando ejecuto `fetchNotes()`
  Entonces obtengo una lista con dos notas con su título, contenido y etiqueta
  Y el MockWebServer verifica que se llamó a `GET /notes`

Escenario: Manejar una respuesta de error del servidor
  Dado un MockWebServer que responde 500
  Cuando ejecuto `fetchNotes()`
  Entonces el resultado es un error de red

Escenario: Manejar un cuerpo JSON inválido
  Dado un MockWebServer que responde 200 con un cuerpo no JSON
  Cuando ejecuto `fetchNotes()`
  Entonces el resultado es un error de parseo

Escenario: Parsear una nota sin etiqueta
  Dado un MockWebServer que responde 200 con una nota sin campo `tag`
  Cuando ejecuto `fetchNotes()`
  Entonces obtengo la nota con etiqueta nula
```

---

## 3. Criterios de aceptación generales del proyecto

| ID | Criterio |
|---|---|
| CA-01 | Todos los escenarios de las US funcionales (US-01 a US-11) pasan cuando la app se usa en el emulador. |
| CA-02 | Todos los escenarios de las US técnicas (US-T01 a US-T09) pasan con `./gradlew test` y `./gradlew connectedCheck`. |
| CA-03 | El proyecto usa Kotlin, arquitectura MVVM, repositorio, `StateFlow<NotesUiState>` y Hilt. |
| CA-04 | La validación vive en una clase JVM pura (`NoteValidator`) y todo lo de Android está excluido de ella. |
| CA-05 | El pipeline de CI (US-T08) corre automáticamente y genera el reporte de JaCoCo (US-T07). |
| CA-06 | La búsqueda (US-09) cubre título, contenido y etiqueta, y se filtra conforme el usuario escribe. |
| CA-07 | Las etiquetas (US-10) son de una por nota, opcionales, editables y filtrables. |
| CA-08 | La capa de red (US-11 / US-T09) usa un backend simulado con MockWebServer y no necesita internet en los tests. |

## 4. Entidades de datos

| Campo | Tipo | Regla |
|---|---|---|
| `id` | Long (PK, autogenerada) | Único |
| `title` | String | Obligatorio, no vacío (US-06), máximo 60 caracteres, se recorta (sin espacios iniciales/finales) al guardar |
| `content` | String | Opcional, pero no puede estar vacío junto con el título (US-07), máximo 5000 caracteres, se recorta al guardar |
| `tag` | String? | Opcional, una sola etiqueta por nota (US-10) |
| `updatedAt` | Long (timestamp) | Se actualiza al crear/modificar |

## 5. Restricciones y supuestos

- R-01: App en **Kotlin**. Sin autenticación; la red es **de solo lectura** (one-way fetch) y se simula con MockWebServer para ampliar el Bloque 3.
- R-02: Min SDK 24+, target SDK actual estable.
- R-03: Sin backend real; el servidor se simula con MockWebServer en los tests (US-T09) y en el modo demostración.
- R-04: Los escenarios Gherkin de US-T05 se escriben con Compose UI Test o Espresso según la UI que se elija.
- R-05: La búsqueda (US-09) usa una consulta `LIKE` sobre título, contenido y etiqueta, sin dependencias de red.
- R-06: La etiqueta (US-10) se almacena como una sola columna nullable en la tabla de notas; las etiquetas existentes se obtienen de valores distintos ya guardados.
- R-07: Los límites de longitud (título ≤ 60, contenido ≤ 5000) y el recorte de espacios se aplican en `NoteValidator` antes de validar y guardar (US-06, US-07).