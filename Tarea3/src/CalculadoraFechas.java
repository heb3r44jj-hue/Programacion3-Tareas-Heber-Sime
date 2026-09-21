import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;
import java.text.SimpleDateFormat;

public class CalculadoraFechas {

    public static void ejecutar(Scanner sc) {
        Locale es = new Locale("es", "ES");

        Calendar hoy = Calendar.getInstance();

        SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat f2 = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", es);
        SimpleDateFormat f3 = new SimpleDateFormat("dd-MM-yyyy");

        System.out.println("\n===== FECHAS =====");
        System.out.println("Formato 1: " + f1.format(hoy.getTime()));
        System.out.println("Formato 2: " + f2.format(hoy.getTime()));
        System.out.println("Formato 3: " + f3.format(hoy.getTime()));

        Calendar en90 = (Calendar) hoy.clone();
        en90.add(Calendar.DAY_OF_YEAR, 90);
        System.out.println("Fecha dentro de 90 dias: " + f3.format(en90.getTime()));

        Calendar hace6 = (Calendar) hoy.clone();
        hace6.add(Calendar.MONTH, -6);
        System.out.println("Fecha hace 6 meses: " + f3.format(hace6.getTime()));

        System.out.print("Ingresa tu anio de nacimiento: ");
        int anioNac = sc.nextInt();
        int edad = hoy.get(Calendar.YEAR) - anioNac;
        System.out.println("Tu edad es: " + edad + " anios");

        Calendar finAnio = Calendar.getInstance();
        finAnio.set(hoy.get(Calendar.YEAR), Calendar.DECEMBER, 31);
        long diferencia = finAnio.getTimeInMillis() - hoy.getTimeInMillis();
        long dias = diferencia / (1000 * 60 * 60 * 24);
        System.out.println("Faltan " + dias + " dias para el 31 de diciembre");
    }
}