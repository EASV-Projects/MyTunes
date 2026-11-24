package easvprojects.mytunes.bll;

import easvprojects.mytunes.be.Genre;
import easvprojects.mytunes.be.Playlist;
import easvprojects.mytunes.be.Song;
import easvprojects.mytunes.be.SongFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistManagerTest extends ManagerTest {

    private PlaylistManager playlistManager;

    private Genre rock;
    private Genre jazz;
    private Song a;
    private Song b;
    private Song c;
    private Playlist mix;

    @BeforeEach
    void setUp() throws Exception {
        playlistManager = new PlaylistManager(db);


        SongManager songManager = new SongManager(db);
        songManager.createSong("A", null, null, List.of(rock));
        songManager.createSong("B", null, null, null);
        songManager.createSong("C", null, null, null);
        a = songManager.searchSongs("A", SongFilter.TITLE).get(0);
        b = songManager.searchSongs("B", SongFilter.TITLE).get(0);
        c = songManager.searchSongs("C", SongFilter.TITLE).get(0);

        playlistManager.createPlaylist("Mix", "My mix");
        mix = playlistManager.getAllPlaylists().get(0);
    }

    /** Returns the titles on the mix playlist, in order. */
    private List<String> titlesOnMix() throws Exception {
        return playlistManager.getSongsOnPlaylist(mix).stream().map(Song::getTitle).toList();
    }

    /** Puts A, B, C on the mix playlist, in that order. */
    private void addABC() throws Exception {
        playlistManager.addSongToPlaylist(mix, a);
        playlistManager.addSongToPlaylist(mix, b);
        playlistManager.addSongToPlaylist(mix, c);
    }

    private List<String> names(List<Playlist> playlists) {
        return playlists.stream().map(Playlist::getName).toList();
    }

    // CREATE

    @Test
    void createPlaylistSavesTrimmedNameAndDescription() throws Exception {
        playlistManager.createPlaylist("  Road trip ", "  Long drives ");

        Playlist trip = playlistManager.searchPlaylists("Road trip").get(0);
        assertEquals("Road trip", trip.getName());
        assertEquals("Long drives", trip.getDescription());
        assertTrue(trip.getSongs().isEmpty());
    }

    @Test
    void createPlaylistTurnsANullDescriptionIntoAnEmptyOne() throws Exception {
        playlistManager.createPlaylist("Road trip", null);

        assertEquals("", playlistManager.searchPlaylists("Road trip").get(0).getDescription());
    }

    @Test
    void createPlaylistRejectsBlankNamesAndSavesNothing() throws Exception {
        for (String blank : BLANK_TEXTS) {
            assertThrows(IllegalArgumentException.class, () -> playlistManager.createPlaylist(blank, "desc"));
        }
        assertEquals(List.of("Mix"), names(playlistManager.getAllPlaylists()));
    }

    @Test
    void addSongToPlaylistAppendsAndAllowsDuplicates() throws Exception {
        addABC();
        playlistManager.addSongToPlaylist(mix, a);

        assertEquals(List.of("A", "B", "C", "A"), titlesOnMix());
    }

    @Test
    void addSongToPlaylistRejectsNull() {
        assertThrows(IllegalArgumentException.class, () -> playlistManager.addSongToPlaylist(null, a));
        assertThrows(IllegalArgumentException.class, () -> playlistManager.addSongToPlaylist(mix, null));
    }

    // READ

    @Test
    void searchPlaylistsFindsNamesContainingTheQuery() throws Exception {
        playlistManager.createPlaylist("Road trip", null);

        assertEquals(List.of("Road trip"), names(playlistManager.searchPlaylists(" trip ")));
    }

    @Test
    void searchPlaylistsWithABlankQueryReturnsEveryPlaylist() throws Exception {
        playlistManager.createPlaylist("Road trip", null);

        for (String blank : BLANK_TEXTS) {
            assertEquals(2, playlistManager.searchPlaylists(blank).size());
        }
    }

    @Test
    void getPlaylistsByGenreAndBySong() throws Exception {
        playlistManager.createPlaylist("Road trip", null);
        Playlist trip = playlistManager.searchPlaylists("Road trip").get(0);
        playlistManager.addSongToPlaylist(mix, a);   // A is Rock
        playlistManager.addSongToPlaylist(trip, b);

        assertEquals(List.of("Mix"), names(playlistManager.getPlaylistsByGenre(rock)));
        assertTrue(playlistManager.getPlaylistsByGenre(jazz).isEmpty());
        assertEquals(List.of("Road trip"), names(playlistManager.getPlaylistsBySong(b)));
        assertTrue(playlistManager.getPlaylistsBySong(c).isEmpty());
    }

    @Test
    void readMethodsRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> playlistManager.getPlaylistsByGenre(null));
        assertThrows(IllegalArgumentException.class, () -> playlistManager.getPlaylistsBySong(null));
        assertThrows(IllegalArgumentException.class, () -> playlistManager.getSongsOnPlaylist(null));
    }

    // UPDATE

    @Test
    void updatePlaylistChangesNameAndDescriptionButKeepsTheSongs() throws Exception {
        addABC();

        playlistManager.updatePlaylist(new Playlist(mix.getId(), "Best of", "Favourites", List.of()));

        Playlist updated = playlistManager.getAllPlaylists().get(0);
        assertEquals("Best of", updated.getName());
        assertEquals("Favourites", updated.getDescription());
        assertEquals(List.of("A", "B", "C"), titlesOnMix());
    }

    @Test
    void updatePlaylistRejectsNullAndBlankNames() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> playlistManager.updatePlaylist(null));
        for (String blank : BLANK_TEXTS) {
            assertThrows(IllegalArgumentException.class,
                    () -> playlistManager.updatePlaylist(new Playlist(mix.getId(), blank, "desc", List.of())));
        }
        assertEquals(List.of("Mix"), names(playlistManager.getAllPlaylists()));
    }

    @Test
    void moveSongMovesAnEntryInEitherDirection() throws Exception {
        addABC();

        playlistManager.moveSong(mix, 0, 2);
        assertEquals(List.of("B", "C", "A"), titlesOnMix());

        playlistManager.moveSong(mix, 2, 0);
        assertEquals(List.of("A", "B", "C"), titlesOnMix());
    }

    @Test
    void moveSongToTheSamePositionDoesNothing() throws Exception {
        addABC();

        playlistManager.moveSong(mix, 1, 1);

        assertEquals(List.of("A", "B", "C"), titlesOnMix());
    }

    @Test
    void moveSongRejectsPositionsOffThePlaylist() throws Exception {
        addABC();

        assertThrows(IllegalArgumentException.class, () -> playlistManager.moveSong(mix, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> playlistManager.moveSong(mix, 0, 3));
        assertThrows(IllegalArgumentException.class, () -> playlistManager.moveSong(mix, 3, 0));
        assertThrows(IllegalArgumentException.class, () -> playlistManager.moveSong(null, 0, 1));
        assertEquals(List.of("A", "B", "C"), titlesOnMix());
    }

    @Test
    void moveSongUpAndDown() throws Exception {
        addABC();

        playlistManager.moveSongUp(mix, 2);
        assertEquals(List.of("A", "C", "B"), titlesOnMix());

        playlistManager.moveSongDown(mix, 0);
        assertEquals(List.of("C", "A", "B"), titlesOnMix());
    }

    @Test
    void moveSongUpOnTheFirstAndDownOnTheLastDoNothing() throws Exception {
        addABC();

        playlistManager.moveSongUp(mix, 0);
        playlistManager.moveSongDown(mix, 2);

        assertEquals(List.of("A", "B", "C"), titlesOnMix());
    }

    @Test
    void positionsStayInSyncAfterMovingTheLastSongDown() throws Exception {
        addABC();

        playlistManager.moveSongDown(mix, 2);
        playlistManager.removeSongFromPlaylist(mix, 2);   // must still find C at position 2

        assertEquals(List.of("A", "B"), titlesOnMix());
    }

    // DELETE

    @Test
    void removeSongFromPlaylistRemovesOnlyThatEntry() throws Exception {
        addABC();
        playlistManager.addSongToPlaylist(mix, a);

        playlistManager.removeSongFromPlaylist(mix, 0);

        assertEquals(List.of("B", "C", "A"), titlesOnMix());
    }

    @Test
    void removeSongFromPlaylistRejectsPositionsOffThePlaylist() throws Exception {
        addABC();

        assertThrows(IllegalArgumentException.class, () -> playlistManager.removeSongFromPlaylist(mix, -1));
        assertThrows(IllegalArgumentException.class, () -> playlistManager.removeSongFromPlaylist(mix, 3));
        assertThrows(IllegalArgumentException.class, () -> playlistManager.removeSongFromPlaylist(null, 0));
        assertEquals(List.of("A", "B", "C"), titlesOnMix());
    }
}
