import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class GestorEstudiantes
{
    final static String NOMBRE_ARCHIVO = "estudiantes.txt";

    private ArrayList<Estudiante> lista = new ArrayList<>();

    public ArrayList<Estudiante> getLista()
    {
        return lista;
    }

    public String validar(String matricula, String nombre, String carrera, String calificacionTexto, Estudiante editando)
    {
        if (matricula.isEmpty() || nombre.isEmpty() || carrera.isEmpty() || calificacionTexto.isEmpty())
        {
            return "Todos los campos son obligatorios complete el formulario antes de continuar";
        }

        if (matricula.contains(";") || nombre.contains(";") || carrera.contains(";"))
        {
            return "Los campos no pueden contener punto y coma (;)";
        }

        double calificacion;
        try
        {
            calificacion = Double.parseDouble(calificacionTexto.replace(",", "."));
        }
        catch (NumberFormatException e)
        {
            return "La calificacion debe ser un numero. Valor recibido: " + calificacionTexto;
        }

        if (calificacion < 0 || calificacion > 100)
        {
            return "La calificacion debe estar entre 0 y 100";
        }

        Estudiante existente = buscarPorMatricula(matricula);
        if (existente != null && existente != editando)
        {
            return "Ya existe un estudiante con la matricula " + matricula + " (" + existente.getNombre() + ").";
        }

        return null;
    }

    public double convertirCalificacion(String calificacionTexto)
    {
        return Double.parseDouble(calificacionTexto.replace(",", "."));
    }

    public void registrar(Estudiante estudiante) throws IOException
    {
        lista.add(estudiante);
        guardarArchivo();
    }

    public void modificar(Estudiante estudiante, String matricula, String nombre, String carrera, double calificacion) throws IOException
    {
        estudiante.setMatricula(matricula);
        estudiante.setNombre(nombre);
        estudiante.setCarrera(carrera);
        estudiante.setCalificacion(calificacion);
        guardarArchivo();
    }

    public void eliminar(Estudiante estudiante) throws IOException
    {
        lista.remove(estudiante);
        guardarArchivo();
    }

    public Estudiante buscarPorMatricula(String matricula)
    {
        for (int i = 0; i < lista.size(); i++)
        {
            Estudiante estudiante = lista.get(i);
            if (estudiante.getMatricula().equalsIgnoreCase(matricula))
            {
                return estudiante;
            }
        }
        return null;
    }

    public ArrayList<Estudiante> buscar(String texto)
    {
        ArrayList<Estudiante> resultados = new ArrayList<>();
        String t = texto.toLowerCase();

        for (int i = 0; i < lista.size(); i++)
        {
            Estudiante estudiante = lista.get(i);
            if (estudiante.getMatricula().toLowerCase().contains(t) || estudiante.getNombre().toLowerCase().contains(t))
            {
                resultados.add(estudiante);
            }
        }
        return resultados;
    }

    public int getTotal()
    {
        return lista.size();
    }

    public double getPromedio()
    {
        if (lista.size() == 0)
        {
            return 0;
        }
        double suma = 0;
        for (int i = 0; i < lista.size(); i++)
        {
            suma = suma + lista.get(i).getCalificacion();
        }
        return suma / lista.size();
    }

    public int getAprobados()
    {
        int contador = 0;
        for (int i = 0; i < lista.size(); i++)
        {
            if (lista.get(i).isAprobado())
            {
                contador++;
            }
        }
        return contador;
    }

    public int getReprobados()
    {
        return lista.size() - getAprobados();
    }

    public void guardarArchivo() throws IOException
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO)))
        {
            for (int i = 0; i < lista.size(); i++)
            {
                bw.write(lista.get(i).aLineaArchivo());
                bw.newLine();
            }
        }
    }

    public int cargarArchivo() throws IOException
    {
        lista.clear();
        File archivo = new File(NOMBRE_ARCHIVO);
        int lineasConError = 0;

        if (archivo.createNewFile())
        {
            return 0;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo)))
        {
            String linea;

            while ((linea = br.readLine()) != null)
            {
                if (!linea.trim().isEmpty())
                {
                    String[] partes = linea.split(";");
                    if (partes.length == 4)
                    {
                        try
                        {
                            double calificacion = Double.parseDouble(partes[3]);
                            lista.add(new Estudiante(partes[0], partes[1], partes[2], calificacion));
                        }
                        catch (NumberFormatException e)
                        {
                            lineasConError++;
                        }
                    }
                    else
                    {
                        lineasConError++;
                    }
                }
            }
        }

        return lineasConError;
    }

    public void crearRespaldo(File archivo) throws IOException
    {
        try (ObjectOutputStream archivoSerializable = new ObjectOutputStream(new FileOutputStream(archivo)))
        {
            archivoSerializable.writeObject(lista);
        }
    }

    @SuppressWarnings("unchecked")
    public int restaurarRespaldo(File archivo) throws IOException, ClassNotFoundException
    {
        try (ObjectInputStream lecturaSerializable = new ObjectInputStream(new FileInputStream(archivo)))
        {
            Object leido = lecturaSerializable.readObject();

            if (!(leido instanceof ArrayList))
            {
                throw new IOException("El archivo seleccionado no es una copia de seguridad valida.");
            }

            ArrayList<Estudiante> recuperados = (ArrayList<Estudiante>) leido;
            lista.clear();
            lista.addAll(recuperados);
        }
        guardarArchivo();
        return lista.size();
    }
}