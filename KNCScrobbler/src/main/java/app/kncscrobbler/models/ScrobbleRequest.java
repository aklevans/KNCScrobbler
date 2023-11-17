package app.kncscrobbler.models;

import app.kncscrobbler.SessionManager;
import de.umass.lastfm.Session;

public class ScrobbleRequest {
    private String artist;
    private String duration;
    private String end;
    private String id;
    private String playlist_id;
    private String song;
    private String start;
    private String timezone;
    

	private String key;
	
	boolean first;
    

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public String getArtist () {
        return artist;
    }
    public void setArtist ( String artist ) {
        this.artist = artist;
    }
    public String getDuration () {
        return duration;
    }
    public void setDuration ( String duration ) {
        this.duration = duration;
    }
    public String getEnd () {
        return end;
    }
    public void setEnd ( String end ) {
        this.end = end;
    }
    public String getId () {
        return id;
    }
    public void setId ( String id ) {
        this.id = id;
    }
    public String getPlaylist_id () {
        return playlist_id;
    }
    public void setPlaylist_id ( String playlist_id ) {
        this.playlist_id = playlist_id;
    }
    public String getSong () {
        return song;
    }
    public void setSong ( String song ) {
        this.song = song;
    }
    public String getStart () {
        return start;
    }
    public void setStart ( String start ) {
        this.start = start;
    }
    public String getTimezone () {
        return timezone;
    }
    public void setTimezone ( String timezone ) {
        this.timezone = timezone;
    }
    

    
    public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	public Session getSession() {
    	return SessionManager.getSessionFromKey(key);
    }
    
    
}
