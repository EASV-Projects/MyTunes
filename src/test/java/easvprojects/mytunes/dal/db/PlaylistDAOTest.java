package easvprojects.mytunes.dal.db;

import easvprojects.mytunes.be.Genre;
import easvprojects.mytunes.be.Playlist;
import easvprojects.mytunes.be.Song;
import easvprojects.mytunes.be.SongFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistDAOTest extends DatabaseTest {

    private PlaylistDAO playlistDAO;

    private Genre rock;
    private Genre jazz;
    private Song a;
    private Song b;
    private Song c;
    private Playlist mix;

    @BeforeEach
    void setUp() throws Exception {
        playlistDAO = new PlaylistDAO(db);

        GenreDAO genreDAO = new GenreDAO(db);
        genreDAO.insertGenre(new Genre(0, "Rock"));
        genreDAO.insertGenre(new Genre(0, "Jazz"));
        rock = genreDAO.searchGenres("Rock").get(0);
        jazz = genreDAO.searchGenres("Jazz").get(0);

        SongDAO songDAO = new SongDAO(db);
        songDAO.insertSong(new Song(0, "A", null, null, List.of(rock)));
        songDAO.insertSong(new Song(0, "B", null, null, List.of()));
        songDAO.insertSong(new Song(0, "C", null, null, List.of()));
        a = songDAO.searchSongs("A", SongFilter.TITLE).get(0);
        b = songDAO.searchSongs("B", SongFilter.TITLE).get(0);
        c = songDAO.searchSongs("C", SongFilter.TITLE).get(0);

        playlistDAO.insertPlaylist(new Playlist(0, "Mix", "My mix", List.of()));
        mix = playlistDAO.getPlaylists().get(0);
    }

    /** Returns the titles on the mix playlist, in order. */
    private List<String> titlesOnMix() throws Exception {
        return playlistDAO.getSongsOnPlaylist(mix).stream().map(Song::getName).collect(Collectors.toList());
    }

    /** Puts A, B, C on the mix playlist, in that order. */
    private void addABC() throws Exception {
        playlistDAO.addSongToPlaylist(mix, a);
        playlistDAO.addSongToPlaylist(mix, b);
        playlistDAO.addSongToPlaylist(mix, c);
    }

    @Test
    void insertedPlaylistIsEmpty() throws Exception {
        assertEquals("Mix", mix.getName());
        assertEquals("My mix", mix.getDescription());
        assertTrue(mix.getSongs().isEmpty());
    }

    @Test
    void songsAreAddedAtTheEnd() throws Exception {
        addABC();

        assertEquals(List.of("A", "B", "C"), titlesOnMix());
    }

    @Test
    void sameSongCanBeAddedTwice() throws Exception {
        playlistDAO.addSongToPlaylist(mix, a);
        playlistDAO.addSongToPlaylist(mix, a);

        assertEquals(List.of("A", "A"), titlesOnMix());
    }

    @Test
    void getPlaylistsLoadsTheSongs() throws Exception {
        addABC();

        Playlist loaded = playlistDAO.getPlaylists().get(0);

        assertEquals(3, loaded.getSongs().size());
    }

    @Test
    void getPlaylistByNameMatchesPartOfTheName() throws Exception {
        playlistDAO.insertPlaylist(new Playlist(0, "Workout", null, List.of()));

        assertEquals(1, playlistDAO.getPlaylistByName("work").size());
        assertEquals(2, playlistDAO.getPlaylists().size());
    }

    @Test
    void getPlaylistsByGenreFindsPlaylistsWithASongOfThatGenre() throws Exception {
        playlistDAO.addSongToPlaylist(mix, a);

        assertEquals(1, playlistDAO.getPlaylistsByGenre(rock).size());
        assertTrue(playlistDAO.getPlaylistsByGenre(jazz).isEmpty());
    }

    @Test
    void getPlaylistsBySongFindsPlaylistsContainingIt() throws Exception {
        playlistDAO.addSongToPlaylist(mix, a);

        assertEquals(1, playlistDAO.getPlaylistsBySong(a).size());
        assertTrue(playlistDAO.getPlaylistsBySong(b).isEmpty());
    }

    @Test
    void updatePlaylistChangesNameAndDescription() throws Exception {
        playlistDAO.updatePlaylist(new Playlist(mix.getId(), "Party", "Loud", List.of()));

        Playlist updated = playlistDAO.getPlaylists().get(0);
        assertEquals("Party", updated.getName());
        assertEquals("Loud", updated.getDescription());
    }

    @Test
    void removeSongClosesTheGap() throws Exception {
        addABC();

        playlistDAO.removeSongFromPlaylist(mix, 1);

        assertEquals(List.of("A", "C"), titlesOnMix());
        // The positions are 0 and 1 again, so a new song goes to the end
        playlistDAO.addSongToPlaylist(mix, b);
        assertEquals(List.of("A", "C", "B"), titlesOnMix());
    }

    @Test
    void removeSongOnlyRemovesOneCopy() throws Exception {
        playlistDAO.addSongToPlaylist(mix, a);
        playlistDAO.addSongToPlaylist(mix, b);
        playlistDAO.addSongToPlaylist(mix, a);

        playlistDAO.removeSongFromPlaylist(mix, 0);

        assertEquals(List.of("B", "A"), titlesOnMix());
    }

    @Test
    void removeSongAtUnknownPositionThrows() throws Exception {
        addABC();

        assertThrows(IllegalArgumentException.class, () -> playlistDAO.removeSongFromPlaylist(mix, 5));
        assertEquals(List.of("A", "B", "C"), titlesOnMix());
    }

    @Test
    void moveSongTowardsTheEnd() throws Exception {
        addABC();

        playlistDAO.moveSong(mix, 0, 2);

        assertEquals(List.of("B", "C", "A"), titlesOnMix());
    }

    @Test
    void moveSongTowardsTheStart() throws Exception {
        addABC();

        playlistDAO.moveSong(mix, 2, 0);

        assertEquals(List.of("C", "A", "B"), titlesOnMix());
    }

    @Test
    void moveSongToSamePositionChangesNothing() throws Exception {
        addABC();

        playlistDAO.moveSong(mix, 1, 1);

        assertEquals(List.of("A", "B", "C"), titlesOnMix());
    }

    @Test
    void moveSongFromUnknownPositionThrows() throws Exception {
        addABC();

        assertThrows(IllegalArgumentException.class, () -> playlistDAO.moveSong(mix, 7, 0));
        assertEquals(List.of("A", "B", "C"), titlesOnMix());
    }
}
