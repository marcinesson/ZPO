package pl.konkutery.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.konkutery.helpdesk.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
