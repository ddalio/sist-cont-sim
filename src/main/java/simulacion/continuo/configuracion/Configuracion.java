package simulacion.continuo.configuracion;

import java.util.Map;

public record Configuracion(
    Map<String, Double> parametros,
    double h,
    double t_max,
    double tolerance
) {}
