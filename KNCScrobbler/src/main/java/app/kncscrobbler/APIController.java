package app.kncscrobbler;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import app.kncscrobbler.models.ScrobbleRequest;
import app.kncscrobbler.models.SessionInfo;
import de.umass.lastfm.Session;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.net.HttpCookie;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController 
public class APIController {

    static final protected String BASE_PATH = "/api/";
    
    
    @GetMapping(BASE_PATH + "test")
    public String test(){
        
        return "GOOGAS";
    }
    
    @PostMapping ( BASE_PATH + "token/{token}" )
    public SessionInfo startSession(@PathVariable ("token") final String token, HttpServletResponse response) {
    	Session s = SessionManager.createSession( token );
    	
    	Cookie cookie = new Cookie("sessionKey", s.getKey());  
    	cookie.setMaxAge(30 * 24 * 60 * 60); // expires in 30 days
    	cookie.setSecure(true);
    	cookie.setHttpOnly(true);
    	cookie.setPath("/"); // global cookie accessible every where
        response.addCookie(cookie);

    	
//    	Cookie testCookie = new Cookie("test", "amog");
//        testCookie.setPath("/"); // global cookie accessible every where
//    	response.addCookie( testCookie );
    	
    	SessionInfo r = new SessionInfo(s.getUsername());
        return r;

    }
    
    
    @PostMapping ( BASE_PATH + "song/" )
    public ResponseEntity<String> scrobbleSong(@RequestBody  String req, @CookieValue(value = "sessionKey", defaultValue = "") String key ) {
        SessionManager.scrobbleSong( req, key );
        return new ResponseEntity<String>( HttpStatus.OK );
    }
    
    @PutMapping(BASE_PATH + "reset/")
    public void scrobbleSong(HttpServletResponse response) {
        Cookie cookie = new Cookie("sessionKey", null);
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        //add cookie to response
        response.addCookie(cookie);
    }
    

    
    


}
