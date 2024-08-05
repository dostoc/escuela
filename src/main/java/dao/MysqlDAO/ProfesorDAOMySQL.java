package dao.MysqlDAO;

import dao.DAOExecption;
import dao.ProfesorDAO;
import modelo.Profesor;

import java.sql.SQLException;
import java.util.List;

public class ProfesorDAOMySQL implements ProfesorDAO {
    @Override
    public List<Profesor> buscarPorNombre(String nombre) throws DAOExecption {
        return null;
    }

    @Override
    public void insertar(Profesor K) {

    }

    @Override
    public void modificar(Profesor a) {

    }

    @Override
    public void eliminar(Profesor a) {

    }

    @Override
    public List<Profesor> listarTodos() {
        return null;
    }

    @Override
    public Profesor buscarPorId(Long id) {
        return null;
    }
}
