import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            
            System.out.println("");
            System.out.println("╔══════════════════════════════════╗");
            System.out.println("║     MENU DE EJERCICIOS           ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  1. Calculadora básica           ║");
            System.out.println("║  2. Par o impar                  ║");
            System.out.println("║  3. Tabla de multiplicar         ║");
            System.out.println("║  4. Números del 1 al 100         ║");
            System.out.println("║  5. Clase Persona                ║");
            System.out.println("║  6. Clase Rectángulo             ║");
            System.out.println("║  7. Clase Empleado               ║");
            System.out.println("║  8. Clase Figura                 ║");
            System.out.println("║  9. Contador de vocales          ║");
            System.out.println("║ 10. Calculadora de notas         ║");
            System.out.println("║  0. Salir                        ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("Elige una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    Calculadora(sc);
                    break;
                case 2:
                    ParImpar(sc);
                    break;
                case 3:
                    TablaMultiplicar(sc);
                    break;
                case 4:
                    Numeros1al100();
                    break;
                case 5:
                    ClasePersona(sc);
                    break;
                case 6:
                    ClaseRectangulo(sc);
                    break;
                case 7:
                    ClaseEmpleado(sc);
                    break;
                case 8:
                    ClaseFigura(sc);
                    break;
                case 9:
                    ContadorVocales(sc);
                    break;
                case 10:
                    CalculadoraNotas(sc);
                    break;
                case 0:
                    System.out.println("Saliendo del programa");
                    break;
                default:
                    System.out.println("Opción inválida intenta de nuevo.");
            }  
        }
        sc.close();

    } 

    public static void Calculadora(Scanner sc) {
        System.out.println("--- Calculadora básica ---");
        System.out.print("Ingresa el primer número: ");
        double num1 = sc.nextDouble();
        System.out.print("Ingresa el segundo número: ");
        double num2 = sc.nextDouble();
        System.out.print("Ingresa la operación (+, -, *, /): ");
        String operacion = sc.next();
        
        double resultado = 0;
        boolean valido = true;
        
        if (operacion.equals("+")) {
            resultado = num1 + num2;
        } else if (operacion.equals("-")) {
            resultado = num1 - num2;
        } else if (operacion.equals("*")) {
            resultado = num1 * num2;
        } else if (operacion.equals("/")) {
            if (num2 == 0) {
                System.out.println("Error: no se puede dividir entre cero");
                valido = false;
            } else {
                resultado = num1 / num2;
            }
        } else {
            System.out.println("Operación no valida");
            valido = false;
        }

        if (valido) {
            System.out.println("El resultado es: " + resultado);
        }
    }

    public static void ParImpar(Scanner sc) {
        System.out.println("--- Par o impar ---");
        System.out.print("Ingresa un número: ");
        int numero = sc.nextInt();
        
        if (numero % 2 == 0) {
            System.out.println("El número " + numero + " es par.");
        } else {
            System.out.println("El número " + numero + " es impar.");
        }

        if (numero > 0) {
            System.out.println("El número " + numero + " es positivo.");
        } else if (numero < 0) {
            System.out.println("El número " + numero + " es negativo.");
        } else {
            System.out.println("El número es cero.");
        }
    }

    public static void TablaMultiplicar(Scanner sc) {
        System.out.println("--- Tabla de multiplicar ---");
        System.out.print("Ingresa un número: ");
        int numero = sc.nextInt();
        
        System.out.println("Tabla de multiplicar del " + numero + ":");
        for (int i = 1; i <= 10; i++) {
            System.out.println(numero + " x " + i + " = " + (numero * i));
        }
    }

    public static void Numeros1al100() {
        System.out.println("--- Números del 1 al 100 ---");
        for (int i = 1; i <= 100; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void ClasePersona(Scanner sc) {
        System.out.println("--- Clase Persona ---");
        System.out.print("Ingresa el nombre: ");
        String nombre = sc.next();
        System.out.print("Ingresa la edad: ");
        int edad = sc.nextInt();
        sc.nextLine();
        System.out.print("Ingresa el correo electrónico: ");
        String correo = sc.nextLine();

        Persona persona = new Persona(nombre, edad, correo);
        persona.mostrarInformacion();
        
    }

    public static void ClaseRectangulo(Scanner sc) {
        System.out.println("--- Clase Rectángulo ---");
        System.out.print("Ingresa la base: ");
        double base = sc.nextDouble();
        System.out.print("Ingresa la altura: ");
        double altura = sc.nextDouble();

        Rectangulo rectangulo = new Rectangulo(base, altura);
        System.out.println("Área del rectángulo: " + rectangulo.calcularArea());
        System.out.println("Perímetro del rectángulo: " + rectangulo.calcularPerimetro());
    }

    public static void ClaseEmpleado(Scanner sc) {
        System.out.println("--- Clase Empleado ---");
        System.out.print("Ingresa el nombre: ");
        String nombre = sc.next();
        System.out.print("Ingresa la edad: ");
        int edad = sc.nextInt();
        System.out.print("Ingresa el salario: ");
        double salario = sc.nextDouble();
        sc.nextLine();
        System.out.print("Ingresa el puesto: ");
        String puesto = sc.nextLine();

        Empleado empleado = new Empleado(nombre, edad, puesto, salario);
        empleado.mostrarDatos();
        System.out.println("Bono del empleado (10%): " + empleado.calcularBono());
    }

    public static void ClaseFigura(Scanner sc) {
        System.out.println("--- Clase Figura ");
        System.out.println("Que figura desea calcular");
        System.out.println("1. Circulo");
        System.out.println("2. Triangulo");
        int opcionFigura = sc.nextInt();

        Figura figura = null;

        if (opcionFigura == 1) {
            System.out.print("Ingresa el radio del círculo: ");
            double radio = sc.nextDouble();
            figura = new Circulo(radio);
        } else if (opcionFigura == 2) {
            System.out.print("Ingresa la base del triángulo: ");
            double base = sc.nextDouble();
            System.out.print("Ingresa la altura del triángulo: ");
            double altura = sc.nextDouble();
            figura = new Triangulo(base, altura);
        }else {
            System.out.println("Opción inválida.");
            return;
        }

        System.out.println("Área de la figura: " + figura.calcularArea());

    }

    public static void ContadorVocales(Scanner sc) {
        System.out.println("--- Contador de Vocales ---");
        sc.nextLine();
        System.out.print("Ingresa una frase: ");
        String frase = sc.nextLine();

        int contador = 0;
        for (int i = 0; i < frase.length(); i++) {
            char c = Character.toLowerCase(frase.charAt(i));
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                contador++;
            }
        }
        System.out.println("Número de vocales en la frase: " + contador);

    }

    public static void CalculadoraNotas(Scanner sc) {
        System.out.println("--- Calculadora de Notas ---");
        sc.nextLine();
        System.out.print("Ingrese el nombre del estudiante: ");
        String nombre = sc.nextLine();

        double[] notas = new double[5];
        for(int i = 0; i < 5; i++) {
            System.out.print("Ingrese la nota " + (i + 1) + ": ");
            notas[i] = sc.nextDouble();
        }
        Estudiante estudiante = new Estudiante(nombre, notas);
        System.out.println("Promedio de notas: " + estudiante.calcularPromedio());
        System.out.println("Calificación final: " + estudiante.obtenerCalificacion());

    }
}  


    






    
