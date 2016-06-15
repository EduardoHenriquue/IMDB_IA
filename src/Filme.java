/**
 * Created by Eduardo on 12/06/2016.
 */
public class Filme {

    private int ator1;
    private int ator2;
    private int ator3;
    private int ator4;
    private int diretor;
    private int nota; // rótulo

    public Filme(int diretor, int ator1, int ator2, int ator3, int ator4, int nota) {
        this.ator1 = ator1;
        this.ator2 = ator2;
        this.ator3 = ator3;
        this.ator4 = ator4;
        this.diretor = diretor;
        this.nota = nota;
    }

    public int getAtor1() {
        return ator1;
    }

    public void setAtor1(int ator1) {
        this.ator1 = ator1;
    }

    public int getAtor2() {
        return ator2;
    }

    public void setAtor2(int ator2) {
        this.ator2 = ator2;
    }

    public int getAtor3() {
        return ator3;
    }

    public void setAtor3(int ator3) {
        this.ator3 = ator3;
    }

    public int getAtor4() {
        return ator4;
    }

    public void setAtor4(int ator4) {
        this.ator4 = ator4;
    }

    public int getDiretor() {
        return diretor;
    }

    public void setDiretor(int diretor) {
        this.diretor = diretor;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}
