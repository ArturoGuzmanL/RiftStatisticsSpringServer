package javacode.server.springelectronriftstatisticswebapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name="cookies")
@EntityListeners(AuditingEntityListener.class)
public class Cookie {

    @Id
    @Column(name = "userid")
    String userid;
    @Column(name = "ip")
    String ip;

    public Cookie(String userid, String ip) {
        this.userid = userid;
        this.ip = ip;
    }

    public Cookie() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
