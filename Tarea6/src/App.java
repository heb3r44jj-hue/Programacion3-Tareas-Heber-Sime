import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class App extends Application
{
    private GestorEstudiantes gestor = new GestorEstudiantes();

    private TextField txtMatricula = new TextField();
    private TextField txtNombre = new TextField();
    private TextField txtCarrera = new TextField();
    private TextField txtCalificacion = new TextField();
    private Label lblModo = new Label("Nuevo registro");

    private TextField txtBuscar = new TextField();
    private TableView<Estudiante> tabla = new TableView<>();
    private ObservableList<Estudiante> datosTabla = FXCollections.observableArrayList();
    private Label lblResultados = new Label();

    private Label lblTotal = new Label();
    private Label lblPromedio = new Label();
    private Label lblAprobados = new Label();
    private Label lblReprobados = new Label();

    private Estudiante seleccionado = null;

    private Stage ventana;

    @Override
    public void start(Stage stage)
    {
        ventana = stage;

        BorderPane raiz = new BorderPane();
        raiz.setPadding(new Insets(15));
        raiz.setStyle("-fx-background-color: #f4f6f9; -fx-font-size: 13px;");

        raiz.setTop(crearEncabezado());
        raiz.setLeft(crearFormulario());
        raiz.setCenter(crearPanelTabla());
        raiz.setBottom(crearPanelInferior());

        try
        {
            int lineasConError = gestor.cargarArchivo();
            if (lineasConError > 0)
            {
                mostrarAdvertencia("Datos cargados con observaciones","Se ignoraron " + lineasConError + " lineas con error del archivo estudiantes.txt.");
            }
        }
        catch (IOException ex)
        {
            mostrarError("Error al cargar los datos", ex.getMessage());
        }

        refrescarTodo();

        stage.setOnCloseRequest(e -> {
            try
            {
                gestor.guardarArchivo();
            }
            catch (IOException ex)
            {
                mostrarError("Error al guardar", ex.getMessage());
            }
        });

        Scene escena = new Scene(raiz, 1050, 620);
        stage.setTitle("UCNE - Sistema de Gestion Academica de Estudiantes");
        stage.setScene(escena);
        stage.setMinWidth(900);
        stage.setMinHeight(550);
        stage.show();
    }

    private VBox crearEncabezado()
    {
        Label titulo = new Label("Universidad Catolica Nordestana (UCNE)");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #1f3a68;");

        Label subtitulo = new Label("Sistema de Gestion Academica de Estudiantes");
        subtitulo.setStyle("-fx-font-size: 14px; -fx-text-fill: #555555;");

        VBox caja = new VBox(2, titulo, subtitulo);
        caja.setPadding(new Insets(0, 0, 12, 0));
        return caja;
    }

    private VBox crearFormulario()
    {
        Label titulo = new Label("Datos del estudiante");
        titulo.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        lblModo.setStyle("-fx-text-fill: #1f7a3a; -fx-font-weight: bold;");

        txtMatricula.setPromptText("Ej. 1000-5945");
        txtNombre.setPromptText("Nombre y apellidos");
        txtCarrera.setPromptText("Ej. Ingenieria en Sistemas");
        txtCalificacion.setPromptText("0 a 100");

        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(10);
        grid.add(new Label("Matricula:"), 0, 0);
        grid.add(txtMatricula, 1, 0);
        grid.add(new Label("Nombre completo:"), 0, 1);
        grid.add(txtNombre, 1, 1);
        grid.add(new Label("Carrera:"), 0, 2);
        grid.add(txtCarrera, 1, 2);
        grid.add(new Label("Calificacion final:"), 0, 3);
        grid.add(txtCalificacion, 1, 3);
        txtNombre.setPrefWidth(200);

        Button btnRegistrar = crearBoton("Registrar", "#1f7a3a");
        Button btnActualizar = crearBoton("Actualizar", "#1f3a68");
        Button btnEliminar = crearBoton("Eliminar", "#b02a2a");
        Button btnLimpiar = crearBoton("Limpiar", "#6c757d");

        btnRegistrar.setOnAction(e -> registrar());
        btnActualizar.setOnAction(e -> actualizar());
        btnEliminar.setOnAction(e -> eliminar());
        btnLimpiar.setOnAction(e -> limpiarFormulario());

        HBox fila1 = new HBox(8, btnRegistrar, btnActualizar);
        HBox fila2 = new HBox(8, btnEliminar, btnLimpiar);

        Label ayuda = new Label("Seleccione un estudiante en la tabla para\nmodificarlo o eliminarlo.");
        ayuda.setStyle("-fx-text-fill: #777777; -fx-font-size: 11px;");

        VBox panel = new VBox(12, titulo, lblModo, grid, fila1, fila2, ayuda);
        panel.setPadding(new Insets(15));
        panel.setPrefWidth(340);
        panel.setStyle("-fx-background-color: white; -fx-border-color: #d0d7e2; -fx-border-radius: 6; -fx-background-radius: 6;");

        BorderPane.setMargin(panel, new Insets(0, 12, 0, 0));
        return panel;
    }

    private VBox crearPanelTabla()
    {
        txtBuscar.setPromptText("Buscar por matricula o nombre...");
        HBox.setHgrow(txtBuscar, Priority.ALWAYS);

        Button btnBuscar = crearBoton("Buscar", "#1f3a68");
        Button btnTodos = crearBoton("Mostrar todos", "#6c757d");

        btnBuscar.setOnAction(e -> buscar());
        txtBuscar.setOnAction(e -> buscar());
        btnTodos.setOnAction(e -> {
            txtBuscar.clear();
            mostrarEnTabla(gestor.getLista());
        });

        HBox barraBusqueda = new HBox(8, txtBuscar, btnBuscar, btnTodos);
        barraBusqueda.setAlignment(Pos.CENTER_LEFT);

        TableColumn<Estudiante, String> colMatricula = new TableColumn<>("Matricula");
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colMatricula.setPrefWidth(110);

        TableColumn<Estudiante, String> colNombre = new TableColumn<>("Nombre completo");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setPrefWidth(200);

        TableColumn<Estudiante, String> colCarrera = new TableColumn<>("Carrera");
        colCarrera.setCellValueFactory(new PropertyValueFactory<>("carrera"));
        colCarrera.setPrefWidth(180);

        TableColumn<Estudiante, Double> colCalificacion = new TableColumn<>("Calificacion");
        colCalificacion.setCellValueFactory(new PropertyValueFactory<>("calificacion"));
        colCalificacion.setPrefWidth(90);

        TableColumn<Estudiante, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colEstado.setPrefWidth(90);

        tabla.getColumns().add(colMatricula);
        tabla.getColumns().add(colNombre);
        tabla.getColumns().add(colCarrera);
        tabla.getColumns().add(colCalificacion);
        tabla.getColumns().add(colEstado);
        tabla.setItems(datosTabla);
        tabla.setPlaceholder(new Label("No hay estudiantes para mostrar."));
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(tabla, Priority.ALWAYS);

        tabla.getSelectionModel().selectedItemProperty().addListener((obs, anterior, nuevo) -> {
            if (nuevo != null)
            {
                cargarEnFormulario(nuevo);
            }
        });

        lblResultados.setStyle("-fx-text-fill: #555555;");

        VBox panel = new VBox(10, barraBusqueda, tabla, lblResultados);
        return panel;
    }

    private HBox crearPanelInferior()
    {
        Label tituloEst = new Label("Estadisticas:");
        tituloEst.setStyle("-fx-font-weight: bold;");

        HBox estadisticas = new HBox(18, tituloEst, lblTotal, lblPromedio, lblAprobados, lblReprobados);
        estadisticas.setAlignment(Pos.CENTER_LEFT);
        lblAprobados.setStyle("-fx-text-fill: #1f7a3a; -fx-font-weight: bold;");
        lblReprobados.setStyle("-fx-text-fill: #b02a2a; -fx-font-weight: bold;");

        Button btnRespaldo = crearBoton("Crear copia de seguridad", "#5a3e8c");
        Button btnRestaurar = crearBoton("Restaurar copia", "#5a3e8c");
        btnRespaldo.setOnAction(e -> crearRespaldo());
        btnRestaurar.setOnAction(e -> restaurarRespaldo());

        HBox espacio = new HBox();
        HBox.setHgrow(espacio, Priority.ALWAYS);

        HBox panel = new HBox(10, estadisticas, espacio, btnRespaldo, btnRestaurar);
        panel.setAlignment(Pos.CENTER_LEFT);
        panel.setPadding(new Insets(12));
        panel.setStyle("-fx-background-color: white; -fx-border-color: #d0d7e2; -fx-border-radius: 6; -fx-background-radius: 6;");
        BorderPane.setMargin(panel, new Insets(12, 0, 0, 0));
        return panel;
    }

    private Button crearBoton(String texto, String color)
    {
        Button b = new Button(texto);
        b.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-weight: bold; "
                + "-fx-background-radius: 4; -fx-padding: 6 14 6 14; -fx-cursor: hand;");
        return b;
    }

    private void registrar()
    {
        if (seleccionado != null)
        {
            mostrarAdvertencia("Estudiante seleccionado","Hay un estudiante seleccionado. Use Actualizar para modificarlo " + "o presione Limpiar para registrar uno nuevo.");
            return;
        }

        String matricula = txtMatricula.getText().trim();
        String nombre = txtNombre.getText().trim();
        String carrera = txtCarrera.getText().trim();
        String calTexto = txtCalificacion.getText().trim();

        String error = gestor.validar(matricula, nombre, carrera, calTexto, null);
        if (error != null)
        {
            mostrarError("No se pudo registrar", error);
            return;
        }

        try
        {
            Estudiante nuevo = new Estudiante(nombre, matricula, carrera, gestor.convertirCalificacion(calTexto));
            gestor.registrar(nuevo);
            mostrarInfo("Registro exitoso", "El estudiante " + nombre + " fue registrado correctamente.");
            limpiarFormulario();
            refrescarTodo();
        }
        catch (IOException ex)
        {
            mostrarError("Error al guardar", ex.getMessage());
        }
    }

    private void actualizar()
    {
        if (seleccionado == null)
        {
            mostrarAdvertencia("Ningun estudiante seleccionado",
                    "Seleccione en la tabla el estudiante que desea modificar.");
            return;
        }

        String matricula = txtMatricula.getText().trim();
        String nombre = txtNombre.getText().trim();
        String carrera = txtCarrera.getText().trim();
        String calTexto = txtCalificacion.getText().trim();

        String error = gestor.validar(matricula, nombre, carrera, calTexto, seleccionado);
        if (error != null)
        {
            mostrarError("No se pudo actualizar", error);
            return;
        }

        try
        {
            gestor.modificar(seleccionado, matricula, nombre, carrera, gestor.convertirCalificacion(calTexto));
            mostrarInfo("Actualizacion exitosa", "Los datos de " + nombre + " fueron actualizados.");
            limpiarFormulario();
            refrescarTodo();
        }
        catch (IOException ex)
        {
            mostrarError("Error al guardar", ex.getMessage());
        }
    }

    private void eliminar()
    {
        if (seleccionado == null)
        {
            mostrarAdvertencia("Ningun estudiante seleccionado","Seleccione en la tabla el estudiante que desea eliminar.");
            return;
        }

        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
        confirmar.initOwner(ventana);
        confirmar.setTitle("Confirmar eliminacion");
        confirmar.setHeaderText("Desea eliminar este estudiante?");
        confirmar.setContentText(seleccionado.getMatricula() + " - " + seleccionado.getNombre()
                + "\n\nEsta operacion no se puede deshacer.");

        Optional<ButtonType> respuesta = confirmar.showAndWait();
        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK)
        {
            try
            {
                gestor.eliminar(seleccionado);
                mostrarInfo("Eliminacion exitosa", "El estudiante fue eliminado.");
                limpiarFormulario();
                refrescarTodo();
            }
            catch (IOException ex)
            {
                mostrarError("Error al guardar", ex.getMessage());
            }
        }
    }

    private void buscar()
    {
        String texto = txtBuscar.getText().trim();
        if (texto.isEmpty())
        {
            mostrarAdvertencia("Busqueda vacia", "Escriba una matricula o un nombre para buscar");
            return;
        }

        ArrayList<Estudiante> resultados = gestor.buscar(texto);
        mostrarEnTabla(resultados);

        if (resultados.size() == 0)
        {
            mostrarInfo("Sin resultados", "No se encontro ningun estudiante con " + texto + ".");
        }
        else
        {
            lblResultados.setText("Resultados de la busqueda " + texto + ": " + resultados.size()+ " estudiante(s)");
        }
    }

    private void crearRespaldo()
    {
        if (gestor.getTotal() == 0)
        {
            mostrarAdvertencia("Sin datos", "No hay estudiantes registrados para respaldar.");
            return;
        }

        FileChooser selector = new FileChooser();
        selector.setTitle("Guardar copia de seguridad");
        selector.setInitialFileName("respaldo_estudiantes.dat");
        selector.getExtensionFilters().add(new FileChooser.ExtensionFilter("Copia de seguridad (*.dat)", "*.dat"));
        File archivo = selector.showSaveDialog(ventana);

        if (archivo == null)
        {
            return;
        }

        try
        {
            gestor.crearRespaldo(archivo);
            mostrarInfo("Copia de seguridad creada", "Se respaldaron " + gestor.getTotal()
                    + " estudiante(s) en:\n" + archivo.getAbsolutePath());
        }
        catch (IOException ex)
        {
            mostrarError("Error al crear la copia de seguridad", ex.getMessage());
        }
    }

    private void restaurarRespaldo()
    {
        FileChooser selector = new FileChooser();
        selector.setTitle("Seleccionar copia de seguridad");
        selector.getExtensionFilters().add(new FileChooser.ExtensionFilter("Copia de seguridad (*.dat)", "*.dat"));
        File archivo = selector.showOpenDialog(ventana);

        if (archivo == null)
        {
            return;
        }

        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
        confirmar.initOwner(ventana);
        confirmar.setTitle("Confirmar restauracion");
        confirmar.setHeaderText("Restaurar la copia de seguridad?");
        confirmar.setContentText("Los " + gestor.getTotal() + " registro(s) actuales seran reemplazados "+ "por los datos de la copia seleccionada");

        Optional<ButtonType> respuesta = confirmar.showAndWait();
        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK)
        {
            try
            {
                int cantidad = gestor.restaurarRespaldo(archivo);
                limpiarFormulario();
                refrescarTodo();
                mostrarInfo("Restauracion exitosa", "Se restauraron " + cantidad + " estudiante(s).");
            }
            catch (IOException ex)
            {
                mostrarError("Error al restaurar","No se pudo leer la copia de seguridad. Verifique que el archivo sea correcto.\n\nDetalle: "+ ex.getMessage());
            }
            catch (ClassNotFoundException ex)
            {
                mostrarError("Error al restaurar", "El archivo no contiene estudiantes validos: " + ex.getMessage());
            }
        }
    }

    private void cargarEnFormulario(Estudiante e)
    {
        seleccionado = e;
        txtMatricula.setText(e.getMatricula());
        txtNombre.setText(e.getNombre());
        txtCarrera.setText(e.getCarrera());
        txtCalificacion.setText(String.valueOf(e.getCalificacion()));
        lblModo.setText("Editando: " + e.getMatricula());
        lblModo.setStyle("-fx-text-fill: #1f3a68; -fx-font-weight: bold;");
    }

    private void limpiarFormulario()
    {
        seleccionado = null;
        txtMatricula.clear();
        txtNombre.clear();
        txtCarrera.clear();
        txtCalificacion.clear();
        tabla.getSelectionModel().clearSelection();
        lblModo.setText("Nuevo registro");
        lblModo.setStyle("-fx-text-fill: #1f7a3a; -fx-font-weight: bold;");
        txtMatricula.requestFocus();
    }

    private void mostrarEnTabla(ArrayList<Estudiante> estudiantes)
    {
        datosTabla.clear();
        datosTabla.addAll(estudiantes);
        tabla.refresh();
        lblResultados.setText("Mostrando " + estudiantes.size() + " de " + gestor.getTotal() + " estudiante(s)");
    }

    private void refrescarTodo()
    {
        txtBuscar.clear();
        mostrarEnTabla(gestor.getLista());
        actualizarEstadisticas();
    }

    private void actualizarEstadisticas()
    {
        lblTotal.setText("Total: " + gestor.getTotal());
        lblPromedio.setText("Promedio general: " + String.format("%.2f", gestor.getPromedio()));
        lblAprobados.setText("Aprobados: " + gestor.getAprobados());
        lblReprobados.setText("Reprobados: " + gestor.getReprobados());
    }

    private void mostrarError(String titulo, String mensaje)
    {
        mostrarAlerta(Alert.AlertType.ERROR, titulo, mensaje);
    }

    private void mostrarAdvertencia(String titulo, String mensaje)
    {
        mostrarAlerta(Alert.AlertType.WARNING, titulo, mensaje);
    }

    private void mostrarInfo(String titulo, String mensaje)
    {
        mostrarAlerta(Alert.AlertType.INFORMATION, titulo, mensaje);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje)
    {
        Alert alerta = new Alert(tipo);
        if (ventana != null && ventana.isShowing())
        {
            alerta.initOwner(ventana);
        }
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}