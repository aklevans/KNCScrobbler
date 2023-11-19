package app.kncscrobbler.models;

/**
 * Like a session, but only includes username and session key. Does not include sensitive information such as secret and api key
 */
public class SessionInfo {
	
	private String username;

	
	public SessionInfo(String username) {
		this.username = username;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
