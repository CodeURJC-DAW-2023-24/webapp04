package webapp4.main.repository;

import webapp4.main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

}