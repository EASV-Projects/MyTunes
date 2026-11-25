package easvprojects.mytunes.dal.db;

import easvprojects.mytunes.be.Album;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

/**
 * Base class for the DAO tests.
 * Every test gets its own empty in-memory database (see InMemoryDBConnector),
 * so tests never touch data/mytunes.db, never write to disk and never affect each other.
 */
abstract class DatabaseTest {

    /*
     * IntelliJ runs these tests inside the app's module (easvprojects.mytunes), with JUnit as a
     * separate module. module-info.java can't say "requires org.junit.jupiter.api", because JUnit
     * only exists while testing, so the module is told to read JUnit here instead, before any test
     * calls Assertions. When Maven runs the tests on the classpath this does nothing.
     */
    static {
        try {
            Module junit = Class.forName("org.junit.jupiter.api.Assertions").getModule();
            DatabaseTest.class.getModule().addReads(junit);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JUnit is not available", e);
        }
    }

    InMemoryDBConnector db;

    @BeforeEach
    void createDatabase() throws Exception {
        db = new InMemoryDBConnector();
    }

    @AfterEach
    void deleteDatabase() throws Exception {
        db.close();   // the in-memory database disappears here
    }

    /** Saves an album (without genres) through the AlbumDAO and returns it with its real id. */
    Album insertAlbum(String name) throws Exception {
        AlbumDAO albumDAO = new AlbumDAO(db);
        albumDAO.insertAlbum(new Album(0, name, new ArrayList<>(), new ArrayList<>()));
        return albumDAO.getAlbumsByName(name).stream()
                .filter(a -> a.getName().equals(name))
                .findFirst()
                .orElseThrow();
    }
}
