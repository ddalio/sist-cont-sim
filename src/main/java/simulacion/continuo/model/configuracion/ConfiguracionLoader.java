package simulacion.continuo.model.configuracion;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

            // Usamos LinkedHashMap para mantener el orden de definición del JSON en la UI
            Map<String, ParametroConfig> simulationParams = new LinkedHashMap<>();
            
            for (Map<String, Object> item : configItems) {
                String nombre = (String) item.get("nombre");
                double valor = ((Number) item.get("valor")).doubleValue();

                switch (nombre) {
                    case "h": h = valor; break;
                    case "t_max": t_max = valor; break;
                    case "tolerance": tolerance = valor; break;
                    default:
                        double min = item.containsKey("min") ? ((Number) item.get("min")).doubleValue() : 0.0;
                        double max = item.containsKey("max") ? ((Number) item.get("max")).doubleValue() : Math.max(100.0, valor * 2);
                        String unidad = (String) item.getOrDefault("unidad", "");
                        String label = (String) item.getOrDefault("label", formatearNombre(nombre));
                        
                        simulationParams.put(nombre, new ParametroConfig(valor, min, max, unidad, label));
                        break;
                }
            }
            return new Configuracion(simulationParams, h, t_max, tolerance);
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar la configuracion", e);
        }
    }

    private static String formatearNombre(String camelCase) {
        if (camelCase == null || camelCase.isEmpty()) return "";
        String result = camelCase.replaceAll("([A-Z])", " $1");
        return result.substring(0, 1).toUpperCase() + result.substring(1);
    }
}
