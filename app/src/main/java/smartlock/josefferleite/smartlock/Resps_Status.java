package smartlock.josefferleite.smartlock;

public class Resps_Status {


private int certo;
private int errado;

    public Resps_Status(int certo, int errado) {
        this.certo = certo;
        this.errado = errado;
    }

    public Resps_Status() {
        this.certo = 0;
        this.errado =0;
    }

    public int getCerto() {
        return certo;
    }

    public void setCerto(int certo) {
        this.certo = certo;
    }

    public int getErrado() {
        return errado;
    }

    public void setErrado(int errado) {
        this.errado = errado;
    }
}
