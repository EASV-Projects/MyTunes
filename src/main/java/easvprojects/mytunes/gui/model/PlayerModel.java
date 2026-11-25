package easvprojects.mytunes.gui.model;

import easvprojects.mytunes.be.Song;
import javafx.beans.property.*;

public class PlayerModel {
    // Stores the currently selected/playing song (start null).
    // 'Property' allows the UI to automatically update whenever this value changes.
    private final ObjectProperty<Song> currentSong = new SimpleObjectProperty<>(null);

    // Track whether a song is currently playing (true) or paused/stopped (false)
    private final BooleanProperty playing = new SimpleBooleanProperty(false);

    // Store the volume level, default to 50%
    private final DoubleProperty volume = new SimpleDoubleProperty(0.5);

    // Returns the property wrapper itself so UI elements (like labels) can listen to it
    public ObjectProperty<Song> currentSongProperty() {
        return currentSong;
    }

    // Getter: return the actual Song object inside the property
    public Song getCurrentSong() {
        return currentSong.get();
    }

    // Setter: update the current song and notify any listening UI elements
    public void setCurrentSong(Song song) {
        currentSong.set(song);
    }
}
