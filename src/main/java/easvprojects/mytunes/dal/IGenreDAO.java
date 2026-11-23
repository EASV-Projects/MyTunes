package easvprojects.mytunes.dal;

import java.util.List;
import easvprojects.mytunes.be.Genre;
public interface IGenreDAO {
    List<Genre> searchGenres(String query) throws Exception;

    void insertGenre(Genre genre) throws Exception;
    void updateGenre(Genre genre) throws Exception;
    void deleteGenre(int id) throws Exception;
    List<Genre> getGenres() throws Exception;
}
