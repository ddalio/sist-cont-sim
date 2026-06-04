package simulacion.continuo.model.metodos;

import simulacion.continuo.model.Estado;

public interface Integrador {
    void paso(Estado estado, double h);
}
