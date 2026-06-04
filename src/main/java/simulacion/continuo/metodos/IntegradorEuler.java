package simulacion.continuo.metodos;

import simulacion.continuo.Estado;
import simulacion.continuo.Variable;

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
