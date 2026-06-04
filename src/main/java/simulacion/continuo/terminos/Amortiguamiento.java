package simulacion.continuo.terminos;

import simulacion.continuo.Estado;
import simulacion.continuo.variables.Variable;

public class Amortiguamiento implements Termino {
    double coeficienteAmortiguamiento, masa;

    public Amortiguamiento(double coeficienteAmortiguamiento, double masa) {
        this.coeficienteAmortiguamiento = coeficienteAmortiguamiento;
        this.masa = masa;
    }

    @Override
    public double calcular(Estado estado) {
        double v = estado.get("velocidad");
        return (-coeficienteAmortiguamiento * v) / masa;
    }
}