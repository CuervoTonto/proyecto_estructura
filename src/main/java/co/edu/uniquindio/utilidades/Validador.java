package co.edu.uniquindio.utilidades;

import co.edu.uniquindio.excepciones.validaciones.FormatoCorreoInvalidoException;
import co.edu.uniquindio.excepciones.validaciones.ValidacionException;

/**
 * validador para los datos de la interfaz grafica de usuario
 */
final public class Validador
{
    private Validador() {}

    /**
     * valida que los campos ingresado no esten en blanco
     * 
     * @param campos valor de los campos a validar
     * 
     * @throws ValidacionEception si alguno de los campos esta en blanco
     */
    public static void validarRequeridos(String... campos) throws ValidacionException
    {
        for (String entrada : campos) {
            if (entrada.isBlank()) {
                throw new ValidacionException("ninguno de los campos puede estar vacio");
            }
        }
    }

    /**
     * valida que los campos ingresado sean correos validos (por formato)
     * 
     * @param campos valor de los campos a validar
     * 
     * @throws ValidacionException si alguno de los campos no cumple los requisitos
     */
    public static void ValidarCorreo(String... campos) throws ValidacionException
    {
        for (String entrada : campos) {
            if (! entrada.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.([a-zA-Z]{2,4})$")) {
                throw new FormatoCorreoInvalidoException();
            }
        }
    }
}