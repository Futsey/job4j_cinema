package cinema.controller;

import cinema.model.MovieSession;
import cinema.service.MovieSessionService;
import net.jcip.annotations.ThreadSafe;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@ThreadSafe
public class MovieSessionController {

    private final MovieSessionService movieSessionService;

    public MovieSessionController(MovieSessionService movieSessionService) {
        this.movieSessionService = movieSessionService;
    }

    @GetMapping("/formAddMovieSession")
    public String addSession(Model model) {
        model.addAttribute("movieSessions", new MovieSession(0, "Добавьте название фильма"));
        return "addMovieSession";
    }

    @GetMapping("/movieSessions")
    public String candidates(Model model, HttpSession session) {
        model.addAttribute("movieSessions", movieSessionService.findAll());
//        setGuest(model, session);
        return "movieSessions";
    }

    @PostMapping("/createMovieSession")
    public String createCandidate(@ModelAttribute MovieSession movieSession,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        movieSession.setPoster(file.getBytes());
        movieSessionService.add(movieSession);
        return "redirect:/movieSessions";
    }

    @GetMapping("/formUpdateMovieSessions/{movieSessionId}")
    public String formUpdateCandidate(Model model, @PathVariable("movieSessionId") int id, HttpSession session) {
        model.addAttribute("movieSessions", movieSessionService.findById(id));
//        User user = (User) session.getAttribute("user");
//        setGuest(model, session);
        return "updateCandidate";
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
}

