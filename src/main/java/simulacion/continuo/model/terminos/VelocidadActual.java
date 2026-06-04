package simulacion.continuo.model.terminos;

import simulacion.continuo.model.Estado;

public class VelocidadActual implements Termino {
    public VelocidadActual(){}
    @Override
    public double calcular(Estado estado) {
        return estado.get("velocidad");
    }

}