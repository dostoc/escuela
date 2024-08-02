package dao;

import modelo.Alumno;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T,K> {

    // T es un objeto tipo generico y K es el tipo de dato del Id de la clase que hereda

    void insertar(T K) throws DAOExecption;

    void modificar(T a) throws DAOExecption;

    void eliminar(T a) throws DAOExecption;

    List<T> listarTodos() throws DAOExecption;

    T buscarPorId(K id) throws DAOExecption, SQLException;

}
