package cinema.controller;

import cinema.model.Ticket;
import cinema.service.MovieSessionService;
import cinema.service.TicketService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static cinema.util.HttpSessionUtil.setGuest;

@Controller
@ThreadSafe
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/formAddTicket")
    public String addTicket(Model model, HttpSession session) {
        model.addAttribute("tickets");
        setGuest(model, session);
        return "addTicket";
    }

    @GetMapping("/myTickets")
    public String candidates(Model model, HttpSession session) {
        model.addAttribute("movieSessions", ticketService.findAll());
        setGuest(model, session);
        return "myTickets";
    }

    @PostMapping("/createTicket")
    public String createTicket(@ModelAttribute Ticket ticket) {
        ticketService.add(ticket);
        return "redirect:/myTickets";
    }

    /** TODO implement updateMovieSession
    @GetMapping("/formUpdateMovieSessions/{movieSessionId}")
    public String formUpdateCandidate(Model model, @PathVariable("movieSessionId") int id, HttpSession session) {
        model.addAttribute("movieSessions", movieSessionService.findById(id));
        Visitor visitor = (Visitor) session.getAttribute("visitor");
        setGuest(model, session);
        return "updateMovieSession";
    }

    @PostMapping("/updateMovieSession")
    public String updateCandidate(@ModelAttribute MovieSession movieSession,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        movieSession.setPoster(file.getBytes());
        movieSessionService.update(movieSession);
        return "redirect:/movieSessions";
    }

    @GetMapping("/moviePoster/{movieSessionId}")
    public ResponseEntity<Resource> download(@PathVariable("movieSessionId") Integer movieSessionId) {
        MovieSession movieSession = movieSessionService.findById(movieSessionId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(movieSession.getPoster().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(movieSession.getPoster()));
    }
    */
}
