package it.marconivr.microblog.entities;

import java.util.Date;
import javax.persistence.*;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

/**
 * Class Entity Post
 *
 * @author Alessio Trentin
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Post extends RepresentationModel<Post> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Getter
    @Setter
    @Column(nullable = false)
    public Date dateHour;

    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    public String title;

    @Getter
    @Setter
    @Column(nullable = false)
    public String body;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @Getter
    @Setter
    @JoinColumn(name = "USER_USERNAME", nullable = false)
    private User user;

}