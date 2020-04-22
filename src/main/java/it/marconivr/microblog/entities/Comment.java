package it.marconivr.microblog.entities;

import java.util.Date;

import javax.persistence.*;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

/**
 * 
 * Comment Entity
 * 
 * @author Alessio Trentin - 5^EI
 * @version 3.0.0 - 22/04/2020
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends RepresentationModel<Post> {

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

}
