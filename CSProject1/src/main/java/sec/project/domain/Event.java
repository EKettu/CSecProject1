package sec.project.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Event extends AbstractPersistable<Long> {

    @Id
    private Long id;

    private String name;

    @ManyToOne
    private Account account;
    
    @OneToMany (mappedBy = "event", fetch=FetchType.EAGER)
    private List<Signup> signups;

    public List<Signup> getSignups() {
        if(signups == null) {
            return new ArrayList<Signup>();
        }
        return signups;
    }

    public void setSignups(List<Signup> signups) {
        this.signups = signups;
    }
  
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
