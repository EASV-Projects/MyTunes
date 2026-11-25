package easvprojects.mytunes.bll;

import easvprojects.mytunes.be.Genre;
import easvprojects.mytunes.dal.IDBConnector;
import easvprojects.mytunes.dal.IGenreDAO;
import easvprojects.mytunes.dal.db.GenreDAO;

import java.util.List;

public class GenreManager {

    private final IGenreDAO genreDAO;

    // Constructor using database connector
    public GenreManager(IDBConnector db) {
        this.genreDAO = new GenreDAO(db);
    }

    // Constructor using DAO
    public GenreManager(IGenreDAO genreDAO) {
        this.genreDAO = genreDAO;
    }

    // Get all genres
    public List<Genre> getAllGenres() throws Exception {
        return genreDAO.getGenres();
    }

    // Genre search
    public List<Genre> searchGenres(String query) throws Exception {
        if (Validation.isBlank(query)){
            return genreDAO.getGenres();
        }

        return genreDAO.searchGenres(query);
    }

    // Insert a new genre
    public void createGenre(String name) throws Exception {

        Validation.requireText(name, "Name");

        Genre genre = new Genre(0, name);
        genreDAO.insertGenre(genre);
    }

    // Update an existing genre
    public void updateGenre(Genre genre) throws Exception {

        Validation.requireNonNull(genre, "Genre");
        Validation.requireText(genre.getName(), "Name");

        genreDAO.updateGenre(genre);
    }

    // Delete a genre
    public void deleteGenre(Genre genre) throws Exception {

        Validation.requireNonNull(genre, "Genre");

        genreDAO.deleteGenre(genre.getId());
    }
}
