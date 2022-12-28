package cinema.service;

import cinema.model.Visitor;
import cinema.store.VisitorDBStore;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;

import java.util.Optional;

@ThreadSafe
@Service
public class VisitorService {

    private VisitorDBStore visitorStore;

    public VisitorService(VisitorDBStore visitorStore) {
        this.visitorStore = visitorStore;
    }

    public Optional<Visitor> add(Visitor visitor) {
        return visitorStore.add(visitor);
    }

    public void update(Visitor visitor) {
        visitorStore.update(visitor);
    }

    public Optional<Visitor> findById(int id) {
        return visitorStore.findById(id);
    }

    public Optional<Visitor> findVisitorByEmailAndPassword(String email, String password) {
        return visitorStore.findVisitorByEmailAndPassword(email, password);
    }
}
