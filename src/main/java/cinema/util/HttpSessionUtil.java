package cinema.util;

import cinema.model.Visitor;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public final class HttpSessionUtil {

    private HttpSessionUtil() {
    }

    public static void setGuest(Model model, HttpSession session) {
        Visitor visitor = (Visitor) session.getAttribute("visitor");
        if (visitor == null) {
            visitor = new Visitor();
            visitor.setUserName("Гость");
        }
        model.addAttribute("visitor", visitor);
    }
}
