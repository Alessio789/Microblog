
package it.marconivr.microblog.entities;

import javax.persistence.*;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * User Entity
 *
 * @author Alessio Trentin
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User extends RepresentationModel<Post> {

    @Id
    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    private String email;

    @Getter
    @Setter
    @Column(nullable = false)
    private String password;

    @Getter
    @Setter
    @Column(nullable = false)
    private String roles = "USER";

    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}
