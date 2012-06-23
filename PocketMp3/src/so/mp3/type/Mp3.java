package so.mp3.type;

import android.os.Parcel;
import android.os.Parcelable;

public class Mp3 implements Parcelable {
	
	private String title;
	private String singer;
	private String album;
	private String playerLink;
	private String mp3Link;
	private String size;
	public Mp3() {
		super();
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getPlayerLink() {
		return playerLink;
	}
	public void setPlayerLink(String link) {
		this.playerLink = link;
	}
	public String getMp3Link() {
		return mp3Link;
	}
	public void setMp3Link(String mp3Link) {
		this.mp3Link = mp3Link;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	@Override
	public String toString() {
		return "Mp3 [title=" + title + ", singer=" + singer + ", album="
				+ album + ", link=" + playerLink + ", size=" + size + "]";
	}
	
	public Mp3(Parcel in) {
		readFromParcel(in);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(singer);
		dest.writeString(album);
		dest.writeString(playerLink);
		dest.writeString(mp3Link);
		dest.writeString(size);
	}
	
	@SuppressWarnings("unchecked")
	private void readFromParcel(Parcel in) {
		title = in.readString();
		singer = in.readString();
		album = in.readString();
		playerLink = in.readString();
		mp3Link = in.readString();
		size = in.readString();
	}
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = 
		new Parcelable.Creator() {
	
            public Mp3 createFromParcel(Parcel in) {
                return new Mp3(in);
            }
 
            public Mp3[] newArray(int size) {
                return new Mp3[size];
            }
            
        };
        
}
