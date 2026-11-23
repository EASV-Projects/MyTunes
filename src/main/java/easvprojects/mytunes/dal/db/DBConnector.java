package easvprojects.mytunes.dal.db;

import easvprojects.mytunes.dal.IDBConnector;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class DBConnector implements IDBConnector {
    private static final String DEFAULT_URL = "jdbc:sqlite:data/mytunes.db";

    private final String url;

    /** Connects to the app's database file, data/mytunes.db. */
    public DBConnector() throws Exception {
        new File("data").mkdirs();
        this.url = DEFAULT_URL;
        createTables();
    }

    /**
     * Connects to the database at the given JDBC URL.
     * Used by the tests to work on a throwaway database instead of the real one.
     *
     * @param url a JDBC URL, for example {@code jdbc:sqlite:C:/temp/test.db}
     */
    public DBConnector(String url) throws Exception {
        this.url = url;
        createTables();
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection c = DriverManager.getConnection(url);
        try (Statement s = c.createStatement()) {
            s.execute("PRAGMA foreign_keys = ON");   // SQLite has FKs off by default
        }
        return c;
    }

    public void createTables() throws Exception {
        try (Connection c = getConnection()) {
            createTables(c);
        }
    }

    /**
     * Runs schema.sql on the given connection.
     * Package-private and static so the test's InMemoryDBConnector can build the same tables.
     */
    static void createTables(Connection c) throws Exception {
        String sql;

        try (InputStream in = DBConnector.class.getResourceAsStream("schema.sql")) {
            sql = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }

        try (Statement s = c.createStatement()) {
            for (String stmt : sql.split(";")) {
                if (!stmt.isBlank()) s.execute(stmt);
            }
        }
    }
}
