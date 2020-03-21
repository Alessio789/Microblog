package it.marconivr.microblog.entities;

import javax.persistence.*;

import lombok.*;

/**
 * 
 * User Entity
 * 
 * @author Alessio Trentin - 5^EI
 * @version 2.3.1 - 21/03/2020
 */
@Entity
public class User {

    @Id
    @Getter @Setter 
    private String username;

    @Basic
    @Getter @Setter
    @Column(unique = true, nullable = false)
    private String email;

    @Basic
    @Getter @Setter
    @Column(nullable = false)
    private String password;

    @Basic
    @Getter @Setter
    @Column(unique = true, nullable = false)
    private String salt;

    @Basic
    @Getter @Setter
    @Column(nullable = false)
    private String role = "USER";

    /**
     * 
     * Constructor
     */
    public User() {
    }

    /**
     * 
     * Constructor
     * 
     * @param username
     * @param email
     * @param passwordHash
     * @param salt
     */
    public User(String username, String email, String password, String salt) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.salt = salt;
    }
}
