package cinema.util;

import cinema.model.MovieSession;
import cinema.model.Ticket;
import cinema.model.Visitor;

public final class TicketUtil {

    private Ticket ticket;

    private TicketUtil() {
    }

    public int getVisitorId() {
        Visitor visitor = ticket.getVisitor();
        return visitor.getId();
    }

    public int getMovieSessionId() {
        MovieSession movieSession = ticket.getMovieSession();
        return movieSession.getId();
    }
}
