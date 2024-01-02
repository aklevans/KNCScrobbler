package app.kncscrobbler.models;

import de.umass.lastfm.Track;

public class ScrobbleResponse {

	private boolean isValid;
	private Track previous;
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public Track getPrevious() {
		return previous;
	}
	public void setPrevious(Track previous) {
		this.previous = previous;
	}
	
	public ScrobbleResponse(boolean isValid, Track previous) {
		super();
		this.isValid = isValid;
		this.previous = previous;
	}
	
	
}
