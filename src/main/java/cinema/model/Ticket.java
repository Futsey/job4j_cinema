package cinema.model;

import java.util.Objects;

public class Ticket {

    int id;
    Visitor visitor;
    MovieSession movieSession;
    Auditorium auditorium;
    PayMent payMent;

    public Ticket(int id, Visitor visitor, MovieSession movieSession, Auditorium auditorium, PayMent payMent) {
        this.id = id;
        this.visitor = visitor;
        this.movieSession = movieSession;
        this.auditorium = auditorium;
        this.payMent = payMent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public MovieSession getMovieSession() {
        return movieSession;
    }

    public void setMovieSession(MovieSession movieSession) {
        this.movieSession = movieSession;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public PayMent getPayMent() {
        return payMent;
    }

    public void setPayMent(PayMent payMent) {
        this.payMent = payMent;
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
        return id == ticket.id && Objects.equals(visitor, ticket.visitor) && Objects.equals(movieSession, ticket.movieSession) && Objects.equals(auditorium, ticket.auditorium) && Objects.equals(payMent, ticket.payMent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, visitor, movieSession, auditorium, payMent);
    }
}
