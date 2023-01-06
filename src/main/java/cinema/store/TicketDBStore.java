package cinema.store;

import cinema.model.Ticket;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TicketDBStore {

    private final BasicDataSource pool;
    private static final Logger LOG = LoggerFactory.getLogger(TicketDBStore.class.getName());
    private static final String SELECT_ALL = "SELECT * FROM tickets";
    private static final String SELECT_BY_ID = "SELECT * FROM tickets WHERE id = ?";
    private static final String INSERT = "INSERT INTO tickets(visitor_id, movie_session_id, pos_row, pos_cell) "
            + "VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE tickets SET visitor_id = ?, "
            + "movie_session_id = ?, pos_row = ?, pos_cell = ?";

    public TicketDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT_ALL)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(addNewTicket(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return tickets;
    }

    public Optional<Ticket> add(Ticket ticket) {
        Optional<Ticket> notNullTicket = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(INSERT,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, ticket.getVisitorID());
            ps.setInt(2, ticket.getMovieSessionID());
            ps.setInt(3, ticket.getPosRow());
            ps.setInt(4, ticket.getPosCell());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt(1));
                }
            }
            notNullTicket = Optional.of(ticket);
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return notNullTicket;
    }

    public void update(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE)) {
            ps.setInt(1, ticket.getVisitorID());
            ps.setInt(2, ticket.getMovieSessionID());
            ps.setInt(3, ticket.getPosRow());
            ps.setInt(4, ticket.getPosCell());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
    }

    public Ticket findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return addNewTicket(it);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return null;
    }

    private Ticket addNewTicket(ResultSet resultSet) throws SQLException {
        return new Ticket(resultSet.getInt("id"),
                resultSet.getInt("visitorID"),
                resultSet.getInt("movieSessionID"),
                resultSet.getInt("posRow"),
                resultSet.getInt("posCell"));
    }
}
