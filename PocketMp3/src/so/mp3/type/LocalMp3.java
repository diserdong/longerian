package so.mp3.type;

public class LocalMp3 {
	
	private String artist, album, title;
    private int id, duration, size;

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public int getSize() {
		return size;
	}

	public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

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

}
