package dao.MysqlDAO;

import dao.DAOExecption;
import dao.MatriculaDAO;
import modelo.Matricula;

import java.sql.SQLException;
import java.util.List;

public class MatriculaDAOMySQL implements MatriculaDAO {
    @Override
    public List<Matricula> buscarPorNombre(String nombre) throws DAOExecption {
        return null;
    }

    @Override
    public void insertar(Matricula K) {

    }

    @Override
    public void modificar(Matricula a) {

    }

    @Override
    public void eliminar(Matricula a) {

    }

    @Override
    public List<Matricula> listarTodos() {
        return null;
    }

    @Override
    public Matricula buscarPorId(Long id) {
        return null;
    }
}
