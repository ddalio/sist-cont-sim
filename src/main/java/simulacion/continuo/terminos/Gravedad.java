package simulacion.continuo.terminos;

import simulacion.continuo.Estado;

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