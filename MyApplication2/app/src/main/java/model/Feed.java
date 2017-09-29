package model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luisafarias on 26/09/17.
 */

public class Feed implements Parcelable {
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

    protected Feed(Parcel in) {
        nome = in.readString();
        url = in.readString();
        userId = in.readString();
        id = in.readString();
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };

    public String getNome(){ return this.nome;}

    public String getUrl(){ return this.url;}

    public void setNome(String nome){ this.nome = nome;}

    public void setUrl(String url){ this.url = url;}

    public String getUserId(){ return this.userId;}

    public String getId(){ return this.id;}

    public void setId(String id){ this.id = id;}

    public void setUserId(String userId){ this.userId = userId;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nome);
        parcel.writeString(url);
        parcel.writeString(userId);
        parcel.writeString(id);
    }
}
