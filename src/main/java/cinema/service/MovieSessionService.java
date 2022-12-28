package cinema.service;

import cinema.model.MovieSession;
import cinema.store.MovieSessionDBStore;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class MovieSessionService {

    private MovieSessionDBStore sessionStore;

    public MovieSessionService(MovieSessionDBStore visitorStore) {
        this.sessionStore = visitorStore;
    }

    public Optional<MovieSession> add(MovieSession movieSession) {
        return sessionStore.add(movieSession);
    }

    public List<MovieSession> findAll() {
        return sessionStore.findAll();
    }

    public void update(MovieSession movieSession) {
        sessionStore.update(movieSession);
    }

    public MovieSession findById(int id) {
        return sessionStore.findById(id);
    }

    public Optional<MovieSession> findSessionByTitle(String movieTitle) {
        return sessionStore.findSessionByTitle(movieTitle);
    }
}
