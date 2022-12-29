//package cinema.store;
//
//import cinema.model.MovieSession;
//import cinema.model.Ticket;
//import cinema.model.Visitor;
//import cinema.util.TicketUtil;
//import org.apache.commons.dbcp2.BasicDataSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Repository;
//
//import java.sql.*;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class TicketDBStore {
//
//    private final BasicDataSource pool;
//    TicketUtil ticketUtil;
//    private static final Logger LOG = LoggerFactory.getLogger(TicketDBStore.class.getName());
//    private static final String SELECT_ALL = "SELECT * FROM tickets";
//    private static final String SELECT_BY_ID = "SELECT * FROM tickets WHERE id = ?";
//    private static final String SELECT_BY_VISITOR = "SELECT * FROM tickets WHERE visitor_id = ?";
//    private static final String SELECT_BY_MOVIE_SESSION = "SELECT * FROM tickets WHERE session_id = ?";
//    private static final String INSERT = "INSERT INTO tickets(visitor, auditorium, payMent) VALUES (?, ?, ?)";
//    private static final String UPDATE = "UPDATE tickets SET visitorId = ?, auditoriumID = ?, payMent = ?";
//
//    public TicketDBStore(BasicDataSource pool) {
//        this.pool = pool;
//    }
//
//    public List<Ticket> findAll() {
//        List<Ticket> tickets = new ArrayList<>();
//        try (Connection cn = pool.getConnection();
//             PreparedStatement ps = cn.prepareStatement(SELECT_ALL)) {
//            try (ResultSet it = ps.executeQuery()) {
//                while (it.next()) {
//                    tickets.add(addNewTicket(it));
//                }
//            }
//        } catch (Exception e) {
//            LOG.error("Exception: ", e);
//        }
//        return tickets;
//    }
//
//    public Optional<Ticket> add(Ticket ticket) {
//        Optional<Ticket> notNullTicket = Optional.empty();
//        try (Connection cn = pool.getConnection();
//             PreparedStatement ps =  cn.prepareStatement(INSERT,
//                     PreparedStatement.RETURN_GENERATED_KEYS)
//        ) {
//            ps.setInt(1, ticketUtil.getVisitorId());
//            ps.setInt(1, ticketUtil.getMovieSessionId());
//            ps.setString(2, ticket.getMovieTitle());
//            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
//            ps.execute();
//            try (ResultSet id = ps.getGeneratedKeys()) {
//                if (id.next()) {
//                    ticket.setId(id.getInt(1));
//                }
//            }
//            notNullTicket = Optional.of(ticket);
//        } catch (Exception e) {
//            LOG.error("Exception: ", e);
//        }
//        return notNullTicket;
//    }
//
//    public void update(Ticket ticket) {
//        try (Connection cn = pool.getConnection();
//             PreparedStatement ps = cn.prepareStatement(UPDATE)) {
//            ps.setBytes(1, ticket.getPoster());
//            ps.setString(2, ticket.getMovieTitle());
//            ps.execute();
//        } catch (Exception e) {
//            LOG.error("Exception: ", e);
//        }
//    }
//
//    public Ticket findById(int id) {
//        try (Connection cn = pool.getConnection();
//             PreparedStatement ps =  cn.prepareStatement(SELECT_BY_ID)
//        ) {
//            ps.setInt(1, id);
//            try (ResultSet it = ps.executeQuery()) {
//                if (it.next()) {
//                    return addNewTicket(it);
//                }
//            }
//        } catch (Exception e) {
//            LOG.error("Exception: ", e);
//        }
//        return null;
//    }
//
//    public Optional<Ticket> findSessionByTitle(String movieTitle) {
//        Optional<Ticket> notNullTicket = Optional.empty();
//        try (Connection cn = pool.getConnection();
//             PreparedStatement ps =  cn.prepareStatement(SELECT_BY_NAME)
//        ) {
//            ps.setString(1, movieTitle);
//            try (ResultSet it = ps.executeQuery()) {
//                if (it.next()) {
//                    notNullTicket = Optional.of(addNewTicket(it));
//                }
//            }
//        } catch (Exception e) {
//            LOG.error("Exception: ", e);
//        }
//        return notNullTicket;
//    }
//
//    INSERT INTO tickets(visitor, auditorium, payMent) VALUES (?, ?, ?)";
//    private Ticket addNewTicket(ResultSet resultSet) throws SQLException {
//        return new Ticket(resultSet.getInt("id"),
//                resultSet.getString("visitor"),
//                resultSet.getString("auditorium"),
//                resultSet.getString("payMent"));
//    }
//}
