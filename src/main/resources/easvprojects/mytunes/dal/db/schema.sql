-- MyTunes schema (SQLite)

-- be/Artist: id, name
CREATE TABLE IF NOT EXISTS artist (
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT    NOT NULL
);

-- be/Genre: id, name
CREATE TABLE IF NOT EXISTS genre (
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT    NOT NULL
);

-- be/Album: id, name (songs come from song.album_id, genres from album_genre)
CREATE TABLE IF NOT EXISTS album (
    id   INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT    NOT NULL
);

-- be/Song: id, title, artist, album (genres come from song_genre)
CREATE TABLE IF NOT EXISTS song (
    id        INTEGER PRIMARY KEY AUTOINCREMENT,
    title     TEXT    NOT NULL,
    artist_id INTEGER REFERENCES artist (id) ON DELETE SET NULL,
    album_id  INTEGER REFERENCES album (id)  ON DELETE SET NULL
);

-- be/Playlist: id, name, description (songs come from playlist_song)
CREATE TABLE IF NOT EXISTS playlist (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    name        TEXT    NOT NULL,
    description TEXT
);

-- Song.genres (many-to-many)
CREATE TABLE IF NOT EXISTS song_genre (
    song_id  INTEGER NOT NULL REFERENCES song (id)   ON DELETE CASCADE,
    genre_id INTEGER NOT NULL REFERENCES genre (id) ON DELETE CASCADE,
    PRIMARY KEY (song_id, genre_id)
);

-- Album.genre (many-to-many)
CREATE TABLE IF NOT EXISTS album_genre (
    album_id INTEGER NOT NULL REFERENCES album (id) ON DELETE CASCADE,
    genre_id INTEGER NOT NULL REFERENCES genre (id) ON DELETE CASCADE,
    PRIMARY KEY (album_id, genre_id)
);

-- Playlist.songs, ordered.
-- IPlaylistDAO removes and moves songs by position, so the same songs can appear
-- more than once and order is stored explicitly. position is deliberately not
-- UNIQUE: SQLite checks uniqueness row by row, which would break the
-- "shift positions up/down" updates that moveSong / removeSongFromPlaylist need.
CREATE TABLE IF NOT EXISTS playlist_song (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    playlist_id INTEGER NOT NULL REFERENCES playlist (id) ON DELETE CASCADE,
    song_id     INTEGER NOT NULL REFERENCES song (id)      ON DELETE CASCADE,
    position    INTEGER NOT NULL
);

-- Indexes for the lookups the DAOs expose
CREATE INDEX IF NOT EXISTS idx_song_artist         ON song (artist_id);
CREATE INDEX IF NOT EXISTS idx_song_album          ON song (album_id);
CREATE INDEX IF NOT EXISTS idx_song_genre_genre    ON song_genre (genre_id);
CREATE INDEX IF NOT EXISTS idx_album_genre_genre   ON album_genre (genre_id);
CREATE INDEX IF NOT EXISTS idx_playlist_song_order ON playlist_song (playlist_id, position);
CREATE INDEX IF NOT EXISTS idx_playlist_song_song  ON playlist_song (song_id);
