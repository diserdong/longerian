package so.mp3.type;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class OnlineTrack implements Parcelable, Tracker {
	
	private String title;
	private String artist;
	private String album;
	private String playerLink;
	private String mp3Link;
	private String size;
	
	@Override
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	@Override
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
	
	@Override
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	@Override
	public String toString() {
		return "Mp3 [title=" + title + ", singer=" + artist + ", album="
				+ album + ", link=" + playerLink + ", size=" + size + "]";
	}
	
	public OnlineTrack(Parcel in) {
		readFromParcel(in);
	}
	
	public OnlineTrack() {
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(artist);
		dest.writeString(album);
		dest.writeString(playerLink);
		dest.writeString(mp3Link);
		dest.writeString(size);
	}
	
	@SuppressWarnings("unchecked")
	private void readFromParcel(Parcel in) {
		title = in.readString();
		artist = in.readString();
		album = in.readString();
		playerLink = in.readString();
		mp3Link = in.readString();
		size = in.readString();
	}
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = 
		new Parcelable.Creator() {
	
            public OnlineTrack createFromParcel(Parcel in) {
                return new OnlineTrack(in);
            }
 
            public OnlineTrack[] newArray(int size) {
                return new OnlineTrack[size];
            }
            
        };
	@Override
	public Uri getUri() {
		if(mp3Link != null) {
			return Uri.parse(mp3Link);
		}
		return null;
	}
	@Override
	public String getArtist() {
		return artist;
	}
	@Override
	public int getDuration() {
		//not available
		return -1;
	}
        
}
