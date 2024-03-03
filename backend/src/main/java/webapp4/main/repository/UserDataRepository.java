package webapp4.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webapp4.main.model.UserData;

import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserData, Long> {
    Optional<UserData> findByUsername(String username);
}
