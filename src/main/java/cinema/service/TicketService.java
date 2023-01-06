package cinema.service;

import cinema.model.MovieSession;
import cinema.model.Ticket;
import cinema.store.MovieSessionDBStore;
import cinema.store.TicketDBStore;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class TicketService {

    private TicketDBStore ticketStore;

    public TicketService(TicketDBStore ticketStore) {
        this.ticketStore = ticketStore;
    }

    public Optional<Ticket> add(Ticket ticket) {
        return ticketStore.add(ticket);
    }

    public List<Ticket> findAll() {
        return ticketStore.findAll();
    }

    public void update(Ticket ticket) {
        ticketStore.update(ticket);
    }

    public Ticket findById(int id) {
        return ticketStore.findById(id);
    }
}
