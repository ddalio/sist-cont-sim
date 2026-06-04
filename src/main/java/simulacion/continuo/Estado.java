package simulacion.continuo;

import java.util.ArrayList;
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

    public ArrayList<Variable> getVariables() {
        return new ArrayList<>(variables.values());
    }
}