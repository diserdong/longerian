package so.mp3.type;

import java.text.DecimalFormat;

import android.net.Uri;
import android.provider.MediaStore;

public class LocalTrack implements Tracker {
	
	private String artist, album, title;
    private int id, duration, size;

    @Override
    public String getArtist() {
        return artist;
    }

    @Override
    public String getAlbum() {
        return album;
    }

    @Override
    public String getSize() {
    	DecimalFormat decimalFormater = new DecimalFormat("0.00");
    	String sizeInStr = null;
    	if(size > 1024 * 1024) {
    		sizeInStr = decimalFormater.format(((double) size / 1024 / 1024 )) + "MB" ;
		} else if(size > 1024) {
			sizeInStr = decimalFormater.format(((double) size / 1024 )) + "KB";
		} else {
			sizeInStr = size + "B";
		}
		return sizeInStr;
	}

    @Override
	public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    @Override
    public int getDuration() {
        return duration;
    }

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public Uri getUri() {
		return Uri.withAppendedPath(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 
                "/"+String.valueOf(id));
	}

}
