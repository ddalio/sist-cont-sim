package simulacion.continuo.terminos;

import simulacion.continuo.variables.Variable;

public class Amortiguamiento implements Termino {
    double b, m;
    public Amortiguamiento(double b, double m) {
        this.b = b;
        this.m = m;
    }
    @Override
    public double calculate(Variable variable) {
        return (-b * v) / m;
    }
}