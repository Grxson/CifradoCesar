package org.example;

/**
 * Implementación del algoritmo de cifrado César.
 * Permite cifrar y descifrar textos mediante desplazamiento de caracteres en el abecedario.
 * Incluye soporte para caracteres especiales del español (Ñ, ñ) y manejo de mayúsculas/minúsculas.
 */
public class CifradoCesar {
    /**
     * Cadena que contiene todos los caracteres válidos para el cifrado.
     * Incluye letras mayúsculas (A-Z, Ñ) y minúsculas (a-z, ñ) en orden secuencial.
     */
    private static final String ABECEDARIO = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz";
    public static int getLongitudAbecedario() {
        /**
         * Obtiene la longitud total del abecedario utilizado para el cifrado.
         * @return Número total de caracteres en el abecedario (incluye mayúsculas, minúsculas y Ñ/ñ)
         */
        return ABECEDARIO.length();
    }

    /**
     * Método principal para cifrar o descifrar texto.
     * @param texto Texto a procesar (original o cifrado)
     * @param desplazamiento Número de posiciones a desplazar (clave)
     * @param cifrar true para cifrar, false para descifrar
     * @return Texto procesado (cifrado o descifrado)
     *
     * @implNote El algoritmo mantiene caracteres no alfabéticos sin cambios.
     * @implSpec Para descifrar, se usa el mismo desplazamiento pero en dirección opuesta.
     */
    public String cifrarDescifrar(String texto, int desplazamiento, boolean cifrar) {
        StringBuilder resultado = new StringBuilder();
        // Ajusta la dirección del desplazamiento según la operación
        desplazamiento = cifrar ? desplazamiento : -desplazamiento;

        for (char caracter : texto.toCharArray()) {
            int posicion = ABECEDARIO.indexOf(caracter);
            if (posicion != -1) {
                // Calcula la nueva posición con desplazamiento circular
                int nuevaPosicion = (posicion + desplazamiento) % ABECEDARIO.length();
                // Maneja desplazamientos negativos
                if (nuevaPosicion < 0) {
                    nuevaPosicion += ABECEDARIO.length();
                }
                resultado.append(ABECEDARIO.charAt(nuevaPosicion));
            } else {
                resultado.append(caracter);
            }
        }
        return resultado.toString();
    }

    /**
     * Realiza un ataque por fuerza bruta probando todos los desplazamientos posibles.
     * @param textoCifrado Texto a descifrar
     *
     * @apiNote Método útil cuando se desconoce el desplazamiento usado para cifrar.
     * @implNote Imprime todos los resultados posibles numerados por desplazamiento.
     */
    public void probarTodosLosDesplazamientos(String textoCifrado) {
        System.out.println("\n=== RESULTADOS CON TODOS LOS DESPLAZAMIENTOS ===");
        for (int i = 1; i <= ABECEDARIO.length(); i++) {
            System.out.println("Desplazamiento " + i + ": " + cifrarDescifrar(textoCifrado, i, false));
        }
    }
}