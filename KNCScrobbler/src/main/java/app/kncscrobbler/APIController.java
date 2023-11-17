package app.kncscrobbler;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import app.kncscrobbler.models.ScrobbleRequest;
import app.kncscrobbler.models.SessionInfo;
import de.umass.lastfm.Session;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    
    @GetMapping ( BASE_PATH + "token/{token}" )
    public SessionInfo startSession(@PathVariable ("token") final String token) {
    	Session s = SessionManager.createSession( token );
        return new SessionInfo(s.getKey(), s.getUsername());

    }
    
    
    @PostMapping ( BASE_PATH + "song/" )
    public ResponseEntity<String> scrobbleSong(@RequestBody  String req) {
        SessionManager.scrobbleSong( req );
        return new ResponseEntity<String>( HttpStatus.OK );
    }
    

    
    


}
