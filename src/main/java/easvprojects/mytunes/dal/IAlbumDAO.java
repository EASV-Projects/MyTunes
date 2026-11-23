package easvprojects.mytunes.dal;

import easvprojects.mytunes.be.Album;

import java.util.List;

public interface IAlbumDAO {
    //Get all albums
    List<Album> getAlbums() throws Exception;
    //Get albums by name
    List<Album> getAlbumsByName() throws Exception;
    //Get albums by ID

    Album getAlbumById(int id) throws Exception;


    //Get albums by genre
    List<Album> getAlbumsByGenre() throws Exception;


    //Add a new album
    void insertAlbum (Album album) throws Exception;

    // Update an existing album
    void updateAlbum(Album album) throws Exception;

    // Delete an album
    void deleteAlbum(int id) throws Exception;

}