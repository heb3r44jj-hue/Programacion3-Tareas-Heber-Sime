import java.util.Scanner;

public class Main {

    static String[] productos = {"Laptop", "Mouse", "Teclado", "Monitor", "Audifonos"};
    static double[] precios = {45000.0, 850.0, 1200.0, 12000.0, 2500.0};
    static int[] cantidades = {10, 50, 30, 8, 25};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println();
            System.out.println("===== MENU PRINCIPAL =====");
            System.out.println("1. Gestion de inventario");
            System.out.println("2. Matriz de calificaciones");
            System.out.println("3. Busqueda de producto");
            System.out.println("4. Procesador CSV");
            System.out.println("5. Validador de estudiantes");
            System.out.println("6. Calculadora de fechas");
            System.out.println("0. Salir");
            System.out.print("Elige una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1: Inventario.ejecutar(); 
                break;
                case 2: Calificaciones.ejecutar(); 
                break;
                case 3: BuscadorProductos.ejecutar(sc); 
                break;
                case 4: ProcesadorCSV.ejecutar(); 
                break;
                case 5: ValidadorEstudiantes.ejecutar(); 
                break;
                case 6: CalculadoraFechas.ejecutar(sc);
                break;
                case 0: System.out.println("Saliendo del programa");
                 break;
                default: System.out.println("Opcion no valida");
            }
        } while (opcion != 0);

        sc.close();
    }
}