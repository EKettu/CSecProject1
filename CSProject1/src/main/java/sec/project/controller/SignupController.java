package sec.project.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sec.project.domain.Account;
import sec.project.domain.Event;
import sec.project.domain.Signup;
import sec.project.repository.EventRepository;
import sec.project.repository.AccountRepository;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private EventRepository eventRepository;
        
    @Autowired
    private SignupRepository signupRepository;
    
        @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm(Model model, Authentication authentication) {
         Account account = null;
        if(authentication != null) {
             account = accountRepository.findByUsername(authentication.getName());
        }
       
        model.addAttribute("account", account);
        model.addAttribute("allEvents", eventRepository.findAll());
        return "form";
    }
   
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address, 
    @RequestParam Long eventID, Model model) {
        Signup signup = new Signup(name, address);  
        model.addAttribute("signup", signup);
        Event event = eventRepository.findOne(eventID);
        List<Signup> signUps = event.getSignups();
        signUps.add(signup);      
        event.setSignups(signUps); 
        signup.setEvent(event);
        signupRepository.save(signup);
        eventRepository.save(event);
        return "done";
    }
    
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String loadLogin() {
//        return "login";
//    }
       
}
