package dao.MysqlDAO;

import conexion.Conexion;
import dao.DAOExecption;
import dao.ProfesorDAO;
import modelo.Profesor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProfesorDAOMySQL implements ProfesorDAO {

    private Connection conn;

    public ProfesorDAOMySQL(Connection conn){
        this.conn = conn;
    }

    private String INSERTAR = "INSERT INTO profesores(nombre,apellido) VALUES ( ? , ? )";
    private String MODIFICAR = ""
    private String BORRAR;
    private String LISTAR_TODOS;
    private String BUSCAR_ID;



    @Override
    public Long obtenerIndex() throws DAOExecption, SQLException {
        return 0L;
    }

    @Override
    public Profesor crearObjeto() throws DAOExecption, SQLException {
        return null;
    }

    @Override
    public List<Profesor> buscarPorNombre(String nombre) throws DAOExecption {
        return null;
    }

    @Override
    public void insertar(Profesor p) throws DAOExecption {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(INSERTAR);
            stat.setString(1,p.getNombre());
            stat.setString(2,p.getApellido());
            if(stat.executeUpdate() == 0){
                throw new DAOExecption("falla al guardar");
            }
        } catch (SQLException e) {
            throw new DAOExecption("Error SQL", e);
        }finally {
            if (stat!= null) {
                try {
                    stat.close();
                } catch (SQLException e) {
                    throw new DAOExecption("Error SQL", e);
                }
            }
        }
    }

    @Override
    public void modificar(Profesor a) {
        //update
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(MODIFICAR);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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


    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/escuela","root","root");
            ProfesorDAO dao = new ProfesorDAOMySQL(conn);
            /*
            dao.insertar(new Profesor("Juancho","Gomez"));
            */
            dao.modificar();

        } catch (DAOExecption e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn!= null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


}
