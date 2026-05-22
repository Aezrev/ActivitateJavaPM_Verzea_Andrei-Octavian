package management_biblioteca;

public class Carte {
    private int id;
    private String nume;
    private int pagini;

    public Carte(int id, String nume, int pagini) {
        this.id = id;
        this.nume = nume;
        this.pagini = pagini;
    }

    public Carte() {

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

    public int getPagini() {
        return pagini;
    }

    public void setPagini(int pagini) {
        this.pagini = pagini;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Carte{");
        sb.append("id=").append(id);
        sb.append(", nume='").append(nume).append('\'');
        sb.append(", pagini=").append(pagini);
        sb.append('}');
        return sb.toString();
    }
}
