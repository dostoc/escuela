package dao.MysqlDAO;

import dao.AlumnoDAO;
import dao.DAOExecption;
import modelo.Alumno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAOMySQL implements AlumnoDAO {

    final String INSERTSQL = "INSERT INTO alumnos(id_alumno, nombre, apellido, fecha_nac) VALUES (?,?,?,?)";
    final String UPDATESQL = "UPDATE alumnos SET nombre = ?, apellido = ?, fecha_nac = ? WHERE id_alumno  = ?";
    final String DELETESQL = "DELETE FROM alumnos WHERE id_alumno = ?";
    final String GETALLSQL = "SELECT id_alumno, nombre, apellido, fecha_nac FROM alumnos";
    final String GETONESQL = "SELECT id, nombre, apellido, fecha_nac FROM alumnos WHERE id_alumno = ?";
    final String GETNAME =  "SELECT id, nombre, apellido, fecha_nac FROM alumnos WHERE name = ?";



    private Connection conn;

    public AlumnoDAOMySQL(Connection conn) {
        this.conn = conn;
    }

    private Alumno convertir(ResultSet rs) throws SQLException {
        String nombre = rs.getString("nombre");
        String apellido = rs.getString("apellido");
        Date fechaNacimiento = rs.getDate("fecha_nac");
        Alumno alumno = new Alumno(nombre, apellido, fechaNacimiento);
        alumno.setId(rs.getLong("id_alumno"));
        return alumno;
    }


    /*
    INSERT INTO alumnos(id_alumno, nombre, apellido, feccha_mac) VALUES (50,"PACO","PEREZ",2015-01-01);
    */
    public void insertar(Alumno a) throws DAOExecption {
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(INSERTSQL);
            stat.setLong(1, a.getId());
            stat.setString(2, a.getNombre());
            stat.setString(3, a.getApellido());
            stat.setDate(4, new Date(a.getFechaNacimiento().getTime()));
            if (stat.executeUpdate() == 0) {
                throw new DAOExecption("Puede que no se haya guardado");
            }

        } catch (SQLException ex) {
            throw new DAOExecption("Error en SQL", ex);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    throw new DAOExecption("Error en sql", ex);
                }
            }
        }

    }



    @Override
    public void modificar(Alumno a) throws DAOExecption {
        // "UPDATE alumnos SET nombre = ?, apellido = ?, fecha_nac = ? WHERE id_alumno  = ?";
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(UPDATESQL);
            stat.setString(1, a.getNombre());
            stat.setString(2, a.getApellido());
            stat.setDate(3, new Date(a.getFechaNacimiento().getTime()));
            stat.setLong(4, a.getId());
            if (stat.executeUpdate() == 0) {
                throw new DAOExecption("Puede que no se haya guardado");
            }
        } catch (SQLException ex) {
            throw new DAOExecption("Error en SQL", ex);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    throw new DAOExecption("Error de SQL", ex);
                }
            }
        }
    }

    @Override
    public void eliminar(Alumno a) throws DAOExecption {
        // "DELETE FROM alumnos WHERE id_alumno = ?";
        PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(DELETESQL);
            stat.setLong(1,a.getId());
            if (!stat.execute()) {
                throw new DAOExecption("Error al borrar");
            }
        }catch (SQLException ex) {
            throw new DAOExecption("Error en SQL", ex);
        }finally {
            if (stat!= null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    new DAOExecption("Error al cerrar conexion");
                }
            }
        }




    }

    @Override
    public List<Alumno> listarTodos() throws DAOExecption {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Alumno> alumnos = new ArrayList<>();
        Long id = null;
        try {
            stat = conn.prepareStatement(GETALLSQL);
            rs = stat.executeQuery();

            while (rs.next()) {
                alumnos.add(convertir(rs));
            }


        } catch (SQLException ex) {
            throw new DAOExecption("Error en sql", ex);
        } finally {
            if (rs != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    throw new DAOExecption("Error de SQL");
                }
            }

            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    throw new DAOExecption("Error de SQL");
                }
            }

        }
        return alumnos;
    }

    @Override
    public Alumno buscarPorId(Long id) throws DAOExecption, SQLException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Alumno a = null;
        try {
            stat = conn.prepareStatement(GETONESQL);
            stat.setLong(1, id);
            rs = stat.executeQuery();
            if (rs.next()) {
                a = convertir(rs);
            } else {
                throw new DAOExecption("No se encontro el registro");
            }
        } catch (SQLException ex) {
            throw new DAOExecption("Error en sql", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    throw new DAOExecption("Error al cerrar conexion");
                }
            }
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    throw new DAOExecption("Error al cerrar");
                }
            }
        }
        return a;
    }



    public static void main(String[] args) throws SQLException {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/escuela","root","root");
            System.out.println(conn.getMetaData().getDriverName()); //con esto corroboramos que no sea null
            AlumnoDAO dao = new AlumnoDAOMySQL(conn);
            List<Alumno> alumnos = dao.listarTodos();
            if (alumnos.size() == 0) {
                System.out.println("Lista vacia");
            }
            for(Alumno a : alumnos){
                System.out.println(a);
            }
            dao.eliminar(alumnos.get(0));

        } catch (SQLException | DAOExecption ex) {
            new DAOExecption("Algo fallo", ex);
        }finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                new DAOExecption("Aglo fallo", ex);
            }
        }
    }


}
