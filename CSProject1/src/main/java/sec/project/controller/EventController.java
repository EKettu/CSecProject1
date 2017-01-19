
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
import sec.project.repository.AccountRepository;
import sec.project.repository.EventRepository;
import sec.project.repository.SignupRepository;

@Controller
public class EventController {
    
    @Autowired
    private EventRepository eventRepository;
    
     @Autowired
    private AccountRepository accountRepository;
     
     @Autowired
    private SignupRepository signupRepository;

    
    
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String showEvents(Authentication authentication, Model model, @PathVariable Long id) {
        if(authentication==null) {
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
        return "redirect:/view/"+a.getId();
    }
    
    @RequestMapping(value = "/view/signed/{id}", method = RequestMethod.POST)
    public String addVIP(@PathVariable Long id) {
        Signup signup = signupRepository.getOne(id);
        signup.setVIP();
        Event event = signup.getEvent();
        Account a = event.getAccount();
        signupRepository.save(signup);
        return "redirect:/view/"+a.getId();
    }
        
}
