package cinema.model;

import java.util.Arrays;
import java.util.Objects;

public class Auditorium {

    String name;
    int[][] seats = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    int numberOfSeats = seats.length;
    MovieSession movieSession;

    public Auditorium() {
    }

    public Auditorium(String name, int[][] seats, int numberOfSeats, MovieSession movieSession) {
        this.name = name;
        this.seats = seats;
        this.numberOfSeats = numberOfSeats;
        this.movieSession = movieSession;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[][] getSeats() {
        return seats;
    }

    public void setSeats(int[][] seats) {
        this.seats = seats;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public MovieSession getMovieSession() {
        return movieSession;
    }

    public void setMovieSession(MovieSession movieSession) {
        this.movieSession = movieSession;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Auditorium that = (Auditorium) o;
        return numberOfSeats == that.numberOfSeats
                && Objects.equals(name, that.name)
                && Arrays.equals(seats, that.seats)
                && Objects.equals(movieSession, that.movieSession);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, numberOfSeats, movieSession);
        result = 31 * result + Arrays.hashCode(seats);
        return result;
    }
}
