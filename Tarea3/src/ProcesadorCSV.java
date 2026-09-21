import java.util.StringTokenizer;

public class ProcesadorCSV {

    public static void ejecutar() {
        String datos = "Juan Perez,juanp@empresa.com,Sistemas,35000\n"+ "Ana Lopez,anal@empresa.com,Contabilidad,28000\n"+ "Carlos Ruiz,carlosr@gmail.com,Sistemas,42000\n" + "Maria Diaz,mariad@empresa.com,RRHH,31000\n"+ "Luis Marte,luism@gmail.com,Sistemas,38000";

        String[] lineas = datos.split("\n");
        String[] nombres = new String[lineas.length];
        String[] correos = new String[lineas.length];
        String[] deptos = new String[lineas.length];
        double[] salarios = new double[lineas.length];

        for (int i = 0; i < lineas.length; i++) {
            StringTokenizer st = new StringTokenizer(lineas[i], ",");
            nombres[i] = st.nextToken();
            correos[i] = st.nextToken();
            deptos[i] = st.nextToken();
            salarios[i] = Double.parseDouble(st.nextToken());
        }

        System.out.println("\n===== EMPLEADOS (CSV) =====");
        System.out.println(String.format("%-15s %-25s %-15s %10s","NOMBRE", "CORREO", "DEPTO", "SALARIO"));
        for (int i = 0; i < lineas.length; i++) {
            System.out.println(String.format("%-15s %-25s %-15s %,10.0f",nombres[i], correos[i], deptos[i], salarios[i]));
        }

        System.out.println("\nEmpleados del departamento Sistemas:");
        for (int i = 0; i < lineas.length; i++) {
            if (deptos[i].equals("Sistemas")) {
                System.out.println("  " + nombres[i] + " - " + correos[i]);
            }
        }

        System.out.println("\nEmpleados con correo @empresa.com:");
        for (int i = 0; i < lineas.length; i++) {
            if (correos[i].endsWith("@empresa.com")) {
                System.out.println("  " + nombres[i] + " - " + correos[i]);
            }
        }

        System.out.println("\nNombres en MAYUSCULAS y correos en minusculas:");
        for (int i = 0; i < lineas.length; i++) {
            System.out.println("  " + nombres[i].toUpperCase() + " - " + correos[i].toLowerCase());
        }

        int mas = 0;
        int menos = 0;
        double suma = 0;
        for (int i = 0; i < lineas.length; i++) {
            suma = suma + salarios[i];
            if (salarios[i] > salarios[mas]) {
                mas = i;
            }
            if (salarios[i] < salarios[menos]) {
                menos = i;
            }
        }
        System.out.println("\nSalario promedio: $" + String.format("%,.2f", suma / lineas.length));
        System.out.println("El que mas gana: " + nombres[mas]+ " ($" + String.format("%,.0f", salarios[mas]) + ")");
        System.out.println("El que menos gana: " + nombres[menos]+ " ($" + String.format("%,.0f", salarios[menos]) + ")");
    }
}