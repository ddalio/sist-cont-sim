package simulacion.continuo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simulacion.continuo.configuracion.Configuracion;
import simulacion.continuo.variables.ModeloFisico;
import simulacion.continuo.variables.Posicion;
import simulacion.continuo.variables.Variable;
import simulacion.continuo.variables.Velocidad;

public class SistemaContinuo {
    Map<String, Double> parametros = new HashMap<>();
    List<Variable> variables = new ArrayList<>();
    double h = 0.001;
    double t_max = 50.0;
    double tolerance = 0.001;
    double tiempo = 0.0;

    private String nombreArchivo = "simulacion_continua.csv";

    public void cargarParametros(Configuracion config, ModeloFisico modelo) {
        this.parametros = config.parametros();
        this.h = config.h();
        this.t_max = config.t_max();
        this.tolerance = config.tolerance();

        variables.add(new Posicion(1.0));
        variables.add(new Velocidad(0.0, modelo));
    }

    public void paso() {
        for (Variable v : variables) {
            v.calcularDerivada(parametros, variables);
        }
        for (Variable v : variables) {
            v.avanzar(h);
        }
        tiempo += h;
    }

    public void run() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            // Escribir cabecera del CSV
            writer.println("tiempo,posicion,velocidad");

            while (tiempo < t_max) {
                paso();
                
                // Guardar cada 1 segundo (o cada paso si prefieres más detalle)
                if (tiempo % 1.0 < h) {
                    double x = variables.stream().filter(variable -> variable.getNombre().equals("posicion")).findFirst().get().getValor();
                    double v = variables.stream().filter(variable -> variable.getNombre().equals("velocidad")).findFirst().get().getValor();
                    
                    // Escribir en archivo
                    writer.printf("%.4f,%.4f,%.4f%n", tiempo, x, v);
                    
                    // Mantener el log en consola
                    System.out.printf("Tiempo: %.2f | x: %.4f v: %.4f%n", tiempo, x, v);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        }
    }

    public void setNombreArchivo(String nombre) {
        this.nombreArchivo = nombre;
    }
}
