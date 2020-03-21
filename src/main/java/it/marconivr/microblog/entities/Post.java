package it.marconivr.microblog.entities;

import java.util.Date;

import javax.persistence.*;

import lombok.*;

/**
 * 
 * Classe Entity Post
 * 
 * @author Alessio Trentin - 5^EI
 * @version 2.1.1 - 21/03/2020
 */
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter
    @Column(nullable = false)
    public Date dateHour;

    @Basic
    @Getter @Setter
    @Column(unique = true, nullable = false)
    public String title;

    @Lob
    @Getter @Setter
    @Column(nullable = false)
    public String body;

    @ManyToOne(targetEntity = User.class)
    @Getter @Setter
    @JoinColumn(name = "USER_USERNAME", nullable = false)
    private User user;

    /**
     * 
     * Constructor 
     * 
     * @param id
     * @param dateHour
     * @param title
     * @param body
     * @param user
     */
    public Post(long id, Date dateHour, String title, String body, User user) {
        this.id = id;
        this.dateHour = dateHour;
        this.title = title;
        this.body = body;
        this.user = user;
    }

    /**
     * 
     * Constructor
     */
    public Post() {
    }
}