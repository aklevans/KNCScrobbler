package com.example.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String startSession(@PathVariable ("token") final String token) {
        SessionManager.getInstance().createSession( token );
        
        return "IT WORKED OK";

    }
    
    @PostMapping ( BASE_PATH + "song/" )
    public ResponseEntity<String> scrobbleSong(@RequestBody  String info) {
        SessionManager.getInstance().scrobbleSong( info );
        return new ResponseEntity<String>( HttpStatus.OK );
    }
    
    
    
    


}
