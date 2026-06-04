package simulacion.continuo.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simulacion.continuo.model.configuracion.Configuracion;
import simulacion.continuo.model.metodos.Integrador;
import simulacion.continuo.model.metodos.IntegradorEuler;
import simulacion.continuo.model.metodos.IntegradorRK4;
import simulacion.continuo.model.terminos.Termino;
import simulacion.continuo.model.terminos.TerminoFactory;

public class SistemaFactory {

    public static SistemaContinuo crear(Map<String, Double> parametros, boolean esLineal, boolean usarRK4, Configuracion config) {
        TerminoFactory terminoFactory = new TerminoFactory(parametros);
        
        List<Termino> terminosVelocidad = esLineal ? 
            List.of(terminoFactory.crear("Gravedad"), terminoFactory.crear("Amortiguamiento"), terminoFactory.crear("ResorteLineal")) :
            List.of(terminoFactory.crear("Gravedad"), terminoFactory.crear("Amortiguamiento"), terminoFactory.crear("ResorteNoLineal"));
        
        List<Termino> terminosPosicion = List.of(terminoFactory.crear("VelocidadActual"));

        Variable velocidad = new Variable("velocidad", 0.0, terminosVelocidad);
        Variable posicion = new Variable("posicion", 1.0, terminosPosicion);

        Map<String, Variable> variables = new HashMap<>();
        variables.put(velocidad.getNombre(), velocidad);
        variables.put(posicion.getNombre(), posicion);
        Estado estado = new Estado(variables);

        Integrador integrador = usarRK4 ? new IntegradorRK4() : new IntegradorEuler();

        SistemaContinuo sistema = new SistemaContinuo(estado,integrador);
        sistema.cargarParametros(config);
        
        return sistema;
    }
}
