# KNCScrobbler
This program allows users to track the songs they listen to WKNC 88.1 onto their last.fm profile. 

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
User authentication is handled by last.fm and session keys are encrypted and stored in secure HTTPOnly cookies.

# Unlinking Account
To unlink your account, click the "Unlink account" button. KNC Scrobbler will no longer scrobble the songs from the webstream to your last.fm 
account.

# Troubleshooting
See the troubleshooting wiki page: https://github.com/aklevans/KNCScrobbler/wiki/Troubleshooting

