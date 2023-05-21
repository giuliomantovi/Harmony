# Harmony
<img src="src/main/resources/com/gmantovi/harmony/icons/harmony_icon.png" width="100" height="100">

An intuitive application to get all the information you need about your favourite music

## Features
- Display the current most popular tracks and artists
- Search for specific artists and tracks to see detailed information (lyrics, genres, discography, related artists...)
- Create your personal playlist
- Get song suggestions based on your playlist to explore new music
- And much more ...

>Note: Except for the song suggestions, the music data is retrieved using https://www.musixmatch.com/ API

## Screenshots

###### Home page
![Alt text](src/main/resources/com/gmantovi/harmony/screenshots/homePage.png?raw=true "HomePage")

###### Search page
Track features:
![Alt text](src/main/resources/com/gmantovi/harmony/screenshots/searchTrackPage.png?raw=true "SearchPage")

Artist feature:
![Alt text](src/main/resources/com/gmantovi/harmony/screenshots/searchArtistPage.png?raw=true "SearchPage")

###### Playlist page
![Alt text](src/main/resources/com/gmantovi/harmony/screenshots/playlistPage.png?raw=true "PlaylistPage")



## Requirements
It only works on **Java 18+** .    
The proper JDK or JRE should be installed in your system to run this application.

## Download and Usage
This software can be downloaded here on github.

I inserted my personal MusixMatch api key to send requests, if you reach the API calls limit (2k) you can create a Musixmatch account and 
change the PERSONAL_API_KEY value in the [Constants](src/main/java/com/gmantovi/harmony/config/Constants.java) file.
To try the playlist feature (third section of the menu) you need to do the followings:
- establish a mysql connection with a local database, here is the mySQL WORKBENCH code to create database and table:
  
  CREATE SCHEMA `harmony` ;

  CREATE TABLE `harmony`.`playlist` (\
  `IDsong` INT NOT NULL,\
  `song` VARCHAR(45) NULL,\
  `singer` VARCHAR(45) NULL,\
  PRIMARY KEY (`IDsong`));


- In the file [Constants](src/main/java/com/gmantovi/harmony/config/Constants.java) you have to modify the MYSQL_CONNECTION_URL
  value so that the user and password credentials are correct for your local database.


## Credits
The MusixMatch Wrapper downloadable at https://github.com/sachin-handiekar/jMusixMatch has been imported and used as a reference to build others gsonClasses.
Author: Sachin Handiekar,
Version: 1.0.
## License
This software is licensed under GNU GENERAL PUBLIC LICENSE.
See [License](LICENSE).

