package javacode.server.springelectronriftstatisticswebapp.repository;

import javacode.server.springelectronriftstatisticswebapp.model.Cookie;
import javacode.server.springelectronriftstatisticswebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CookieRepository extends JpaRepository<Cookie, Long> {

    Optional<Cookie> findByUserid(@Param("userid") String userid);

}
