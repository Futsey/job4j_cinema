package cinema.store;

import cinema.model.Visitor;
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
public class VisitorDBStore {

    private final BasicDataSource pool;
    private static final Logger LOG = LoggerFactory.getLogger(VisitorDBStore.class.getName());
    private static final String SELECT_ALL = "SELECT * FROM visitors";
    private static final String SELECT_BY_ID = "SELECT * FROM visitors WHERE id = ?";
    private static final String SELECT_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";
    private static final String INSERT = "INSERT INTO users(userName, password, email, phoneNumber, created)"
            + "VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE =
            "UPDATE users SET userName = ?, email = ?, password = ?, phoneNumber = ? WHERE id = ?";

    public VisitorDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Visitor> findAll() {
        List<Visitor> visitors = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SELECT_ALL)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    visitors.add(addNewVisitor(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return visitors;
    }

    public Optional<Visitor> add(Visitor visitor) {
        Optional<Visitor> notNullUser = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(INSERT,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, visitor.getUserName());
            ps.setString(2, visitor.getPassword());
            ps.setString(3, visitor.getEmail());
            ps.setString(4, visitor.getPhoneNumber());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    visitor.setId(id.getInt(1));
                }
            }
            notNullUser = Optional.of(visitor);
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return notNullUser;
    }

    public void update(Visitor visitor) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE)) {
            ps.setString(1, visitor.getUserName());
            ps.setString(2, visitor.getPassword());
            ps.setString(3, visitor.getEmail());
            ps.setString(4, visitor.getPhoneNumber());
            ps.setInt(5, visitor.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
    }

    public Optional<Visitor> findById(int id) {
        Optional<Visitor> notNullVisitor = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    notNullVisitor = Optional.of(addNewVisitor(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return notNullVisitor;
    }

    public Optional<Visitor> findVisitorByEmailAndPassword(String email, String password) {
        Optional<Visitor> notNullVisitor = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_BY_EMAIL_AND_PASSWORD)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    notNullVisitor = Optional.of(addNewVisitor(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
        return notNullVisitor;
    }

    private Visitor addNewVisitor(ResultSet resultSet) throws SQLException {
        return new Visitor(resultSet.getInt("id"),
                resultSet.getString("userName"),
                resultSet.getString("password"),
                resultSet.getString("email"),
                resultSet.getString("phoneNumber"),
                resultSet.getTimestamp("created").toLocalDateTime());
    }
}
