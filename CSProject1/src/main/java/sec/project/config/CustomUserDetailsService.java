package sec.project.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sec.project.domain.Account;
import sec.project.domain.Event;
import sec.project.repository.AccountRepository;
import sec.project.repository.EventRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {

        Account account = new Account();
        account.setUsername("Charlie");
        account.setPassword(passwordEncoder.encode("snoopy"));
        accountRepository.save(account);
        Event event1 = new Event();
        event1.setName("Great Pumpkin party");
        eventRepository.save(event1);
        List<Event> jEvents = new ArrayList<Event>();
        jEvents.add(event1);
        event1.setAccount(account);
        account.setEvents(jEvents);
        eventRepository.save(event1);

        account = new Account();
        account.setUsername("Jon");
        account.setPassword(passwordEncoder.encode("garfield"));
        accountRepository.save(account);
        Event event2 = new Event();
        event2.setName("Pizza Party");
        List<Event> eEvents = new ArrayList<Event>();
        eEvents.add(event2);
        event2.setAccount(account);
        account.setEvents(eEvents);
        eventRepository.save(event2);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                account.getUsername(),
                account.getPassword(),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("USER")));
    }

}
