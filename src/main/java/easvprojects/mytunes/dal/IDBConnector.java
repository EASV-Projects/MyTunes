package easvprojects.mytunes.dal;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Hands out connections to the database.
 * The DAOs only depend on this interface, so they don't care which database
 * (or which implementation, e.g. a test one) is behind it.
 */
public interface IDBConnector {

    /** Opens a new connection. The caller must close it (use try-with-resources). */
    Connection getConnection() throws SQLException;
}
