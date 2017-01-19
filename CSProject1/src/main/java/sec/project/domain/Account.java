package sec.project.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Account extends AbstractPersistable<Long> {

    @Id
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    @OneToMany(mappedBy = "account")
    private List<Event> events;

    public List<Event> getEvents() {
        if (events == null) {
            return new ArrayList<Event>();
        }
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
