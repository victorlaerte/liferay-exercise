package model;

/**
 * Created by luisafarias on 26/09/17.
 */

public class Feed {
    private String nome;
    private String url;
    private String userId;

    public Feed(String nome, String url, String userId){
        this.nome = nome;
        this.url = url;
    }

    public String getNome(){ return this.nome;}

    public String getUrl(){ return this.url;}

    public void setNome(String nome){ this.nome = nome;}

    public void setUrl(String url){ this.url = url;}

    public String getUserId(){ return this.userId;}

}
