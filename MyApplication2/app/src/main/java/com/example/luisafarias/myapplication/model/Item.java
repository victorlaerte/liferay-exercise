package com.example.luisafarias.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by luisafarias on 19/10/17.
 */
@Root(name = "item", strict = false)
public class Item implements Parcelable {
	@Element(name = "pubDate", required = false) private String pubDate;
	@Element(name = "title", required = false) private String title;
	@Element(name = "description", required = false) private String description;
	@Element(name = "link", required = false) private String link;

	public Item() {
	}

	;//para desserializar precisa do construtor vazio public

	protected Item(Parcel in) {
		pubDate = in.readString();
		title = in.readString();
		description = in.readString();
		link = in.readString();
	}

	public static final Creator<Item> CREATOR = new Creator<Item>() {
		@Override
		public Item createFromParcel(Parcel in) {
			return new Item(in);
		}

		@Override
		public Item[] newArray(int size) {
			return new Item[size];
		}
	};

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return "ClassPojo [pubDate = "
			+ pubDate
			+ ", title = "
			+ title
			+ ", description = "
			+ description
			+ ", link = "
			+ link
			+ "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(pubDate);
		dest.writeString(title);
		dest.writeString(description);
		dest.writeString(link);
	}
}
