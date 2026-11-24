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
     * Check that {@code text} does exceed the provided length.
     * @param text the text to check
     * @param field what the text is, used in the error message (e.g. "Song title")
     * @param maxLength the length to check
     */
    static String checkLength(String text, String field, int maxLength) {
        if (text.length() > maxLength) {
            throw new IllegalArgumentException(field + " must not exceed "+maxLength+" characters.");
        }
        return text;
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

    /** Checks that {@code position} points at an entry on a playlist with {@code size} entries. */
     static void requireValidPosition(int position, int size) {
        if (position < 0 || position >= size) {
            throw new IllegalArgumentException(
                    "Position " + position + " is not on the playlist (it has " + size + " songs).");
        }
    }


}
