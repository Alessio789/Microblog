
package it.marconivr.microblog.entities;

import javax.persistence.*;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

/**
 * User Entity
 *
 * @author Alessio Trentin - 5^EI
 * @version 3.0.0 - 22/04/2020
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User extends RepresentationModel<Post> {

    @Id
    @Getter
    @Setter
    private String username;

    @Basic
    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    private String email;

    @Basic
    @Getter
    @Setter
    @Column(nullable = false)
    private String password;

    @Basic
    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    private String salt;

    @Basic
    @Getter
    @Setter
    @Column(nullable = false)
    private String role = "USER";
}
