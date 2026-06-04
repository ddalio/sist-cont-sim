package simulacion.continuo.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class ExportadorService {

    /**
     * Exporta el resultado de la simulación a un archivo de texto o CSV.
     * @param resultado Objeto que contiene las listas de datos.
     * @param file Archivo destino seleccionado por el usuario.
     * @param separador Carácter separador (por ejemplo, "," para CSV estándar o "\t" para TXT tabulado).
     */
    public static void exportarAArchivo(ResultadoSimulacion resultado, File file, String separador) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Escribir el encabezado
            writer.write(String.format("Tiempo%sPosicion%sVelocidad", separador, separador));
            writer.newLine();

            int totalPuntos = resultado.tiempos().size();
            
            for (int i = 0; i < totalPuntos; i++) {
                double t = resultado.tiempos().get(i);
                double pos = resultado.posiciones().get(i);
                double vel = resultado.velocidades().get(i);

                
                String linea = String.format(Locale.US, "%f%s%f%s%f", t, separador, pos, separador, vel);
                writer.write(linea);
                writer.newLine();
            }
        }
    }
}