package com.example.demo;

import com.google.gson.Gson;

import de.umass.lastfm.Authenticator;
import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Session;
import de.umass.lastfm.Track;
import de.umass.lastfm.User;
import de.umass.lastfm.scrobble.ScrobbleResult;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class SessionManager {

    
    private String key;
    
    private String secret;
    
    private Session session;
    
    private static SessionManager instance = new SessionManager();
    
    public SessionManager() {
        try (InputStream input = new FileInputStream("src/main/resources/static/config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            key = prop.getProperty( "key");
            secret = prop.getProperty( "secret" );



        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static SessionManager getInstance() {
        return instance;
    }
    
    public Session getSession() {
        return session;
    }
    
    public Session createSession(String token) {
        session = Authenticator.getSession( token, key, secret );
        return session;
    }
    
    public void scrobbleSong(String title, String artist) {
//        ScrobbleResult result = Track.updateNowPlaying(artist, title, session);
//        System.out.println("ok: " + (result.isSuccessful() && !result.isIgnored()));
        
        
        //check if song has changed 
        // NOTE: this assumes the same song will never be played twice in a row!!!!!

        Track t = (Track)User.getRecentTracks( session.getUsername(), 1, 1, key ).getPageResults().toArray()[0];
        if(!t.getName().equals( title ) || !t.getArtist().equals( artist )) {
            int now = (int) (System.currentTimeMillis() / 1000);
            ScrobbleResult result = Track.scrobble(artist, title, now, session);
            System.out.println("ok: " + (result.isSuccessful() && !result.isIgnored()));
        }
        
        
    }
    
    public void scrobbleSong(String json) {
        Gson g = new Gson();
        Song s = g.fromJson(json, Song.class);
        scrobbleSong( s.getSong() , s.getArtist());
    }
}
