package simulacion.continuo.variables;

import java.util.List;
import java.util.Map;

public class Posicion extends Variable {
    public Posicion(double valor) {
        super("posicion", valor);
    }

    @Override
    public void calcularDerivada(Map<String, Double> parametros, List<Variable> variables) {
        Variable velocidad = variables.stream()
                .filter(v -> v.getNombre().equals("velocidad"))
                .findFirst()
                .orElse(null);
        
        if (velocidad != null) {
            setDerivada(velocidad.getValor());
        }
    }
}
