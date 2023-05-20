package javacode.server.springelectronriftstatisticswebapp.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name="users")
@EntityListeners(AuditingEntityListener.class)

public class User implements Serializable {
    public User (String ID, String username, String email, String password, LocalDate creationdate, String vinculatedlol, String lolregion, byte[] accountimage, boolean verified) {
        this.id = ID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.creationdate = creationdate;
        this.vinculatedlol = vinculatedlol;
        this.lolregion = lolregion;
        this.accountimage = accountimage;
        this.verified = verified;
    }

    public User (String username, String password, String email) {
        this.id = DigestUtils.sha256Hex(username + email + password);
        this.username = username;
        this.password = password;
        this.email = email;
        this.creationdate = LocalDate.now();
        this.verified = false;
    }

    public User () {
    }

    @Id
    @Column(name = "id")
    private String id = null;
    @Column(name = "username")
    private String username = null;
    @Column(name = "email")
    private String email = null;
    @Column(name = "password")
    private String password = null;
    @Column(name = "creationdate")
    private LocalDate creationdate = null;
    @Column(name = "vinculatedlol")
    private String vinculatedlol = null;
    @Column(name = "lolregion")
    private String lolregion = null;
    @Lob
    @Column(name = "accountimage")
    private byte[] accountimage;
    @Column(name = "verified")
    private boolean verified;
    @Column(name = "accountbirthday")
    private LocalDate accountbirthday;

    // getters y setters

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public LocalDate getCreationdate () {
        return creationdate;
    }

    public void setCreationdate (LocalDate creationdate) {
        this.creationdate = creationdate;
    }

    public String getVinculatedlol () {
        return vinculatedlol;
    }

    public void setVinculatedlol (String vinculatedlol) {
        this.vinculatedlol = vinculatedlol;
    }

    public String getLolregion () {
        return lolregion;
    }

    public void setLolregion (String lolregion) {
        this.lolregion = lolregion;
    }

    public byte[] getAccountimage () {
        return accountimage;
    }

    public void setAccountimage (byte[] accountimage) {
        this.accountimage = accountimage;
    }

    public boolean isVerified () {
        return verified;
    }

    public void setVerified (boolean verified) {
        this.verified = verified;
    }

    public LocalDate getAccountbirthday () {
        return accountbirthday;
    }

    public void setAccountbirthday (LocalDate accountbirthday) {
        this.accountbirthday = accountbirthday;
    }

    public boolean noneNull() {
        return id != null && username != null && password != null && email != null && creationdate != null && vinculatedlol != null && accountimage != null && lolregion != null && verified  && accountbirthday != null;
    }

    @Override
    public String toString () {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", create_Date='" + creationdate + '\'' +
                ", vinculatedLoL='" + vinculatedlol + '\'' +
                ", LoLRegion='" + lolregion + '\'' +
                ", AccountImage='" + accountimage + '\'' +
                ", AccountBirthday='" + accountbirthday + '\'' +
                '}';
    }

    public boolean HaslolAccount () {
        return vinculatedlol != null;
    }
}