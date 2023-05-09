package com.gmantovi.harmony;

import com.gmantovi.harmony.gsonClasses.track.Track;

import java.sql.*;
import java.util.List;

public class HomePageController {
    public static void main(String[] args) throws SQLException {
        /*
        Chiamata per il TESTO di una TRACK DATO ID
        MusixMatch m = new MusixMatch("391689594f1ad1d992b2efd5fc5862ef");
        Lyrics l = m.getLyrics(88236109);
        System.out.println(l.getLyricsBody());

        chiamata PER INFO TRACK DATO ID
        MusixMatch m = new MusixMatch("391689594f1ad1d992b2efd5fc5862ef");
        Track l = m.getTrack(88236109);
        System.out.println("NOME CANZONE = " + l.getTrack().getTrackName() + " NOME ALBUM = " + l.getTrack().getAlbumName());

         chiamata PER MATCHARE CANZONE CON Titolo, artista, album
         MusixMatch m = new MusixMatch("391689594f1ad1d992b2efd5fc5862ef");
         Track l = m.getMatchingTrack("Mistake","NF");
         System.out.println("NOME CANZONE = " + l.getTrack().getTrackName() + "\nNOME ALBUM = " + l.getTrack().getAlbumName() + "\nNome Cantante = " + l.getTrack().getArtistName());

         chiamata PER SNIPPET TRACK DATO ID
         MusixMatch m = new MusixMatch("391689594f1ad1d992b2efd5fc5862ef");
         Snippet l = m.getSnippet(m.getMatchingTrack("Mistake","NF").getTrack().getTrackId());
         System.out.println("SNIPPET CANZONE = " + l.getSnippetBody());

         chiamata per ALBUM DATO ID
         MusixMatch m = new MusixMatch("391689594f1ad1d992b2efd5fc5862ef");
         Album l = m.getAlbum(14250417);
         System.out.println("NOME ALBUM = " + l.getAlbum().getAlbumName() + " NOME ARTISTA = " + l.getAlbum().getArtistName());

         chiamata per LISTA ARTISTI DATO NOME
        MusixMatch m = new MusixMatch("391689594f1ad1d992b2efd5fc5862ef");
        List<Artist> l = m.searchArtists("Shawn Mendes",1);
        System.out.println("NOME Artista = " + l.get(0).getArtist().getArtistName() + "\nID ARTISTA = " + l.get(0).getArtist().getArtistId());

        chiamata per TOP CANZONI DI NAZIONE
        MusixMatch m = new MusixMatch("391689594f1ad1d992b2efd5fc5862ef");
        List<Track> l = m.getChartTracks("it",2,"top");
        System.out.println("NOME CANZONE 1 = " + l.get(0).getTrack().getTrackName() + "\nNOME CANZONE 2 = " + l.get(1).getTrack().getTrackName());

        chiamata per avere lista album di un artista
        MusixMatch m = new MusixMatch("391689594f1ad1d992b2efd5fc5862ef");
        List<Album> l = m.getArtistAlbums(27846837,5);
        System.out.println("NOME ALBUM = " + l.get(0).getAlbum().getAlbumName());

        chiamata per avere lista canzoni di album
        MusixMatch m = new MusixMatch("391689594f1ad1d992b2efd5fc5862ef");
        List<Track> l = m.getAlbumTracks(54605501,5);
        System.out.println("LISTA CANZONI = " + l.get(0).getTrack().getTrackName() );
         */
        try {
            MusixMatch m = new MusixMatch("391689594f1ad1d992b2efd5fc5862ef");
            List<Track> l = m.getTracksChart("it",5,"top");
            System.out.println("NOME CANZONE 1 = " + l.get(0).getTrack().getTrackName() + "\nNOME CANZONE 2 = " + l.get(1).getTrack().getTrackName());
        }catch (NullPointerException e){
            System.out.println("NULLO");
        }catch (Exception e){
            e.printStackTrace();
        }

        /*Connection connection = null;
        Statement statement = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/harmony?user=root&password=giulio");
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM client");
            while(rs.next()) {
                int id = rs.getInt("idclient");
                String name = rs.getString("name");
                String surname= rs.getString("surname");
                System.out.println("CLIENTE: " + id + " " + name + " " + surname);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                statement.close();
                connection.close();
            }
        }*/




}
}
