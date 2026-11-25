package easvprojects.mytunes.dal.db;

import easvprojects.mytunes.dal.IDBConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

/**
 * A database that lives only in memory, for unit tests.
 * Nothing is written to disk, and each instance is a brand-new, empty database
 * with the same tables as the real one (from schema.sql).
 *
 * Why the unusual URL?
 * A plain "jdbc:sqlite::memory:" gives EVERY connection its own private database,
 * but the DAOs open and close a new connection on each call, so the data would vanish.
 * Instead each instance gets a uniquely named in-memory database that all its
 * connections share ("cache=shared"). SQLite deletes that database as soon as its last
 * connection closes, so this class keeps one connection open until close() is called.
 */
public class InMemoryDBConnector implements IDBConnector, AutoCloseable {

    private final String url;
    private final Connection keepAlive;

    public InMemoryDBConnector() throws Exception {
        String name = "test-" + UUID.randomUUID();          // unique, so tests never share data
        url = "jdbc:sqlite:file:" + name + "?mode=memory&cache=shared";

        keepAlive = getConnection();
        DBConnector.createTables(keepAlive);
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection c = DriverManager.getConnection(url);
        try (Statement s = c.createStatement()) {
            s.execute("PRAGMA foreign_keys = ON");   // SQLite has FKs off by default
        }
        return c;
    }

    /** Closes the last connection, which makes SQLite throw the database away. */
    @Override
    public void close() throws SQLException {
        keepAlive.close();
    }
}
