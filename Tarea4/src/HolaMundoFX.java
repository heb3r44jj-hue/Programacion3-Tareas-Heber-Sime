import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;




public class HolaMundoFX extends Application {

    @Override 

    public void start(Stage ventana ){
        
        Label etiqueta = new Label("Hola Mundo");

        Button boton = new Button("Saludar");

        boton.setOnAction(evento -> {
            if (etiqueta.getText().equals("Hola Mundo")) {
                etiqueta.setText("hola Heber");
            } else {
                etiqueta.setText("Hola Mundo");
            }

        });

        VBox contenedor = new VBox(10);
        contenedor.setAlignment(Pos.CENTER);
        contenedor.getChildren().add(etiqueta);
        contenedor.getChildren().add(boton);

        Scene escena = new Scene(contenedor, 400, 400);

        ventana.setTitle("Hola Mundo FX");
        ventana.setScene(escena);
        ventana.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
