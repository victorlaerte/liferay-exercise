package com.example.luisafarias.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by luisafarias on 19/10/17.
 */
@Root(strict = false)
public class Channel implements Parcelable{
    private String title;

    private String category;

    private String description;

    private String link;

    private FeedItem[] item;

    //private Image image;

    private String language;

    private String copyright;

    protected Channel(Parcel in) {
        title = in.readString();
        category = in.readString();
        description = in.readString();
        link = in.readString();
        item = in.createTypedArray(FeedItem.CREATOR);
        language = in.readString();
        copyright = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(category);
        dest.writeString(description);
        dest.writeString(link);
        dest.writeTypedArray(item, flags);
        dest.writeString(language);
        dest.writeString(copyright);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Channel> CREATOR = new Creator<Channel>() {
        @Override
        public Channel createFromParcel(Parcel in) {
            return new Channel(in);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getCategory ()
    {
        return category;
    }

    public void setCategory (String category)
    {
        this.category = category;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getLink ()
    {
        return link;
    }

    public void setLink (String link)
    {
        this.link = link;
    }

    public FeedItem[] getItem ()
    {
        return item;
    }

    public void setItem (FeedItem[] item)
    {
        this.item = item;
    }

   // public Image getImage ()
   // {
   //     return image;
   // }

   // public void setImage (Image image)
   // {
   //     this.image = image;
   // }

    public String getLanguage ()
    {
        return language;
    }

    public void setLanguage (String language)
    {
        this.language = language;
    }

    public String getCopyright ()
    {
        return copyright;
    }

    public void setCopyright (String copyright)
    {
        this.copyright = copyright;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [title = "+title+", category = "+category+", description = "+description+", link = "+link+", item = "+item+", image = "+/**image+****/", language = "+language+", copyright = "+copyright+"]";
    }
}
