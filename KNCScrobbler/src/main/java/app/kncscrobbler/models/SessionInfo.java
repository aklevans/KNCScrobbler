package app.kncscrobbler.models;

/**
 * Like a session, but only includes username and session key. Does not include sensitive information such as secret and api key
 */
public class SessionInfo {
	private String key;
	
	private String username;

	
	public SessionInfo(String key, String username) {
		this.key = key;
		this.username = username;
	}
	
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
