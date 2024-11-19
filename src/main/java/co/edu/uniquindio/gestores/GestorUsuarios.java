package co.edu.uniquindio.gestores;

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

import co.edu.uniquindio.estructuras.listas.ListaSimple;
import co.edu.uniquindio.excepciones.autenticacion.UsuarioNoRegistradoException;
import co.edu.uniquindio.excepciones.autenticacion.UsuarioRegistradoException;
import co.edu.uniquindio.excepciones.usuarios.CorreoEnUsoException;
import co.edu.uniquindio.modelos.Usuario;
import co.edu.uniquindio.modelos.Usuario.TipoUsuario;
import co.edu.uniquindio.persistencias.UsuarioPersistencia;
import co.edu.uniquindio.utilidades.UtilidadString;

public class GestorUsuarios
{
    /**
     * instancia por defecto
     */
    private static GestorUsuarios instancia;

    /**
     * usuarios en el gestor
     */
    private ListaSimple<Usuario> usuarios;

    /**
     * previene instanciacion externa
     */
    private GestorUsuarios() { usuarios = new ListaSimple<>(); }
    
    /**
     * carga la informacion desde la persistencai
     * 
     * @return la instancia del gestor
     * 
     * @throws IOException errror con el fichero
     */
    public static GestorUsuarios cargarGestor()
    {
        try {
            instancia().usuarios = new UsuarioPersistencia().todos();

            return instancia;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * obtiene la instacia por defecto de Gestor de uusario
     * @return
     */
    public static GestorUsuarios instancia()
    {
        if (instancia == null) {
            GestorUsuarios inst = new GestorUsuarios();

            try {
                inst = new GestorUsuarios();
                inst.usuarios = new UsuarioPersistencia().todos();
                instancia = inst;
            } catch (Exception e) {
                throw new RuntimeException("Error con fichero de usuarios");
            }
        }

        return instancia;
    }

    /**
     * persiste la informacion
     */
    public void guardarInformacion() throws IOException
    {
        new UsuarioPersistencia().sobreescribir(usuarios);
    }

    /**
     * obtiene los usuarios de un tipo (normal, administrador)
     * 
     * @param tipo tipo de usuario o {@code null} para cualquier tipo
     * 
     * @return usuarios del tipo designado
     */
    public ListaSimple<Usuario> buscarPorTipo(TipoUsuario tipo)
    {
        if (tipo == null) return usuarios;

        ListaSimple<Usuario> resultado = new ListaSimple<>();

        for (Usuario usuario : usuarios) {
            if (tipo.equals(usuario.getTipo())) resultado.agregar(usuario);
        }

        return resultado;
    }

    public ListaSimple<Usuario> buscarUsuario(String nombre, String correo, TipoUsuario tipo)
    {
        ListaSimple<Usuario> resultado = new ListaSimple<>();

        for (Usuario usuario : usuarios) {
            if (tipo != null && usuario.getTipo() != tipo) {
                continue;
            }

            if (UtilidadString.iNoContiene(usuario.getNombre(), nombre)) {
                continue;
            }

            if (UtilidadString.iContiene(usuario.getCorreo(), correo)) {
                resultado.agregar(usuario);
            }
        }

        return resultado;
    }

    /**
     * registra un nuevo usuario
     * 
     * @param usuario usuario a registrar
     * 
     * @throws UsuarioRegistradoException
     */
    public void registrar(Usuario usuario) throws CorreoEnUsoException
    {
        for (Usuario item : usuarios) {
            if (item.getCorreo().equalsIgnoreCase(usuario.getCorreo())) {
                throw new CorreoEnUsoException();
            }
        }

        usuarios.agregar(usuario);
    }

    /**
     * remueve un usuario
     * 
     * @param correo correo del usuario a eliminar
     * 
     * @throws IOException
     */
    public void remover(String correo) throws IOException
    {
        int indice = -1;
        int indiceActual = 0;
        Iterator<Usuario> it = usuarios.iterator();

        while (indice == -1 && it.hasNext()) {
            if (it.next().getCorreo().equalsIgnoreCase(correo)) {
                indice = indiceActual;
            }

            indiceActual++;
        }

        if (indice != -1) {
            usuarios.remover(indice);
        }
    }

    /**
     * encuentra un usuario dado su correo
     * 
     * @param correo correo del usuario a buscar
     * 
     * @return usuario encontrado
     */
    public Optional<Usuario> encontrar(String correo)
    {
        for (Usuario usuario : usuarios) {
            if (! usuario.getCorreo().equalsIgnoreCase(correo)) {
                return Optional.of(usuario);
            }
        }

        return Optional.empty();
    }

    /**
     * @param correo correo del usuario a actualizar
     * @param nuevo nueva informacion del usuario
     * 
     * @throws CorreoEnUsoException si el usuario no pudo ser encontrado
     * @throws UsuarioNoRegistradoException si el correo ya ha sido tomado
     */
    public void actualizarUsuario(String correo, Usuario nuevo) throws UsuarioNoRegistradoException, CorreoEnUsoException
    {
        Usuario usuario = null;

        // buscamos el usuario al que pertence el correo y comprobamos
        // que no halla otro usuario con el nuevo correo
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correo)) {
                usuario = u;
                break;
            } else {
                if (nuevo.getCorreo().equalsIgnoreCase(u.getCorreo())) {
                    throw new CorreoEnUsoException();
                }
            }
        }

        // si no se puedo encontrar al usuario
        if (usuario == null) throw new UsuarioNoRegistradoException();

        // actualizar informacion
        usuario.setNombre(nuevo.getNombre());
        usuario.setCorreo(nuevo.getCorreo());
        usuario.setClave(nuevo.getClave());
        usuario.setTipo(nuevo.getTipo());
    }

    public ListaSimple<Usuario> getUsuarios()
    {
        return usuarios;
    }
}