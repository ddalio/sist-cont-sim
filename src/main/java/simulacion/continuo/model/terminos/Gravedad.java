package simulacion.continuo.model.terminos;

import simulacion.continuo.model.Estado;

public class Gravedad implements Termino {

    private final double g;

    public Gravedad(double g) {
        this.g = g;
    }

    @Override
    public double calcular(Estado estado) {
        return -g;
    }
}