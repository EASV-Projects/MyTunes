package easvprojects.mytunes.dal;

import java.util.List;
import easvprojects.mytunes.be.Genre;
public interface IGenreDAO {
    List<Genre> getGenres() throws Exception;
    Genre getGenreById() throws Exception;
    List<Genre> getGenreByName() throws Exception;

    void insertGenre(Genre genre) throws Exception;
    void updateGenre(Genre genre) throws Exception;
    void removeGenre(int id) throws Exception;
}
