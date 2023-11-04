# KNCScrobbler
This program allows users to track the songs they listen to WKNC 88.1 onto their last.fm profile. The website may take few minutes to load if it has been inactive for over 15 minutes as per render.com's free hosting policy

https://kncscrobbler.app/

Mirror:
https://knc-scrobbler.onrender.com/

This is a springboot project written in Java, with JavaScript and HTML for the frontend.

# Linking Account 
To link your last.fm account, click the button that says "Link last.fm Account". You will be redirected to a page to allow 
access to your last.fm account. Click "Yes, allow access" and you will be redirected back to the website. Your session will
be stored in cookies as to not require authorization on each refresh. If your account is successfully connected, your username
will be displayed upon redirection. 

Songs will stop being scrobbled once the tab is closed. 

**User information is not stored on the server!** 
All user authentication information is handled client-side.

# Unlinking Account
To unlink your account, click the "Unlink account" button. KNC Scrobbler will no longer scrobble the songs from the webstream to your last.fm 
account.
