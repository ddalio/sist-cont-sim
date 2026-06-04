package simulacion.continuo.model;

import java.util.List;

public record ResultadoSimulacion(
    List<Double> tiempos,
    List<Double> posiciones,
    List<Double> velocidades,
    double tFinal
) {}
