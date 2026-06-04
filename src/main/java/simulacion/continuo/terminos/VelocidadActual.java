package simulacion.continuo.terminos;

import simulacion.continuo.Estado;

public class VelocidadActual implements Termino {
    public VelocidadActual(){}
    @Override
    public double calcular(Estado estado) {
        return estado.get("velocidad");
    }

}