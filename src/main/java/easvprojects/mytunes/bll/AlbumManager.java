package easvprojects.mytunes.bll;

import easvprojects.mytunes.be.Album;
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

    public void createAlbum(Album album) throws Exception{
        Validation.requireNonNull(album, "Album");
        Validation.requireText(album.getName(), "Album name");
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
    public List<Album> getAlbumsByName(String name) throws Exception{
        Validation.requireText(name, "Album name");
        return albumDAO.getAlbumsByName(name);
    }
 //deleting album by id
    public void deleteAlbum(int id) throws Exception{
        if(id < 0 || id > getAllAlbums().size()){
            return;
        }
        albumDAO.deleteAlbum(id);
    }

}
