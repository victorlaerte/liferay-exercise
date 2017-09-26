package model;

/**
 * Created by luisafarias on 26/09/17.
 */

public class Feed {
    String nome;
    String url;

    public Feed(String nome, String url){
        this.nome = nome;
        this.url = url;
    }

    public String getNome(){ return this.nome;}

    public String getUrl(){ return this.url;}

    public void setNome(String nome){ this.nome = nome;}

    public void setUrl(String url){ this.url = url;}
}
