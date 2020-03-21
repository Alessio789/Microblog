package it.marconivr.microblog.entities;

import java.util.Date;

import javax.persistence.*;

import lombok.*;

/**
 * 
 * Comment Entity
 * 
 * @author Alessio Trentin - 5^EI
 * @version 2.1.1 - 21/03/2020
 */
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter 
    private Long id;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Getter @Setter 
    @Column(nullable = false)
    private Date dateHour;

    @Basic
    @Getter @Setter 
    @Column(nullable = false)
    private String body;

    @OneToOne(targetEntity = Post.class)
    @Getter @Setter
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

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
     * @param body
     * @param post
     * @param user 
     */
    public Comment(Long id, Date dateHour, String body, Post post, User user) {
        this.id = id;
        this.dateHour = dateHour;
        this.body = body;
        this.post = post;
        this.user = user;
    } 

    /**
     * 
     * Constructor
     */
    public Comment() {
    }
}
