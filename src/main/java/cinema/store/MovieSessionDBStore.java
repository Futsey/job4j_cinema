package cinema.store;

import cinema.model.MovieSession;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieSessionDBStore {

    private final BasicDataSource pool;
    private static final Logger LOG = LoggerFactory.getLogger(MovieSessionDBStore.class.getName());
    private static final String SELECT_ALL = "SELECT * FROM movie_sessions";
    private static final String SELECT_BY_ID = "SELECT * FROM movie_sessions WHERE id = ?";
    private static final String SELECT_BY_NAME = "SELECT * FROM movie_sessions WHERE movieTitle = ?";
    private static final String INSERT = "INSERT INTO movie_sessions(poster, movieTitle, created) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE movie_sessions SET poster = ?, movieTitle = ?";

    public MovieSessionDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<MovieSession> findAll() {
        List<MovieSession> movieSessions = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT_ALL)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    movieSessions.add(addNewSession(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return movieSessions;
    }

    public Optional<MovieSession> add(MovieSession movieSession) {
        Optional<MovieSession> notNullSession = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(INSERT,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setBytes(1, movieSession.getPoster());
            ps.setString(2, movieSession.getMovieTitle());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    movieSession.setId(id.getInt(1));
                }
            }
            notNullSession = Optional.of(movieSession);
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return notNullSession;
    }

    public void update(MovieSession movieSession) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE)) {
            ps.setBytes(1, movieSession.getPoster());
            ps.setString(2, movieSession.getMovieTitle());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
    }

    public MovieSession findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return addNewSession(it);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return null;
    }

    public Optional<MovieSession> findSessionByTitle(String movieTitle) {
        Optional<MovieSession> notNullSession = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_BY_NAME)
        ) {
            ps.setString(1, movieTitle);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    notNullSession = Optional.of(addNewSession(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return notNullSession;
    }

    private MovieSession addNewSession(ResultSet resultSet) throws SQLException {
        return new MovieSession(resultSet.getInt("id"),
                resultSet.getBytes("poster"),
                resultSet.getString("movieTitle"),
                resultSet.getTimestamp("created").toLocalDateTime());
    }
}
