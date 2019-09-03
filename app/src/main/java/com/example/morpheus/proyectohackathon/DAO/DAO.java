package com.example.morpheus.proyectohackathon.DAO;

import java.util.List;

/**
 * Created by Fabi on 20/06/2018.
 */

public abstract class DAO {

    //PARA SACAR DATOS DE LA BD
    public interface OnResultadoConsulta<T> {

        void consultaSuccess(T t);

        void consultaFailed(String error, int codigo);

    }

    //PARA GENERAR LISTAS CON LOS DATOS DE LA BD
    public interface OnResultadoListaConsulta<T> {
        void consultaSuccess(List<T> t);

        void consultaFailed(String error, int codigo);

    }
}


