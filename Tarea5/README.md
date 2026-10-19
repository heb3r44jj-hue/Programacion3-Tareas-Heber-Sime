# Tarea 5 – Control de Gastos del Mes en JavaFX

Estudiante: Heber José Sime Rodríguez
Matrícula: 1000-5945
Asignatura: Programación 3

## Descripción
Aplicación en JavaFX para llevar los gastos de un mes. Permite fijar un
presupuesto mensual, agregar gastos con descripción, monto y categoría, y
eliminar cada gasto con el botón X de su fila. Un Label muestra el total
gastado y lo disponible, y cambia de color según el porcentaje del
presupuesto usado:

- Menos del 70 %: verde
- Del 70 % al 100 %: amarillo
- Más del 100 %: rojo, con el mensaje "¡Presupuesto excedido!"

El total se lleva en una variable double de la clase y el método
actualizarTotal() recalcula el texto y el color cada vez que se agrega o
elimina un gasto. El monto se valida: si no es un número positivo se muestra
un mensaje de error y el gasto no se agrega. Se aplican estilos con setStyle
al título, los botones, las filas y el Label del total.

Extra: un ComboBox permite elegir la categoría (Comida, Transporte, Ocio,
Servicios, Salud, Educación, Otros) y cada fila muestra su categoría.

## Requisitos
- JDK 25
- JavaFX SDK 25.0.1 sin Maven ni Gradle

## Compilación y ejecución
`tasks.json` compila el programa en la carpeta `bin` y luego lo ejecuta con
las opciones necesarias para cargar JavaFX (Ctrl+Shift+B en VS Code):

    javac --module-path C:/javafx-sdk-25.0.1/lib --add-modules javafx.controls -d bin src/GastosMensuales.java
    java --module-path C:/javafx-sdk-25.0.1/lib --add-modules javafx.controls -cp bin GastosMensuales

`settings.json` agrega los archivos .jar de JavaFX a la extensión de Java,
para que el editor reconozca las clases de JavaFX.