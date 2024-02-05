/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony;

import com.gmantovi.harmony.API.Proxy;
import com.gmantovi.harmony.config.Constants;
import com.gmantovi.harmony.gsonClasses.lyrics.Lyrics;
import com.gmantovi.harmony.gsonClasses.track.Track;
import com.gmantovi.harmony.gsonClasses.track.TrackData;
import org.junit.jupiter.api.Test;

import java.util.List;
/**
 * Test class for checking the integrity of the API calls
 * @author Giulio Mantovi
 * @version 2023.05.21
 */
public class ProxyTest {

    @Test
    //@Ignore
    public void testAPI() throws Exception{

        Proxy musixMatch = new Proxy(Constants.PERSONAL_API_KEY);

        String trackName = "Don't stop the Party";
        String artistName = "The Black Eyed Peas";

        // Track Search [ Fuzzy ]
        Track track = musixMatch.getMatchingTrack(trackName, artistName);
        TrackData data = track.getTrack();
        assert data != null;
        System.out.println("AlbumID : " + data.getAlbumId());
        System.out.println("Album Name : " + data.getAlbumName());
        System.out.println("Artist ID : " + data.getArtistId());
        System.out.println("Album Name : " + data.getArtistName());
        System.out.println("Track ID : " + data.getTrackId());

        int trackID = data.getTrackId();

        Lyrics lyrics = musixMatch.getLyrics(trackID);
        assert lyrics != null;
        System.out.println("Lyrics ID       : " + lyrics.getLyricsId());
        System.out.println("Lyrics Language : " + lyrics.getLyricsLang());
        System.out.println("Lyrics Body     : " + lyrics.getLyricsBody());
        System.out.println("Script-Tracking-URL : " + lyrics.getScriptTrackingURL());
        System.out.println("Pixel-Tracking-URL : " + lyrics.getPixelTrackingURL());
        System.out.println("Lyrics Copyright : " + lyrics.getLyricsCopyright());

        List<Track> chartTracks = musixMatch.getTracksChart("it",10,"top");
        for(Track t : chartTracks){
            System.out.println("TRACK: " + t.getTrack().getTrackName());
        }
        /*
        Chiamata per il TESTO di una TRACK DATO ID
        Proxy m = new Proxy("391689594f1ad1d992b2efd5fc5862ef");
        Lyrics l = m.getLyrics(88236109);
        System.out.println(l.getLyricsBody());

        chiamata PER INFO TRACK DATO ID
        Proxy m = new Proxy("391689594f1ad1d992b2efd5fc5862ef");
        Track l = m.getTrack(88236109);
        System.out.println("NOME CANZONE = " + l.getTrack().getTrackName() + " NOME ALBUM = " + l.getTrack().getAlbumName());

         chiamata PER MATCHARE CANZONE CON Titolo, artista, album
         Proxy m = new Proxy("391689594f1ad1d992b2efd5fc5862ef");
         Track l = m.getMatchingTrack("Mistake","NF");
         System.out.println("NOME CANZONE = " + l.getTrack().getTrackName() + "\nNOME ALBUM = " + l.getTrack().getAlbumName() + "\nNome Cantante = " + l.getTrack().getArtistName());

         chiamata PER SNIPPET TRACK DATO ID
         Proxy m = new Proxy("391689594f1ad1d992b2efd5fc5862ef");
         Snippet l = m.getSnippet(m.getMatchingTrack("Mistake","NF").getTrack().getTrackId());
         System.out.println("SNIPPET CANZONE = " + l.getSnippetBody());

         chiamata per ALBUM DATO ID
         Proxy m = new Proxy("391689594f1ad1d992b2efd5fc5862ef");
         Album l = m.getAlbum(14250417);
         System.out.println("NOME ALBUM = " + l.getAlbum().getAlbumName() + " NOME ARTISTA = " + l.getAlbum().getArtistName());

         chiamata per LISTA ARTISTI DATO NOME
        Proxy m = new Proxy("391689594f1ad1d992b2efd5fc5862ef");
        List<Artist> l = m.searchArtists("Shawn Mendes",1);
        System.out.println("NOME Artista = " + l.get(0).getArtist().getArtistName() + "\nID ARTISTA = " + l.get(0).getArtist().getArtistId());

        chiamata per TOP CANZONI DI NAZIONE
        Proxy m = new Proxy("391689594f1ad1d992b2efd5fc5862ef");
        List<Track> l = m.getChartTracks("it",2,"top");
        System.out.println("NOME CANZONE 1 = " + l.get(0).getTrack().getTrackName() + "\nNOME CANZONE 2 = " + l.get(1).getTrack().getTrackName());

        chiamata per avere lista album di un artista
        Proxy m = new Proxy("391689594f1ad1d992b2efd5fc5862ef");
        List<Album> l = m.getArtistAlbums(27846837,5);
        System.out.println("NOME ALBUM = " + l.get(0).getAlbum().getAlbumName());

        chiamata per avere lista canzoni di album
        Proxy m = new Proxy("391689594f1ad1d992b2efd5fc5862ef");
        List<Track> l = m.getAlbumTracks(54605501,5);
        System.out.println("LISTA CANZONI = " + l.get(0).getTrack().getTrackName() );

        try {
            Proxy m = new Proxy("391689594f1ad1d992b2efd5fc5862ef");
            List<Track> l = m.getTracksChart("it",5,"top");
            System.out.println("NOME CANZONE 1 = " + l.get(0).getTrack().getTrackName() + "\nNOME CANZONE 2 = " + l.get(1).getTrack().getTrackName());
        }catch (NullPointerException e){
            System.out.println("NULLO");
        }catch (Exception e){
            e.printStackTrace();
        }

        //DB CONNECTION TEST
        Connection connection = null;
        Statement statement = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/harmony?user=root&password=giulio");
            statement = connection.createStatement();
            assert statement != null;
            /*ResultSet rs = statement.executeQuery("SELECT * FROM playlist");
            while(rs.next()) {
                int id = rs.getInt("IDsong");
                String song = rs.getString("song");
                String singer= rs.getString("singer");
                System.out.println("SONG: " + id + " " + song + " " + singer);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                assert statement != null;
                statement.close();
                connection.close();
            }
        }*/


    }
}
