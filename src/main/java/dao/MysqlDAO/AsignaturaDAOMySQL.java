package dao.MysqlDAO;

import dao.AsignaturaDAO;
import dao.DAOExecption;
import modelo.Asignatura;

import java.sql.SQLException;
import java.util.List;

public class AsignaturaDAOMySQL implements AsignaturaDAO {
    @Override
    public Long obtenerIndex() throws DAOExecption, SQLException {
        return null;
    }

    @Override
    public Asignatura crearObjeto() throws DAOExecption, SQLException {
        return null;
    }

    @Override
    public List<Asignatura> buscarPorNombre(String nombre) throws DAOExecption {
        return null;
    }

    @Override
    public void insertar(Asignatura K) {

    }

    @Override
    public void modificar(Asignatura a) {

    }

    @Override
    public void eliminar(Asignatura a) {

    }

    @Override
    public List<Asignatura> listarTodos() {
        return null;
    }

    @Override
    public Asignatura buscarPorId(Long id) {
        return null;
    }
}
