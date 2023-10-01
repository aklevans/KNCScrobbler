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
import java.util.Collection;
import java.util.Properties;



public class SessionManager {

    


    
    private static void loadProperties() {

    }

    
    
    public static Session createSession(String token) {
        try (InputStream input = new FileInputStream("src/main/resources/static/config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            String key = prop.getProperty( "key");
            String secret = prop.getProperty( "secret" );

            return Authenticator.getSession( token, key, secret );

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        
    }
    
    public static Track getLastPlayed(Session session) {
    	try {
    		//User.getRecentTracks(secret, key)
    		//Collection r = User.getRecentTracks( session.getUsername(), 1, 10, session.getApiKey() ).getPageResults();
    		return (Track)User.getRecentTracks( session.getUsername(), 1, 10, session.getApiKey() ).getPageResults().toArray()[0];
    	} catch(ArrayIndexOutOfBoundsException e) {
    		return null;
    	}
    }
    
    public static void startNext(String title, String artist, Session session) {
    	

        //check if song has changed 
        // NOTE: this assumes the same song will never be played twice iS a row!!!!! 
        Track t = getLastPlayed(session);
        if(t != null && !t.getName().trim().equals( title ) || !t.getArtist().trim().equals( artist )) {

    		System.out.println("old Artist: \"" + t.getArtist() + "\" New Artist: \"" + artist + "\"");
        	System.out.println("old Song: \"" + t.getName() + "\" New Song: \"" + title + "\"");
        	
        	//scrobble last track that was playing
        	if(t.isNowPlaying()) {
        		int now = (int) (System.currentTimeMillis() / 1000);

                ScrobbleResult result2 = Track.scrobble(t.getArtist(), t.getName(), now, session);
                System.out.println("ok: " + (result2.isSuccessful() && !result2.isIgnored()));
        	}
        }
        
    	// set now playing to new song
        ScrobbleResult result = Track.updateNowPlaying(artist, title, session);
        System.out.println("ok: " + (result.isSuccessful() && !result.isIgnored()));
        
        
    }
    
    public static void scrobbleSong(String json) {
        Gson g = new Gson();
        ScrobbleRequest s = g.fromJson(json, ScrobbleRequest.class);
        startNext( s.getSong().trim() , s.getArtist().trim(), s.getSession() );
    }
    

}
