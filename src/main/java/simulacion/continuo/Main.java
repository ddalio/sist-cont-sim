package simulacion.continuo;


import simulacion.continuo.configuracion.Configuracion;
import simulacion.continuo.configuracion.ConfiguracionLoader;
import simulacion.continuo.variables.ModeloLineal;
import simulacion.continuo.variables.ModeloNoLineal;

public class Main {
    public static void main(String[] args) {
        Configuracion config = ConfiguracionLoader.load();
        
        // Simulación Lineal
        SistemaContinuo sisLineal = new SistemaContinuo();
        sisLineal.setNombreArchivo("lineal.csv");
        sisLineal.cargarParametros(config, new ModeloLineal());
        sisLineal.run();

        // Simulación No Lineal
        SistemaContinuo sisNoLineal = new SistemaContinuo();
        sisNoLineal.setNombreArchivo("no_lineal.csv");
        sisNoLineal.cargarParametros(config, new ModeloNoLineal());
        sisNoLineal.run();
    }
}
