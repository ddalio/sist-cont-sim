package simulacion.continuo;

import simulacion.continuo.configuracion.Configuracion;
import simulacion.continuo.configuracion.ConfiguracionLoader;

public class Main {
    public static void main(String[] args) {
        Configuracion configuracion = ConfiguracionLoader.load();
        SistemaContinuo sistema = new SistemaContinuo();
        sistema.cargarParametros(configuracion);
        sistema.run();
    }
}
