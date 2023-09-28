package com.example.demo;

import com.google.gson.Gson;

import de.umass.lastfm.Authenticator;
import de.umass.lastfm.Session;
import de.umass.lastfm.Track;
import de.umass.lastfm.User;
import de.umass.lastfm.scrobble.ScrobbleResult;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



public class SessionManager {

    
    private static String key;
    
    private static String secret;

    
    private static void loadProperties() {
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

    
    
    public static Session createSession(String token) {
    	loadProperties();
        return Authenticator.getSession( token, key, secret );
    }
    
    public static Track getLastPlayed(Session session) {
    	return (Track)User.getRecentTracks( session.getUsername(), 1, 1, key ).getPageResults().toArray()[0];
    }
    
    public static void scrobbleSong(String title, String artist, Session session) {
//        ScrobbleResult result = Track.updateNowPlaying(artist, title, session);
//        System.out.println("ok: " + (result.isSuccessful() && !result.isIgnored()));
        
        
        //check if song has changed 
        // NOTE: this assumes the same song will never be played twice in a row!!!!! 
        Track t = getLastPlayed(session);
        if(!t.getName().equals( title ) || !t.getArtist().equals( artist )) {
        	System.out.println("old Artist:" + t.getArtist() + " New Artist:" + artist);
        	System.out.println("old Song:" + t.getName() + " New Song:" + title);
            int now = (int) (System.currentTimeMillis() / 1000);
            ScrobbleResult result2 = Track.scrobble(artist, title, now, session);
            System.out.println("ok: " + (result2.isSuccessful() && !result2.isIgnored()));
        }
        
        
    }
    
    public static void scrobbleSong(String json) {
        Gson g = new Gson();
        ScrobbleRequest s = g.fromJson(json, ScrobbleRequest.class);
        scrobbleSong( s.getSong() , s.getArtist(), s.getSession() );
    }
    

}
