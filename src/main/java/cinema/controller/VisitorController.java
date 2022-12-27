package cinema.controller;

import cinema.model.Visitor;
import cinema.service.VisitorService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@ThreadSafe
public class VisitorController {

    private final VisitorService visitorService;

    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @GetMapping("/formAddVisitor")
    public String addUser(Model model) {
        model.addAttribute("visitor", new Visitor(0, "Введите имя", "Введите пароль",
                "Введите электронную почту", "Введите номер телефона"));
        return "addVisitor";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute Visitor visitor) {
        Optional<Visitor> regVisitor = visitorService.add(visitor);
        if (regVisitor.isEmpty()) {
            model.addAttribute("message",
                    "Пользователь с такой почтой уже существует");
            return "redirect:/fail";
        }
        return "redirect:/success";
    }

    /** TODO implement html */
    @GetMapping("/fail")
    public String fail(Model model) {
        model.addAttribute("fail", "Registration failed");
        return "registrationFailed";
    }

    /** TODO implement html */
    @GetMapping("/success")
    public String success(Model model) {
        model.addAttribute("user", new Visitor());
        model.addAttribute("success", "Registration successful");
        return "registrationSuccess";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Visitor visitor, HttpServletRequest req) {
        Optional<Visitor> visitorDb = visitorService.findUserByEmailAndPassword(
                visitor.getEmail(), visitor.getPassword()
        );
        if (visitorDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("visitor", visitorDb.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}
