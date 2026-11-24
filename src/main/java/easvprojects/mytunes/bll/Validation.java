package easvprojects.mytunes.bll;

/**
 * Small input checks shared by the managers.
 * Every failed check throws an {@link IllegalArgumentException} with a
 * message that can be shown to the user.
 */
final class Validation {

    private Validation() {
    }

    static boolean isBlank(String text) {
        return text == null || text.trim().strip().isBlank();
    }

    /**
     * Checks that {@code text} is not null or blank and returns it trimmed.
     *
     * @param text  the text to check
     * @param field what the text is, used in the error message (e.g. "Song title")
     */
    static String requireText(String text, String field) {
        if (isBlank(text)) {
            throw new IllegalArgumentException(field + " must not be empty.");
        }
        return text.trim();
    }

    /** Checks that {@code value} is not null and returns it. */
    static <T> T requireNonNull(T value, String field) {
        if (value == null) {
            throw new IllegalArgumentException(field + " must not be null.");
        }
        return value;
    }
}
