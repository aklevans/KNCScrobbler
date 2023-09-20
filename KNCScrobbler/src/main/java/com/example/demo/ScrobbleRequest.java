package com.example.demo;

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
    
	private String apiKey;
	private String secret;
	private String username;
	private String key;
	private boolean subscriber;
    
    public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public boolean isSubscriber() {
		return subscriber;
	}
	public void setSubscriber(boolean subscriber) {
		this.subscriber = subscriber;
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
    
    public Session getSession() {
    	return Session.createSession(this.apiKey, this.secret, this.key);
    }
    
    
}
