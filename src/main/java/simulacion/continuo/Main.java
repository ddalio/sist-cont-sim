package simulacion.continuo;


import simulacion.continuo.configuracion.Configuracion;
import simulacion.continuo.configuracion.ConfiguracionLoader;
import simulacion.continuo.variables.ModeloLineal;
import simulacion.continuo.variables.ModeloNoLineal;

public class Main {
    public static void main(String[] args) {
        Configuracion configuracion = ConfiguracionLoader.load();

        System.out.println("Sistema lineal con modelo lineal:");
        SistemaContinuo sistema = new SistemaContinuo();
        sistema.cargarParametros(configuracion, new ModeloLineal());
        sistema.run();

        System.out.println("Sistema lineal con modelo NO lineal:");
        SistemaContinuo sistema2 = new SistemaContinuo();
        sistema2.cargarParametros(configuracion, new ModeloNoLineal());
        sistema2.run();
    }
}
