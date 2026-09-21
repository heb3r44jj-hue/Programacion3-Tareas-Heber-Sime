public class ValidadorEstudiantes {

    static String validarEstudiante(String nombre, String matricula, String correo) {
        if (nombre.trim().isEmpty() || nombre.trim().split(" ").length < 2) {
            return "Error: el nombre debe tener al menos 2 palabras";
        }
        if (!(matricula.startsWith("202") && matricula.length() == 9)) {
            return "Error: la matricula debe empezar con 202 y tener 9 caracteres";
        }
        if (!(correo.contains("@") && correo.endsWith(".edu"))) {
            return "Error: el correo debe contener @ y terminar en .edu";
        }
        return "Datos validos";
    }

    public static void ejecutar() {
        System.out.println("\n===== VALIDADOR DE ESTUDIANTES =====");
        System.out.println(validarEstudiante("Pedro Gomez", "202-12345", "pedro@uni.edu"));
        System.out.println(validarEstudiante("Ana", "202-95678", "ana@uni.edu"));
        System.out.println(validarEstudiante("Luis Diaz", "202-99999", "luis@uni.edu"));
    }
}