package easvprojects.mytunes.dal;

import java.util.List;
import easvprojects.mytunes.be.Genre;
public interface IGenreDAO {
    void searchGenres(String query);

    void insertGenre(Genre genre) throws Exception;
    void updateGenre(Genre genre) throws Exception;
    void deleteGenre(int id) throws Exception;
}
