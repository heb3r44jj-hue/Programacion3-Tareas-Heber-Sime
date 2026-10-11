# Tarea 4 – Hola Mundo en JavaFX

Estudiante: Heber José Sime Rodríguez
Matrícula: 1000-5945
Asignatura: Programación 3

## Descripción
Programa básico en JavaFX que abre una ventana con un Label que muestra
"Hola Mundo" y un Button que, al pulsarlo, cambia el texto a "Hola Heber".
Los elementos están organizados en un VBox.

## Requisitos
- JDK 25
- JavaFX SDK 25.0.1 sin Maven ni Gradle

## Compilación y ejecución
`launch.json` indica la clase principal y pasa a la máquina virtual de Java
las opciones necesarias para cargar JavaFX:

    "mainClass": "HolaMundoFX",
    "vmArgs": "--module-path C:/javafx-sdk-25.0.1/lib --add-modules javafx.controls"

`settings.json` agrega los archivos .jar de JavaFX a la extensión de Java,
para que el editor reconozca las clases de JavaFX.
