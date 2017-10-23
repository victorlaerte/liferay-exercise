package com.example.luisafarias.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by luisafarias on 19/10/17.
 */
@Root(strict = false)
public class Channel implements Parcelable{
    @Attribute(name = "title")
    private String title;

    @Attribute(name = "link")
    private String link;

    @Attribute(name = "description")
    private String description;

    @Attribute(name = "copyright")
    private String copyright;

    @ElementList(name = "item", inline = true)
    private List<FeedItem> item;

    //private Image image;
    @Attribute(name = "language")
    private String language;

    public Channel(){}

    public Channel(String title, String link, String description, String copyright, List<FeedItem> item, String language){
        setTitle(title);
        setLink(link);
        setDescription(description);
        setCopyright(copyright);
        setItem(item);
        setLanguage(language);
    }

    protected Channel(Parcel in) {
        title = in.readString();
        description = in.readString();
        link = in.readString();
        item = in.createTypedArrayList(FeedItem.CREATOR);
        //item = in.createTypedArray(FeedItem.CREATOR);
        language = in.readString();
        copyright = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(link);
        dest.writeTypedList(item);
        //dest.writeTypedArray(item, flags);
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

    public List<FeedItem> getItem ()
    {
        return item;
    }

    public void setItem (List<FeedItem> item)
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
        return "ClassPojo [title = "+title+", description = "+description+", link = "+link+", item = "+item+", image = "+/**image+****/", language = "+language+", copyright = "+copyright+"]";
    }
}
