package easvprojects.mytunes.bll;

import easvprojects.mytunes.be.Album;
import easvprojects.mytunes.be.Genre;
import easvprojects.mytunes.dal.IAlbumDAO;
import easvprojects.mytunes.dal.IDBConnector;
import easvprojects.mytunes.dal.db.AlbumDAO;

import java.util.ArrayList;
import java.util.List;

public class AlbumManager {
    private IAlbumDAO albumDAO;

    // Constructor using database connector
    public AlbumManager(IDBConnector db) {

        this.albumDAO = new AlbumDAO(db);
    }

    // Constructor using DAO
    public AlbumManager(IAlbumDAO albumDAO) {
        this.albumDAO = albumDAO;
    }

    public void createAlbum(String name, ArrayList<Genre> genres ) throws Exception{
        String validName = Validation.requireText(name, "Album name");
        ArrayList <Genre> validGenre = Validation.requireNonNull(genres, "Genres");
        Album album = new Album(0, validName, validGenre, new ArrayList<>());
        albumDAO.insertAlbum(album);
    }
// return all albums
    public List<Album> getAllAlbums() throws Exception{
        return albumDAO.getAlbums();
    }
// updating albom's name
    public void updateAlbum(Album album) throws Exception{
        Validation.requireNonNull(album, "Album");
        Validation.requireText(album.getName(), "Album name");
        albumDAO.updateAlbum(album);
    }
// get album by name
    public List<Album> searchAlbum(String name) throws Exception{
        Validation.requireText(name, "Album name");
        return albumDAO.getAlbumsByName(name);
    }
//get album by id
    public Album getAlbumById(int id) throws Exception{
        return albumDAO.getAlbumById(id);
    }

// get album by genre
    public List<Album> getAlbumsByGenre(Genre genre) throws Exception{
        Validation.requireNonNull(genre, "Genre");
        return albumDAO.getAlbumsByGenre(genre);
    }
 //deleting album by id
    public void deleteAlbum(Album album) throws Exception{
        albumDAO.deleteAlbum(album.getId());
    }
}
