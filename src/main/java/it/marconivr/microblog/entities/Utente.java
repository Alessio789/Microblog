package it.marconivr.microblog.entities;

import javax.persistence.*;

import lombok.*;

/**
 * 
 * Classe Entity Utente
 * 
 * @author Alessio Trentin - 5^EI
 * @version 2.1.0 - 19/03/2020
 */
@Entity
public class Utente {

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
    private String ruolo = "USER";

    /**
     * 
     * Constructor
     */
    public Utente() {
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
    public Utente(String username, String email, String password, String salt) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.salt = salt;
    }
}
