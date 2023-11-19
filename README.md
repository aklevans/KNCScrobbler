# KNCScrobbler
This program allows users to track the songs they listen to WKNC 88.1 onto their last.fm profile. The website may take few minutes to load if it has been inactive for over 15 minutes as per render.com's free hosting policy

https://kncscrobbler.app/

Mirror:
https://knc-scrobbler.onrender.com/

This is a springboot project written in Java, JavaScript, and HTML with AngularJS.

# Linking Account 
To link your last.fm account, click the button that says "Link last.fm Account". You will be redirected to a page to allow 
access to your last.fm account. Click "Yes, allow access" and you will be redirected back to the website. Your session will
be stored in cookies as to not require authorization on each refresh. If your account is successfully connected, your username
will be displayed upon redirection. 

Songs will stop being scrobbled once the tab is closed. 

**Usernames/passwords are not stored on the server!** 
All user authentication information is handled client-side.

# Unlinking Account
To unlink your account, click the "Unlink account" button. KNC Scrobbler will no longer scrobble the songs from the webstream to your last.fm 
account.

# Troubleshooting
It may take a few seconds for the song to appear on your last.fm profile. This is typically due to traffic on last.fm's servers. 

Song titles and artists may be a few seconds out of sync with the livestream. This is due to the stream being inconsistently delayed from the actual radio broadcast. Different browsers may have different delay times.

If you encounter duplicate scrobbles on your last.fm account, this may be due to a number of reasons. This is typically caused by last.fm not updating the currently playing song fast enough, which the KNC Scrobbler relies on to scrobble correctly. Refresh your last.fm page (not the knc scrobbler page) after a few seconds and you may see this go away.
