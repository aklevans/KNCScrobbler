package app.kncscrobbler;

import com.google.gson.Gson;

import app.kncscrobbler.models.ScrobbleRequest;
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

    
    /**
     * Creates a session given an authentication token
     * @param token the authentication token
     * @return A String json representation of a Session object
     */
    public static Session createSession(String token) {
    	String key;
        String secret;
        try (InputStream input = new FileInputStream("src/main/resources/static/config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            
            // get the property value and print it out
            key = prop.getProperty("key");
            secret = prop.getProperty( "secret" );


        } catch (IOException ex) {
            ex.printStackTrace();
            key = System.getenv("KEY");
            secret = System.getenv("SECRET");
            
            
        }
        
        return Authenticator.getSession( token, key, secret );

        
    }
    
    public static Session getSessionFromKey(String sessionKey, String username) {
    	String api_key;
        String secret;
        try (InputStream input = new FileInputStream("src/main/resources/static/config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            
            // get the property value and print it out
            api_key = prop.getProperty( "key");
            secret = prop.getProperty( "secret" );


        } catch (IOException ex) {
            ex.printStackTrace();
            api_key = System.getenv("KEY");
            secret = System.getenv("SECRET");
            
            
        }
        
        return Session.createSession(api_key, secret, sessionKey, username, false);
    }
    

    
    /**
     * Gets the last played song
     * @param session the authenticated session
     * @return the last played song or null if one cannot be found
     */
    public static Track getLastPlayed(Session session) {
    	try {
    		//User.getRecentTracks(secret, key)
    		//Collection r = User.getRecentTracks( session.getUsername(), 1, 10, session.getApiKey() ).getPageResults();
    		return (Track)User.getRecentTracks( session.getUsername(), 1, 10, session.getApiKey() ).getPageResults().toArray()[0];
    	} catch(ArrayIndexOutOfBoundsException e) {
    		return null;
    	}
    }
    
    /**
     * Gets the second last played song
     * @param session the authenticated session
     * @return	 the second last played song or null if one cannot be found
     */
    public static Track getLastLastPlayed(Session session) {
    	try {
    		//User.getRecentTracks(secret, key)
    		//Collection r = User.getRecentTracks( session.getUsername(), 1, 10, session.getApiKey() ).getPageResults();
    		return (Track)User.getRecentTracks( session.getUsername(), 1, 10, session.getApiKey() ).getPageResults().toArray()[1];
    	} catch(ArrayIndexOutOfBoundsException e) {
    		return null;
    	}
    }
    
    /**
     * Sets the currently playing song on Last.fm. Scrobbles the currently playing song if it has not already been scrobbled
     * and if this isnt the first time the method has been called since the page has been refreshed (This avoids duplicate scrobbling
     * when changing from HD1 to HD2 and back again)
     * 
     * @param title the title of the song to start playing
     * @param artist the artist of the song to start playing
     * @param session the authenticated session
     * @param isFirst true if this is the first time the method has been called since refreshing the page
     */
    public static void startNext(String title, String artist, Session session, boolean isFirst) {
    	

        //check if song has changed 
        // NOTE: this assumes the same song will never be played twice iS a row!!!!! 
    	if(session == null) {
    		return;
    	}
        Track t = getLastPlayed(session);
        Track t2 = getLastLastPlayed(session);
        if(t != null && (!t.getName().trim().toUpperCase().equals( title.toUpperCase() ) || !t.getArtist().trim().toUpperCase().equals( artist.toUpperCase() )) ) {
        	
    		System.out.println("old Artist: [" + t.getArtist() + "] New Artist: [" + artist + "]");
    		System.out.println("Artist Match:" + t.getArtist().trim().equals( artist.toUpperCase() ));
        	System.out.println("old Song: [" + t.getName() + "] New Song: [" + title + "]");
        	System.out.println("Song Match:" + t.getName().trim().toUpperCase().equals( title.toUpperCase() ));
        	
        	//scrobble last track that was playing
        	if(!isFirst && t.isNowPlaying()) {
        		System.out.println("t2: " + t2.getName());
        		if(t2 != null && !t2.equals(t)) {
        			int now = (int) (System.currentTimeMillis() / 1000);
                    ScrobbleResult result2 = Track.scrobble(t.getArtist(), t.getName(), now, session);
                    System.out.println("ok: " + (result2.isSuccessful() && !result2.isIgnored()));
        		}
        	}
        	
        	// set now playing to new song
            System.out.println("updating to:" + title);
        }
        ScrobbleResult result = Track.updateNowPlaying(artist, title, session);
        try {
        	System.out.println("CurrentlyPlaying:" + getLastPlayed(session).getName());
        }
        catch(NullPointerException e) {
        	
        }
        System.out.print(result.toString());
        System.out.println("ok: " + (result.isSuccessful() && !result.isIgnored()));
        

    }
    
    /**
     * Initiates a scrobble
     * @param json string representation of the song to scobble
     */
    public static void scrobbleSong(String str) {
    	Gson gson = new Gson();
    	ScrobbleRequest s = gson.fromJson(str, ScrobbleRequest.class);
    	startNext( s.getSong().trim() , s.getArtist().trim(), s.getSession(), s.isFirst() );
    }

}
