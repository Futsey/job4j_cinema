package cinema.model;

import java.util.Objects;

public class Ticket {

    int id;
    int visitorID;
    int movieSessionID;
    int posRow;
    int posCell;

    public Ticket() {
    }

    public Ticket(int id, int visitorID, int movieSessionID, int posRow, int posCell) {
        this.id = id;
        this.visitorID = visitorID;
        this.movieSessionID = movieSessionID;
        this.posRow = posRow;
        this.posCell = posCell;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVisitorID() {
        return visitorID;
    }

    public void setVisitorID(int visitorID) {
        this.visitorID = visitorID;
    }

    public int getMovieSessionID() {
        return movieSessionID;
    }

    public void setMovieSessionID(int movieSessionID) {
        this.movieSessionID = movieSessionID;
    }

    public int getPosRow() {
        return posRow;
    }

    public void setPosRow(int posRow) {
        this.posRow = posRow;
    }

    public int getPosCell() {
        return posCell;
    }

    public void setPosCell(int posCell) {
        this.posCell = posCell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return id == ticket.id && visitorID == ticket.visitorID && movieSessionID == ticket.movieSessionID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, visitorID, movieSessionID);
    }
}
