package cinema.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class MovieSession {

    int id;
    private byte[] poster;
    String movieTitle;
    LocalDateTime date;

    public MovieSession() {
    }

    public MovieSession(int id, String movieTitle) {
        this.id = id;
        this.movieTitle = movieTitle;
    }

    public MovieSession(int id, byte[] poster, String movieTitle) {
        this.id = id;
        this.poster = poster;
        this.movieTitle = movieTitle;
    }

    public MovieSession(int id, byte[] poster, String movieTitle, LocalDateTime date) {
        this.id = id;
        this.poster = poster;
        this.movieTitle = movieTitle;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getPoster() {
        return poster;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MovieSession movieSession = (MovieSession) o;
        return id == movieSession.id && Arrays.equals(poster, movieSession.poster) && Objects.equals(date, movieSession.date);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, date);
        result = 31 * result + Arrays.hashCode(poster);
        return result;
    }
}
