package simulacion.continuo.configuracion;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfiguracionLoader {
    private static final String CONFIG_FILE = "/config.json";

    public static Configuracion load() {
        ObjectMapper mapper = new ObjectMapper();
        double h = 0.001;
        double t_max = 50.0;
        double tolerance = 0.001;

        try (InputStream is = ConfiguracionLoader.class.getResourceAsStream(CONFIG_FILE)) {
            if (is == null) {
                throw new RuntimeException("No se pudo encontrar el archivo " + CONFIG_FILE);
            }
            
            List<Map<String, Object>> configItems = mapper.readValue(is, new TypeReference<List<Map<String, Object>>>() {});

            Map<String, Double> simulationParams = new HashMap<>();
            for (Map<String, Object> item : configItems) {
                String nombre = (String) item.get("nombre");
                double valor = ((Number) item.get("valor")).doubleValue();

                switch (nombre) {
                    case "h": h = valor; break;
                    case "t_max": t_max = valor; break;
                    case "tolerance": tolerance = valor; break;
                    default:
                        simulationParams.put(nombre, valor);
                        break;
                }
            }
            return new Configuracion(simulationParams, h, t_max, tolerance);
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar la configuracion", e);
        }
    }
}
