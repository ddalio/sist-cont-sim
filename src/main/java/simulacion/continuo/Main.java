package simulacion.continuo;

import simulacion.continuo.configuracion.Configuracion;
import simulacion.continuo.configuracion.ConfiguracionLoader;
import simulacion.continuo.terminos.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Configuracion configuracion = ConfiguracionLoader.load();
        Map<String,Double> parametros = configuracion.parametros();
        TerminoFactory terminoFactory = new TerminoFactory(parametros);

        List<Termino> terminosDerivadaVelocidad = List.of(terminoFactory.crear("Gravedad"), terminoFactory.crear("Amortiguamiento"), terminoFactory.crear("ResorteNoLineal"));
        List<Termino> terminosDerivadaPosicion = List.of(terminoFactory.crear("VelocidadActual"));

        Variable velocidad = new Variable("velocidad", 1.0, terminosDerivadaVelocidad);
        Variable posicion = new Variable("posicion", 0.0, terminosDerivadaPosicion);

        Map<String, Variable> variables = new HashMap<>();
        variables.put(velocidad.getNombre(), velocidad); variables.put(posicion.getNombre(), posicion);
        Estado estado = new Estado(variables);
        SistemaContinuo sistema = new SistemaContinuo(estado);
        sistema.cargarParametros(configuracion);
        sistema.run();
    }
}
