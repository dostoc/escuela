package dao.MysqlDAO;

import dao.AlumnoDAO;
import dao.DAO;
import dao.DAOExecption;
import modelo.Alumno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAOMySQL implements AlumnoDAO {


    @Override
    public Long obtenerIndex() throws DAOExecption, SQLException {
        // final String INDEX_ID = "SELECT Auto_increment FROM information_schema.tables WHERE table_schema = 'escuela' AND table_name = 'alumnos';";

        Long index;
        try (
                PreparedStatement stat = conn.prepareStatement(INDEX_ID);
                ResultSet rs = stat.executeQuery();
        ) {
            if(rs.next()) {
                index = rs.getLong("Auto_increment");
                System.out.println("index: "+index);
                return index;
            } else
                return  null;
        }
        catch (SQLException e) {
            throw new DAOExecption("Error sql" , e);
        }
    }

    final String INSERTSQL = "INSERT INTO alumnos(id_alumno, nombre, apellido, fecha_nac) VALUES (?,?,?,?)";
    final String UPDATESQL = "UPDATE alumnos SET nombre = ?, apellido = ?, fecha_nac = ? WHERE id_alumno  = ?";
    final String DELETESQL = "DELETE FROM alumnos WHERE id_alumno = ?";
    final String GETALLSQL = "SELECT id_alumno, nombre, apellido, fecha_nac FROM alumnos";
    final String GETONESQL = "SELECT id_alumno, nombre, apellido, fecha_nac FROM alumnos WHERE id_alumno = ?";
    final String GETNAME = "SELECT id_alumno, nombre, apellido, fecha_nac FROM alumnos WHERE nombre = ?";

    final String INDEX_ID = "SELECT Auto_increment FROM information_schema.tables WHERE table_schema = 'escuela' AND table_name = 'alumnos';";


    private Connection conn;



    public AlumnoDAOMySQL(Connection conn) {
        this.conn = conn;
    }


    @Override
    public Alumno crearObjeto() throws DAOExecption, SQLException {
        Alumno a = new Alumno("Lorenzo","Lamas",new Date(1982,03,01));
        try {
            a.setId(obtenerIndex()+1);
        } catch (SQLException e) {
            throw new DAOExecption("Error a cargar index", e);
        }
        return a;
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
            stat.setLong(1, a.getId());
            if (!stat.execute()) {
                throw new DAOExecption("Error al borrar");
            }
        } catch (SQLException ex) {
            throw new DAOExecption("Error en SQL", ex);
        } finally {
            if (stat != null) {
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


    @Override
    public List<Alumno> buscarPorNombre(String nombre) throws DAOExecption {
        List<Alumno> alumnos = new ArrayList<>();
        Alumno a = null;
        PreparedStatement stat = null;
        ResultSet rs = null;


        try {
            stat = conn.prepareStatement(GETNAME);
            stat.setString(1, nombre);
            rs = stat.executeQuery();

            while (rs.next()) {
                alumnos.add(convertir(rs));
            }

        } catch (SQLException ex) {
            throw new DAOExecption("Error en en la sql", ex);
        } finally {
            // cierre de conexiones rs - conn
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    throw new DAOExecption("Error al cerrar conexion", ex);
                }
            }
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    throw new DAOExecption("Error al cerrar Statments", ex);
                }
            }
        }
        return alumnos;
    }


    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        Alumno a = null;



        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/escuela", "root", "root");
            System.out.println(conn.getMetaData().getDriverName()); //con esto corroboramos que no sea null
            AlumnoDAO dao = new AlumnoDAOMySQL(conn);



            /*
            List<Alumno> alumnos = dao.listarTodos();
            */

            /*
            List<Alumno> alumnos = dao.buscarPorNombre("Jonatan");
            if (!alumnos.isEmpty()) {
                for (Alumno al : alumnos) {
                    System.out.println(al);
                }
            } else {
                System.out.println("Lista vacia");
            }
            */

            /*
            dao.eliminar(alumnos.get(0));
            */

            dao.insertar(dao.crearObjeto());


        } catch (SQLException | DAOExecption ex) {
            new DAOExecption("Algo fallo", ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                new DAOExecption("Aglo fallo", ex);
            }
        }
    }
}
