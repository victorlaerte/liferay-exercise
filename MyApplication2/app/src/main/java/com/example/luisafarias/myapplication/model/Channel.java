package com.example.luisafarias.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Created by luisafarias on 19/10/17.
 */
@Root(name = "channel", strict = false)
public class Channel implements Parcelable {
	@Element(name = "title") private String title;

	//@Element(name = "link")
	// private String _link;

	@Element(name = "description") private String description;

	@Element(name = "copyright") private String copyright;

	@ElementList(name = "item_body", inline = true) private List<Item> item;

	//private Image image;
	@Element(name = "language") private String language;

	public Channel() {
	}//para desserializar precisa do construtor vazio public

	protected Channel(Parcel in) {
		title = in.readString();
		description = in.readString();
		//this._link = in.readString();
		item = in.createTypedArrayList(Item.CREATOR);
		//item_body = in.createTypedArray(Item.CREATOR);
		language = in.readString();
		copyright = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(description);
		//dest.writeString(this._link);
		dest.writeTypedList(item);
		//dest.writeTypedArray(item_body, flags);
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	//public String getLink ()
	//    {
	//        return this._link;
	//    }

	// public void setLink (String link)
	//    {
	//        this._link = link;
	//    }

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	@Override
	public String toString() {
		return "ClassPojo [title = "
			+ title
			+ ", description = "
			+ description
			+ ", item_body = "
			+ this.item
			+ ", image = "
			+
			/**image+****/", language = "
			+ language
			+ ", copyright = "
			+ copyright
			+ "]";
	}
}
