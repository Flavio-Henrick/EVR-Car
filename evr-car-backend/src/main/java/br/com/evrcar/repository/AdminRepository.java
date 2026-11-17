package br.com.evrcar.repository;

import br.com.evrcar.model.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminUser, Long> {
    Optional<AdminUser> findByUsername(String username);
}
