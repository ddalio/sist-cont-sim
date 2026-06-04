# Simulación de Sistema Masa-Resorte No Lineal

Aplicación desarrollada en Java para la simulación de un sistema masa-resorte amortiguado, permitiendo comparar modelos lineales y no lineales mediante distintos métodos de integración numérica.

## Características

* Simulación de un sistema masa-resorte con amortiguamiento.
* Selección entre sistema lineal y no lineal.
* Integración numérica mediante:

  * Método de Euler.
  * Método de Runge-Kutta de cuarto orden (RK4).
* Visualización gráfica de:

  * Evolución temporal de posición y velocidad.
  * Espacio de fases.
* Modificación interactiva de parámetros del modelo.
* Exportación de resultados a archivos `.csv` y `.txt`.

## Requisitos

* Java 17 o superior.
* Gradle.

## Ejecución

Desde la raíz del proyecto ejecutar:

```bash
./gradlew run
```

En Windows:

```bash
gradlew.bat run
```

## Uso

1. Configurar los parámetros del sistema.
2. Seleccionar:

   * Tipo de sistema (Lineal / No Lineal).
   * Método de integración (Euler / RK4).
3. Presionar **Run** para ejecutar la simulación.
4. Analizar los gráficos generados.
5. Exportar los resultados mediante el botón **Descargar Resultados (.csv/.txt)**.

## Estructura General

* `controller/` — Controladores JavaFX.
* `model/` — Lógica de simulación e integración numérica.
* `view/` — Componentes visuales.
* `configuracion/` — Carga de parámetros desde archivos de configuración.

## Autores

* [Fancisco Natale](https://github.com/francisconatale)
* [Damián Dalio](https://github.com/ddalio)

