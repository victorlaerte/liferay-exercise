package model;

/**
 * Created by luisafarias on 26/09/17.
 */

public class Feed {
    private String nome;
    private String url;
    private String userId;
    private String id;

    public Feed(){}

    public Feed(String nome, String url, String userId){
        this.nome = nome;
        this.url = url;
        this.userId = userId;
    }

    public String getNome(){ return this.nome;}

    public String getUrl(){ return this.url;}

    public void setNome(String nome){ this.nome = nome;}

    public void setUrl(String url){ this.url = url;}

    public String getUserId(){ return this.userId;}

    public String getId(){ return this.id;}

    public void setId(String id){ this.id = id;}

    public void setUserId(String userId){ this.userId = userId;}

}
