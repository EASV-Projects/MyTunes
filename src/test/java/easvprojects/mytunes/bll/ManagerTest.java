package easvprojects.mytunes.bll;

import easvprojects.mytunes.dal.db.InMemoryDBConnector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for the manager tests.
 * Every test gets its own empty in-memory database (see InMemoryDBConnector),
 * and the managers run on top of the real DAOs.
 */
abstract class ManagerTest {

    /*
     * Lets the app's module read JUnit when IntelliJ runs these tests on the module path.
     * See DatabaseTest for the full explanation. When Maven runs the tests on the classpath this does nothing.
     */
    static {
        try {
            Module junit = Class.forName("org.junit.jupiter.api.Assertions").getModule();
            ManagerTest.class.getModule().addReads(junit);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JUnit is not available", e);
        }
    }

    /** Inputs that count as blank (used for the validation tests). */
    static final String[] BLANK_TEXTS = {null, "", "   "};

    InMemoryDBConnector db;

    @BeforeEach
    void createDatabase() throws Exception {
        db = new InMemoryDBConnector();
    }

    @AfterEach
    void deleteDatabase() throws Exception {
        db.close();   // the in-memory database disappears here
    }
}
