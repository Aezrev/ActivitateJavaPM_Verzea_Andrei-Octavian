package org.example;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Student {
    private int id;
    private String nume;
    private int clasa;
    private double nota;

    public Student() {
    }

    public Student(int id, String nume, int clasa, double nota) {
        this.id = id;
        this.nume = nume;
        this.clasa = clasa;
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getClasa() {
        return clasa;
    }

    public void setClasa(int clasa) {
        this.clasa = clasa;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Student{");
        sb.append("id=").append(id);
        sb.append(", nume='").append(nume).append('\'');
        sb.append(", clasa=").append(clasa);
        sb.append(", nota=").append(nota);
        sb.append('}');
        return sb.toString();
    }

}
