package simulacion.continuo;

import java.util.List;
import java.util.Map;
public class Estado {

    private final Map<String, Variable> variables;

    public Estado(Map<String, Variable> variables) {
        this.variables = variables;
    }

    public Double get(String nombre) {
        return variables.get(nombre).getValor();
    }

    public void paso(double time) {

        for (Variable variable : variables.values()) {
            variable.calcularDerivada(this);
        }

        for (Variable variable : variables.values()) {
            variable.avanzar(time);
        }
    }
}