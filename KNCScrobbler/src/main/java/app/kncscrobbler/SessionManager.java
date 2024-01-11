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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;


/**
 * This class p
 */
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
        ArrayList<Integer> arraylist;

        return Authenticator.getSession( token, key, secret );
        
    }
    
    /**
     * This method gets a session object representing a previously created session that is tied to a specific user.
     * Sessions are identified by the user's session key which was generated when the session was originally created.
     * The corresponding user's username must be provided as a second identifier.
     * @param sessionKey the session's unique key
     * @param username the username of the session's user
     * @return the session matching the identifiers
     */
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
            //ex.printStackTrace();
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
     * Initiates a scrobble
     * @param json string representation of the song to scobble
     */
    public static boolean scrobbleSong(String str, String key) {
    	Gson gson = new Gson();
    	ScrobbleRequest s = gson.fromJson(str, ScrobbleRequest.class);
    	s.setKey( key );
    	
    	
    	Session session = s.getSession();
    	String artist = s.getArtist();
    	String oldArtist = s.getOldArtist();
    	String title = s.getSong().trim();
    	String oldTitle = s.getOldSong();
    	
    	if(session == null) {
    		return false;
    	}

        
        
        if(!s.isFirst() && s.getOldSong() != null && oldArtist != null && !title.trim().toLowerCase().equals(oldTitle.trim().toLowerCase()) && !artist.trim().toLowerCase().equals(oldArtist.trim().toLowerCase()) ) {
        	
//    		System.out.println("old Artist: [" + t.getArtist() + "] New Artist: [" + artist + "]");
//    		System.out.println("Artist Match:" + t.getArtist().trim().equals( artist.toUpperCase() ));
//        	System.out.println("old Song: [" + t.getName() + "] New Song: [" + title + "]");
//        	System.out.println("Song Match:" + t.getName().trim().toUpperCase().equals( title.toUpperCase() ));
        	
        	System.out.println("Scrobbled: " + oldTitle + " " + oldArtist);
			int now = (int) (System.currentTimeMillis() / 1000);
            ScrobbleResult result2 = Track.scrobble(oldArtist, oldTitle, now, session);
            System.out.println("ok: " + (result2.isSuccessful() && !result2.isIgnored()));
    			

    		
        	
        	// set now playing to new song
            System.out.println("updating to:" + title);
        }
        else {
        	System.out.println("Did not scrobble!");
        }
        
        ScrobbleResult result = Track.updateNowPlaying(artist, title, session);
        //System.out.println("Updated Now Playing!");

        try {
        	//System.out.println("CurrentlyPlaying: " + getLastPlayed(session).getName());
        }
        catch(NullPointerException e) {
        	
        }
        
        System.out.print(result.toString());
        System.out.println("ok: " + (result.isSuccessful() && !result.isIgnored()));
        
        //invalid authentication
        return result.getErrorCode() != 9;
    }
    


}
