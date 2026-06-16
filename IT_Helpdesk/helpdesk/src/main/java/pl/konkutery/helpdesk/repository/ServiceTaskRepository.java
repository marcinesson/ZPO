package pl.konkutery.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.konkutery.helpdesk.model.ServiceTask;

public interface ServiceTaskRepository extends JpaRepository<ServiceTask, Long> {
    
}
