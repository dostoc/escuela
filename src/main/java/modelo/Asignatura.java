package modelo;

import java.util.Objects;

public class Asignatura {

    private Long id = null;

    private String nombre;

    /*
    se utiliza in idProfesor Long porque este puede que devuelva nulo, y no se utiliza un objeto profesor porque eso haria
    que cada vez que realicemos una peticion de Matricula se ejecute una peticion de DaoProfesor para deolver el profesor,
     de esta manera solo retorna una id en el propio objeto que si se necesitase se utiliza
     */
    private Long idProfesor;


    public Asignatura(Long id, String nombre, Long idProfesor) {
        this.id = id;
        this.nombre = nombre;
        this.idProfesor = idProfesor;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Asignatura{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", idProfesor=" + idProfesor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Asignatura that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(nombre, that.nombre) && Objects.equals(idProfesor, that.idProfesor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), nombre, idProfesor);
    }
}
