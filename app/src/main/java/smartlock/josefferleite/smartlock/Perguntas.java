package smartlock.josefferleite.smartlock;

import java.util.ArrayList;

public class Perguntas {


    private String pergunta ;
    private String resp1;
    private String resp2;
    private String resp3;
    private String resposta_certa;
    private String explicacao;

    public Perguntas() {
    }

    public Perguntas( String pergunta, String resp1, String resp2, String resp3, String resposta_certa, String explicacao) {

        this.pergunta = pergunta;
        this.resp1 = resp1;
        this.resp2 = resp2;
        this.resp3 = resp3;
        this.resposta_certa = resposta_certa;
        this.explicacao = explicacao;
    }



    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResp1() {
        return resp1;
    }

    public void setResp1(String resp1) {
        this.resp1 = resp1;
    }

    public String getResp2() {
        return resp2;
    }

    public void setResp2(String resp2) {
        this.resp2 = resp2;
    }

    public String getResp3() {
        return resp3;
    }

    public void setResp3(String resp3) {
        this.resp3 = resp3;
    }

    public String getResposta_certa() {
        return resposta_certa;
    }

    public void setResposta_certa(String resposta_certa) {
        this.resposta_certa = resposta_certa;
    }

    public String getExplicacao() {
        return explicacao;
    }

    public void setExplicacao(String explicacao) {
        this.explicacao = explicacao;
    }
}
