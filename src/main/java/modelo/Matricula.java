package modelo;

import java.util.Objects;

public class Matricula {


    private long alumno;

    private long asignatura;

    private int year;

    private Integer nota = null;


    public Matricula(long alumno, long asignatura, int year, Integer nota) {
        this.alumno = alumno;
        this.asignatura = asignatura;
        this.year = year;
        this.nota = nota;
    }

    public long getAlumno() {
        return alumno;
    }

    public void setAlumno(long alumno) {
        this.alumno = alumno;
    }

    public long getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(long asignatura) {
        this.asignatura = asignatura;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Matricula matricula)) return false;
        return getAlumno() == matricula.getAlumno() && getAsignatura() == matricula.getAsignatura() && getYear() == matricula.getYear() && Objects.equals(getNota(), matricula.getNota());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAlumno(), getAsignatura(), getYear(), getNota());
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "alumno=" + alumno +
                ", asignatura=" + asignatura +
                ", year=" + year +
                ", nota=" + nota +
                '}';
    }


}
