package br.edu.insper.al.laisns.projeto;

public class Localizar {

    private String localidade;
    private String  bairro;

    public Localizar (String localidade, String  bairro){
        this.localidade = localidade;
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public String getBairro() {
        return bairro;
    }
}
