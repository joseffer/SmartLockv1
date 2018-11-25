package smartlock.josefferleite.smartlock;

public class Resps_Status {


   private int certa;
   private  int errada;


    public Resps_Status(int certa, int errada) {
        this.certa = certa;
        this.errada = errada;
    }

    public Resps_Status() {
    }

    public int getCerta() {
        return certa;
    }

    public void setCerta(int certa) {
        this.certa = certa;
    }

    public int getErrada() {
        return errada;
    }

    public void setErrada(int errada) {
        this.errada = errada;
    }
}
