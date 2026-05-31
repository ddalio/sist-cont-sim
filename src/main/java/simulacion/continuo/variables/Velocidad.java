package simulacion.continuo.variables;

import java.util.List;
import java.util.Map;

public class Velocidad extends Variable {
    public Velocidad(double valor) {
        super("velocidad", valor);
    }

    @Override
    public void calcularDerivada(Map<String, Double> parametros, List<Variable> variables) {
        double m = parametros.getOrDefault("masa", 1.0);
        double b = parametros.getOrDefault("coeficienteAmortiguamiento", 0.0);
        double k = parametros.getOrDefault("constanteElasticaLineal", 0.0);
        
        Variable posicion = variables.stream().filter(v -> v.getNombre().equals("posicion")).findFirst().orElse(null);
        
        if (posicion != null) {
            double x = posicion.getValor();
            double v = getValor();
            setDerivada((-b * v - k * x) / m);
        }
    }
}
