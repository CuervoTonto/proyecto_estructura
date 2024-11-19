package co.edu.uniquindio;

import java.util.Iterator;

import co.edu.uniquindio.estructuras.colas.Cola;
import co.edu.uniquindio.estructuras.listas.ListaDobleCircular;
import co.edu.uniquindio.estructuras.listas.ListaSimple;
import co.edu.uniquindio.modelos.Actividad;
import co.edu.uniquindio.modelos.Proceso;
import co.edu.uniquindio.utilidades.Persistencia;

public class Pruebas
{
    public static void main(String[] args) throws Exception
    {
        // Proceso p = new Proceso("123", "eliminar");
        // p.registrarActividad(new Actividad("Act1", false, "algo"));
        // p.registrarActividad(new Actividad("Act2", false, "otra"));

        // var l = new ListaSimple<String>();

        // l.agregar("A");
        // l.agregar("B");

        // Iterator<String> it = l.iterator();

        // Persistencia.escribirXML("prueba.xml", l);
        // System.out.println(l.getLongitud());

        // var p = Aplicativo.instancia().encontrarProceso("123").get();

        // System.out.println(p.encontrarActividad("algo#1").get().getDescripcion());


        Cola<String> cola = new Cola<>();
        cola.encolar("A");
        cola.encolar("B");

        Persistencia.escribirXML("prueba.xml", cola);
    }
}