package br.edu.insper.al.gabrielamb2.projeto2;

public class Cliente {
    private String cliente;
    private String infill;
    private String layer;
    private String impressoras;
    private String materiais;
    private String mao_de_obra;
    private String peso;
    private String tempo;
    private String valor;
    public Cliente(String cliente1, String infill1,String layer1,String impressoras1,String materiais1,String mao_de_obra1, String peso1, String tempo1, String valor1){
        this.cliente= cliente1;
        this.infill= infill1;
        this.layer= layer1;
        this.impressoras= impressoras1;
        this.materiais= materiais1;
        this.mao_de_obra= mao_de_obra1;
        this.peso= peso1;
        this.tempo= tempo1;
         this.valor= valor1;
    }



    public String getCliente() {
        return cliente;
    }

    public String getImpressoras() {
        return impressoras;
    }

    public String getInfill() {
        return infill;
    }
    public String getLayer() {
        return layer;
    }

    public String getMao_de_obra() {
        return mao_de_obra;
    }

    public String getpeso() {
        return peso;
    }

    public String getMateriais() {
        return materiais;
    }

    public String gettempo() {
        return tempo;
    }

    public String getValor() {
        return valor;
    }

}
