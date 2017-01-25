package sec.project.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sec.project.domain.Account;
import sec.project.domain.Event;
import sec.project.domain.Signup;
import sec.project.repository.EventRepository;
import sec.project.repository.SignupRepository;

@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SignupRepository signupRepository;

    // A7-Missing Function Level Access Control:
    // In this controller there is no proper authentication in the request to the server,
    // so even if the events are only shown if someone is authenticated,
    // there are no checks to make sure the one authenticated is the organizer of the event. Thus
    // two users logged in at the same time are able to view and manage each other's events.
    // Account.id is visible in the url in plain text, 
    // which makes it easy to guess other users' account.ids.
    
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String showEvents(Authentication authentication, Model model, @PathVariable Long id) {
        if (authentication == null) {
            return "redirect:/form";
        }
        List<Event> eventList = eventRepository.findByAccountId(id);
        model.addAttribute("events", eventList);
        return "view";
    }

    @RequestMapping(value = "/view/signed/{id}", method = RequestMethod.DELETE)
    public String deleteSignup(@PathVariable Long id) {
        Signup s = signupRepository.findOne(id);
        Event event = s.getEvent();
        Account a = event.getAccount();
        signupRepository.delete(id);
        return "redirect:/view/" + a.getId();
    }

    @RequestMapping(value = "/view/signed/{id}", method = RequestMethod.POST)
    public String addVIP(@PathVariable Long id) {
        Signup signup = signupRepository.getOne(id);
        signup.setVIP();
        Event event = signup.getEvent();
        Account a = event.getAccount();
        signupRepository.save(signup);
        return "redirect:/view/" + a.getId();
    }

}
