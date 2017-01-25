package sec.project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sec.project.domain.Event;
import sec.project.domain.Signup;

public interface SignupRepository extends JpaRepository<Signup, Long> {

    List<Signup> findByEvent(Event event);

}
