package simulacion.continuo.model.metodos;

import simulacion.continuo.model.Estado;
import simulacion.continuo.model.Variable;

public class IntegradorEuler implements Integrador {
    @Override
    public void paso(Estado estado, double h) {
        for (Variable v : estado.getVariables()) {
            v.calcularDerivada(estado);
        }

        for (Variable v : estado.getVariables()) {
            double nuevoValor = v.getValor() + v.getDerivada() * h;
            v.setValor(nuevoValor);
        }
    }
}
