import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Locale;

public class GastosMensuales extends Application {

    
    private double presupuesto = 0;   
    private double totalGastado = 0;   

    
    private TextField txtPresupuesto;
    private TextField txtDescripcion;
    private TextField txtMonto;
    private ComboBox<String> cmbCategoria;
    private VBox listaGastos;
    private Label lblTotal;
    private Label lblAviso;
    private Label lblError;

    @Override
    public void start(Stage ventana) {

       
        Label lblTitulo = new Label("Control de Gastos del Mes");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1a237e;");

       
        Label lblPresupuesto = new Label("Presupuesto mensual: ");

        txtPresupuesto = new TextField();
        txtPresupuesto.setPromptText("Ej: 25000");
        txtPresupuesto.setPrefWidth(150);

        Button btnFijar = new Button("Fijar");
        btnFijar.setStyle("-fx-background-color: #1565c0; -fx-text-fill: white; "+ "-fx-font-weight: bold; -fx-cursor: hand;");
        btnFijar.setOnAction(e -> fijarPresupuesto());
        txtPresupuesto.setOnAction(e -> fijarPresupuesto()); 

        HBox filaPresupuesto = new HBox(10, lblPresupuesto, txtPresupuesto, btnFijar);
        filaPresupuesto.setAlignment(Pos.CENTER_LEFT);

        txtDescripcion = new TextField();
        txtDescripcion.setPromptText("Descripcion");
        txtDescripcion.setPrefWidth(180);

        txtMonto = new TextField();
        txtMonto.setPromptText("Monto");
        txtMonto.setPrefWidth(100);

        cmbCategoria = new ComboBox<>();
        cmbCategoria.getItems().addAll("Comida", "Transporte", "Ocio","Salud", "Educacion,", "Casa");
        cmbCategoria.setValue("Comida");

        Button btnAgregar = new Button("Agregar gasto");
        btnAgregar.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white; "+ "-fx-font-weight: bold; -fx-cursor: hand;");
        btnAgregar.setOnAction(e -> agregarGasto());
        txtMonto.setOnAction(e -> agregarGasto()); 

        HBox filaGasto = new HBox(10, txtDescripcion, txtMonto, cmbCategoria, btnAgregar);
        filaGasto.setAlignment(Pos.CENTER_LEFT);

        lblError = new Label("");
        lblError.setStyle("-fx-text-fill: #c62828; -fx-font-weight: bold;");

        Label lblLista = new Label("Gastos registrados:");
        lblLista.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        listaGastos = new VBox(6);
        listaGastos.setPadding(new Insets(8));

        ScrollPane scroll = new ScrollPane(listaGastos);
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(250);
        scroll.setStyle("-fx-background: #fafafa; -fx-border-color: #cfd8dc;");
        VBox.setVgrow(scroll, Priority.ALWAYS);

       
        lblTotal = new Label();
        lblTotal.setMaxWidth(Double.MAX_VALUE);

        lblAviso = new Label("");
        lblAviso.setStyle("-fx-text-fill: #c62828; -fx-font-size: 16px; -fx-font-weight: bold;");

        
        VBox raiz = new VBox(12, lblTitulo, filaPresupuesto, filaGasto, lblError,lblLista, scroll, lblTotal, lblAviso);
        raiz.setPadding(new Insets(20));
        raiz.setStyle("-fx-background-color: #eceff1; -fx-font-family: 'Segoe UI';");

        actualizarTotal(); 

        Scene escena = new Scene(raiz, 650, 580);
        ventana.setTitle("Gastos del Mes");
        ventana.setScene(escena);
        ventana.show();
    }

    
    private void fijarPresupuesto() {
        double valor = convertirMonto(txtPresupuesto.getText());

        if (valor == -1) {
            lblError.setText("El presupuesto debe ser un numero positivo");
            txtPresupuesto.requestFocus();
            return;
        }

        presupuesto = valor;
        lblError.setText("");
        actualizarTotal();
    }

   
    private void agregarGasto() {
        String descripcion = txtDescripcion.getText().trim();
        String categoria = cmbCategoria.getValue();
        double monto = convertirMonto(txtMonto.getText());

        if (descripcion.isEmpty()) {
            lblError.setText("Escriba una descripcion para el gasto");
            txtDescripcion.requestFocus();
            return;
        }

        if (monto == -1) {
            lblError.setText("El monto debe ser un numero positivo");
            txtMonto.requestFocus();
            return;
        }

        HBox fila = crearFila(descripcion, categoria, monto);
        listaGastos.getChildren().add(fila);

        totalGastado = totalGastado + monto;
        actualizarTotal();

        lblError.setText("");
        txtDescripcion.clear();
        txtMonto.clear();
        txtDescripcion.requestFocus();
    }

   
    private HBox crearFila(String descripcion, String categoria, double monto) {

        Label lblCategoria = new Label(categoria);
        lblCategoria.setMinWidth(90);
        lblCategoria.setAlignment(Pos.CENTER);
        lblCategoria.setStyle("-fx-background-color: #e3f2fd; -fx-text-fill: #0d47a1; "+ "-fx-padding: 3 8 3 8; -fx-background-radius: 10; -fx-font-size: 11px;");

        Label lblTexto = new Label(descripcion + " \u2014 " + formatoDinero(monto));
        lblTexto.setStyle("-fx-font-size: 14px;");

        Region espacio = new Region();               
        HBox.setHgrow(espacio, Priority.ALWAYS);

        Button btnEliminar = new Button("X");
        btnEliminar.setStyle("-fx-background-color: #e53935; -fx-text-fill: white; "+ "-fx-font-weight: bold; -fx-cursor: hand;");

        HBox fila = new HBox(10, lblCategoria, lblTexto, espacio, btnEliminar);
        fila.setAlignment(Pos.CENTER_LEFT);
        fila.setPadding(new Insets(6, 10, 6, 10));
        fila.setStyle("-fx-background-color: white; -fx-border-color: #cfd8dc; "+ "-fx-border-radius: 5; -fx-background-radius: 5;");

        btnEliminar.setOnAction(e -> {
            listaGastos.getChildren().remove(fila);
            totalGastado = totalGastado - monto;
            
            if (listaGastos.getChildren().isEmpty()) {
                totalGastado = 0;
            }

            lblError.setText("");
            actualizarTotal();
        });

        return fila;
    }

   
    private void actualizarTotal() {
        String estiloBase = "-fx-font-size: 15px; -fx-font-weight: bold; "+ "-fx-padding: 10; -fx-background-radius: 6; ";

       
        if (presupuesto == 0) {
            lblTotal.setText("Total gastado: " + formatoDinero(totalGastado) + "\nFije un presupuesto para ver lo disponible.");
            lblTotal.setStyle(estiloBase + "-fx-background-color: #b0bec5; -fx-text-fill: #263238;");
            lblAviso.setText("");
            return;
        }

        double disponible = presupuesto - totalGastado;
        double porcentaje = (totalGastado / presupuesto) * 100;

        lblTotal.setText("Total gastado: " + formatoDinero(totalGastado) + "Disponible: " + formatoDinero(disponible)+ "\nUsado: " + String.format(Locale.US, "%.1f", porcentaje)+ "% de " + formatoDinero(presupuesto));

        if (porcentaje < 70) {

            lblTotal.setStyle(estiloBase + "-fx-background-color: #2e7d32; -fx-text-fill: white;");
            lblAviso.setText("");
        } else if (porcentaje <= 100) {
            lblTotal.setStyle(estiloBase + "-fx-background-color: #fbc02d; -fx-text-fill: black;");
            lblAviso.setText("");
        } else {
            lblTotal.setStyle(estiloBase + "-fx-background-color: #c62828; -fx-text-fill: white;");
            lblAviso.setText("\u00a1Presupuesto excedido!");
        }
    }

    
    private double convertirMonto(String texto) {
        texto = texto.trim().replace(",", ""); 
        double numero;

        try {
            numero = Double.parseDouble(texto);
        } catch (NumberFormatException ex) {
            return -1;
        }

        if (numero > 0 && !Double.isInfinite(numero)) {
            return numero;
        }
        return -1;
    }

    private String formatoDinero(double cantidad) {
        return "RD$ " + String.format(Locale.US, "%,.2f", cantidad);
    }

    public static void main(String[] args) {
        launch(args);
    }
}