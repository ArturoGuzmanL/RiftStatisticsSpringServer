package javacode.server.springelectronriftstatisticswebapp.repository;

import javacode.server.springelectronriftstatisticswebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername (@Param("username") String username);
    Optional<User> findByEmail (@Param("email") String email);
    Optional<User> findByUsernameAndPassword (@Param("username") String username, @Param("password") String password);
    Optional<User> findByVinculatedlol (@Param("vinculatedlol") String vinculatedlol);
    Optional<User> findById (@Param("id") String id);
}