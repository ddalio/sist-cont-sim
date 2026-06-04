package simulacion.continuo.metodos;

import simulacion.continuo.Estado;

public interface Integrador {
    void paso(Estado estado, double h);
}
