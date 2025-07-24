package org.example;
import java.util.Scanner;
/**
 * Clase principal que maneja la interacción con el usuario para el cifrado César.
 * Proporciona un menú para cifrar, descifrar y salir del programa.
 */
public class Usuario {
    private final Scanner scanner;
    private final CifradoCesar cifrador;
    private String textoOriginal;
    private String textoCifrado;

    /**
     * Constructor que inicializa los componentes necesarios.
     */
    public Usuario() {
        this.scanner = new Scanner(System.in);
        this.cifrador = new CifradoCesar();
        this.textoOriginal = null;
        this.textoCifrado = null;
    }
    /**
     * Método principal que controla el flujo del programa.
     * Muestra el menú y procesa las opciones seleccionadas por el usuario.
     */
    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> cifrarMensaje();
                    case 2 -> descifrarMensaje();
                    case 3 -> salir = true;
                    default -> System.out.println("❌ Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ ¡Debes ingresar un número!");
            }
        }
        scanner.close();
    }
    /**
     * Muestra el menú de opciones al usuario.
     */
    private void mostrarMenu() {
        System.out.println("\n=== CIFRADO CÉSAR (POO) ===");
        System.out.println("1. Cifrar");
        System.out.println("2. Descifrar");
        System.out.println("3. Salir");
        System.out.print("Elige una opción: ");
    }

    /**
     * Maneja el proceso de cifrado de un mensaje.
     * Solicita el texto y el desplazamiento al usuario, luego realiza el cifrado.
     */
    private void cifrarMensaje() {
        System.out.print("\nIngresa el texto a cifrar: ");
        textoOriginal = scanner.nextLine();
        int desplazamiento = solicitarDesplazamiento();
        textoCifrado = cifrador.cifrarDescifrar(textoOriginal, desplazamiento, true);
        System.out.println("✅ ¡Texto cifrado correctamente!");
    }
    /**
     * Maneja el proceso de descifrado con múltiples intentos.
     * Permite hasta 10 intentos para adivinar el desplazamiento correcto.
     */
    private void descifrarMensaje() {
        if (textoCifrado == null) {
            System.out.println("❌ Error: No hay texto cifrado. Cifra un mensaje primero.");
            return;
        }

        System.out.println("\n=== DESCIFRAR ===");
        boolean descifradoExitoso = false;
        int intentos = 0;
        final int MAX_INTENTOS = 10;  // 10 intentos ahora

        while (!descifradoExitoso && intentos < MAX_INTENTOS) {
            int desplazamiento = solicitarDesplazamiento();
            String textoDescifrado = cifrador.cifrarDescifrar(textoCifrado, desplazamiento, false);

            if (textoDescifrado.equals(textoOriginal)) {
                System.out.println("🎉 ¡Éxito! Texto descifrado: " + textoDescifrado);
                descifradoExitoso = true;
            } else {
                intentos++;
                System.out.println("🔓 Intento " + intentos + ": " + textoDescifrado);
                System.out.println(getMensajeError(intentos));
                if (intentos == MAX_INTENTOS) {
                    System.out.println("💥 ¡Agotaste tus " + MAX_INTENTOS + " intentos! El texto cifrado era: " + textoCifrado);
                }
            }
        }
    }
    /**
     * Solicita y valida el desplazamiento (clave) para cifrar/descifrar.
     * @return El desplazamiento válido ingresado por el usuario
     */
    private int solicitarDesplazamiento() {
        while (true) {
            try {
                System.out.print("Ingresa el desplazamiento (llave): ");
                int desplazamiento = Integer.parseInt(scanner.nextLine());
                if (desplazamiento <= 0 || desplazamiento > CifradoCesar.getLongitudAbecedario()) {
                    throw new IllegalArgumentException();
                }
                return desplazamiento;
            } catch (NumberFormatException e) {
                System.out.println("❌ ¡Debe ser un número entero!");
            } catch (IllegalArgumentException e) {
                System.out.println("❌ El desplazamiento debe estar entre 1 y " + CifradoCesar.getLongitudAbecedario());
            }
        }
    }
    /**
     * Genera mensajes de error personalizados según el número de intentos fallidos.
     * @param intentos Número de intento fallido actual (1-10)
     * @return Mensaje de error correspondiente al intento
     */
    private String getMensajeError(int intentos) {
        String[] mensajes = {
                "🤨 ¿En serio? ¡Intenta de nuevo!",
                "😏 Casi, pero no. Sigue intentando.",
                "🧐 Julio César usaba esto... ¿y tú?",
                "😅 ¡La paciencia es clave! Otro intento.",
                "🤔 ¿Has probado con el desplazamiento inverso?",
                "😬 La criptografía no es tu fuerte, ¿eh?",
                "🫠 ¡El abecedario tiene " + CifradoCesar.getLongitudAbecedario() + " letras! Pista útil.",
                "😮‍💨 ¡Últimos intentos! Piensa como un romano.",
                "😰 ¡Solo te queda 1 intento! ¡No lo arruines!",
                "💀 Nada como un fracaso épico para aprender."
        };
        return mensajes[intentos - 1];
    }
}